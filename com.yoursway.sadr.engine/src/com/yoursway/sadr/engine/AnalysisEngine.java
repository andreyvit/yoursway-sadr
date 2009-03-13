package com.yoursway.sadr.engine;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;

import kilim.pausable;
import kilim.fibers.PauseReason;
import kilim.fibers.Task;
import kilim.fibers.TaskDoneReason;

import com.yoursway.sadr.engine.internal.GoalEvaluationFailed;
import com.yoursway.utils.Failure;
import com.yoursway.utils.bugs.Bugs;

/**
 * Continuations evaluator. Just invoke new
 * AnalysisEngine().execute(yourSimpleContinuation) and it will provide
 * scheduler for this continuation and all tasks scheduled by it.
 */
public abstract class AnalysisEngine implements GoalResultCacheCleaner {
    
    protected static final boolean TRACING_GOALS = false;
    
    //    Boolean.valueOf(Platform
    //            .getDebugOption("com.yoursway.sadr.engine/traceGoals"));
    
    protected void trace(String msg) {
        System.out.println(msg);
    }
    
    static final class GoalContinuationContractViolation extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        public GoalContinuationContractViolation(String methodName, Goal<?> goal) {
            super(String.format("%s did not create a continuation or call done, goal %s", methodName, String
                    .valueOf(goal)));
        }
        
    }
    
    interface PossibleSubgoalsAdder {
        
        void runAndPossiblyAddSubgoals(GoalState writableGoalState);
        
    }
    
    public Set<GoalState> toBeDone = new HashSet<GoalState>();
    public Set<GoalState> leaves = new HashSet<GoalState>();
    
    public Set<GoalState> secondMagicSet = new HashSet<GoalState>();
    
    enum GoalSource {
        
        ROOT,

        CONTINUATION,

        DUPLICATE
        
    }
    
    int nextVisitationMark = 1;
    
    protected class GoalState extends Task implements AnalysisTask {
        
        private static final int DONE_COUNT = -1;
        
        private static final int ADDING_SUBGOALS = 1000000;
        
        protected final Collection<GoalState> parentStates = newArrayList();
        
        private final Collection<GoalState> children = newArrayList();
        
        private final Collection<GoalState> doneChildren = newArrayList();
        
        GoalSource source;
        
        /**
         * Either a count of subgoals, or <code>DONE_COUNT</code> when this goal
         * is done, or <code>ADDING_SUBGOALS</code> plus a count of subgoals
         * during execution of <code>allowStateChangeAndRun</code>.
         */
        private int subgoalCount = 0;
        
        private int evalCount = 0;
        
        private int monotonicallyIncreasingSubgoalCount = 0;
        
        public final Goal<?> goal;
        
        public final SlotImpl<? extends Result> slot;
        
        private int totalSubgoals = 0;
        
        private int visitationMark = 0;
        
        private boolean finished = false;
        
        protected boolean pruned = false;
        
        public <R extends Result> GoalState(Goal<R> goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.source = GoalSource.ROOT;
            this.goal = goal;
            ++totalGoals;
            ++unfinishedGoals;
            if (TRACING_GOALS)
                trace("queue.enqueue(): " + goal);
            slot = new SlotImpl<R>();
            start(AnalysisEngine.this.executor);
        }
        
        public void addParent(GoalState parentState) {
            if (parentState == null)
                throw new NullPointerException("parentState is null");
            parentStates.add(parentState);
            parentState.subgoalAdded(this);
        }
        
        private void didAddSubgoals() {
            if (subgoalCount >= ADDING_SUBGOALS)
                subgoalCount -= ADDING_SUBGOALS;
        }
        
        private void willAddSubgoals() throws AssertionError {
            if (subgoalCount != 0)
                if (subgoalCount == DONE_COUNT)
                    throw new IllegalStateException(
                            "Cannot allow changing state when the goal is already done");
                else if (subgoalCount >= ADDING_SUBGOALS)
                    throw new IllegalStateException(
                            "Recursive calls to allowStateChangeAndRun are not allowed");
                else if (subgoalCount > 0)
                    throw new IllegalStateException(
                            "Cannot allow changing state when there are pending subgoals");
                else
                    throw new AssertionError("Invalid value of the internal subgoal count");
            subgoalCount = ADDING_SUBGOALS;
        }
        
        /**
         * DFS over all parent states to find a match.
         */
        public GoalState findGoalStateByGoal(Goal<?> goal) {
            // TODO: handle wrap-around (though it's not likely to occur anyway)
            int mark = nextVisitationMark++;
            List<GoalState> stack = newArrayListWithCapacity(activeGoalStates.size());
            stack.add(this);
            this.visitationMark = mark;
            while (!stack.isEmpty()) {
                GoalState state = stack.remove(stack.size() - 1);
                if (state.goal.hashCode() == goal.hashCode() && state.goal.equals(goal))
                    return state;
                for (GoalState parent : state.parentStates)
                    if (parent.visitationMark < mark) {
                        parent.visitationMark = mark;
                        stack.add(parent);
                    }
            }
            return null;
        }
        
        @SuppressWarnings("unchecked")
        public void markAsDone(Result result) {
            if (source == GoalSource.DUPLICATE)
                System.out.println("source == GoalSource.DUPLICATE, GoalState.markAsDone(" + this + ")");
            if (subgoalCount >= ADDING_SUBGOALS)
                throw new IllegalStateException(
                        "Cannot mark the goal as done from inside allowStateChangeAndRun");
            if (subgoalCount > 0)
                throw new IllegalStateException(
                        "Cannot mark the goal as done when there are pending subgoals");
            subgoalCount = DONE_COUNT;
            ((SlotImpl<Result>) slot).setResult(result);
            goalFinished();
        }
        
        protected void goalFinished() {
            if (finished)
                throw new IllegalStateException("Goal is already finished: " + goal);
            finished = true;
            if (TRACING_GOALS)
                trace("goalFinished(): " + goal);
            --unfinishedGoals;
            GoalState removed = activeGoalStates.remove(goal);
            if (removed != this)
                throw new AssertionError("Goal state is too old for those newy-deletey things");
            for (GoalState parent : parentStates)
                parent.subgoalFinished(this);
            stats.finishedGoal(goal);
        }
        
        void subgoalAdded(GoalState state) {
            if (subgoalCount < ADDING_SUBGOALS)
                if (subgoalCount == DONE_COUNT)
                    throw new IllegalStateException("Cannot add subgoals when the goal is done");
                else
                    throw new IllegalStateException("Cannot add subgoals from outside allowStateChangeAndRun");
            ++subgoalCount;
            ++totalSubgoals;
            ++monotonicallyIncreasingSubgoalCount;
            children.add(state);
        }
        
        protected void subgoalFinished(GoalState state) {
            if (subgoalCount == DONE_COUNT)
                throw new IllegalStateException("Subgoal cannot be finished when the goal is already done");
            if (subgoalCount == 0)
                throw new IllegalStateException("This goal has no subgoals");
            children.remove(state);
            doneChildren.add(state);
            if (--subgoalCount == 0) {
                resume();
            }
        }
        
        @Override
        public String toString() {
            return "<" + subgoalCount + "," + parentStates.size() + ">" + goal;
        }
        
        public void setPruned() {
            pruned = true;
            markAsDone(goal.createRecursiveResult());
        }
        
        @Override
        @pausable
        public void execute() throws Exception {
            try {
                markAsDone(goal.evaluate());
            } catch (OutOfMemoryError e) {
                memoryHog = null;
                Runtime.getRuntime().gc();
                throw e;
            }
        }
        
        @Override
        public void run() {
            if (finished)
                throw new IllegalArgumentException("Goal is already finished: " + goal);
            long start = System.currentTimeMillis();
            try {
                super.run(); // calls goalFinished when absofuckinglutely done
                if (pauseReason instanceof TaskDoneReason) {
                    Object exitobj = ((TaskDoneReason) pauseReason).exitObj;
                    if (exitobj instanceof Error)
                        throw (Error) exitobj;
                    if (exitobj instanceof RuntimeException)
                        throw (RuntimeException) exitobj;
                }
            } finally {
                long d = System.currentTimeMillis() - start;
                ++evalCount;
                stats.finishedRun(goal, d);
            }
        }
        
        @pausable
        public <R extends Result> R evaluate(Goal<R> goal) {
            return evaluate(Collections.singleton(goal)).get(0);
        }
        
        @pausable
        public <R extends Result> List<R> evaluate(final Collection<? extends Goal<R>> goals) {
            final List<Slot<R>> slots = new ArrayList<Slot<R>>(goals.size());
            willAddSubgoals();
            try {
                for (Goal<R> goal : goals)
                    slots.add(subgoal(goal));
            } finally {
                didAddSubgoals();
            }
            Task.pause(new PauseReason() {
                public boolean isValid() {
                    return subgoalCount > 0;
                }
            });
            List<R> results = new ArrayList<R>(slots.size());
            for (Slot<R> slot : slots)
                results.add(slot.result());
            return results;
        }
        
        @SuppressWarnings("unchecked")
        <R extends Result> Slot<R> subgoal(Goal<R> goal) {
            Result cachedResult = lookupResultInCache(goal, this);
            if (cachedResult != null) {
                if (TRACING_GOALS)
                    trace("cacheHit() for subgoal: " + goal);
                stats.cacheHit(goal);
                return new SlotImpl<R>((R) cachedResult);
            }
            GoalState state = activeGoalStates.get(goal);
            if (state == null)
                state = createAndPutGoalState(goal);
            else {
                long start = System.currentTimeMillis();
                if (findGoalStateByGoal(goal) != null) {
                    stats.recursiveGoal(goal);
                    return new SlotImpl<R>(createRecursiveResult(this, goal));
                }
                long span = System.currentTimeMillis() - start;
                if (span > 50)
                    System.out.println("SubqueryCreator.subgoal() - findGoalStateByGoal took " + span);
            }
            state.addParent(this);
            return (Slot<R>) state.slot;
        }
        
    }
    
    @pausable
    public static AnalysisTask currentTask() {
        return (AnalysisTask) Task.getCurrentTask();
    }
    
    /**
     * Mass grave: Q, QQ, QQQ, QQQQ, QQQQ_ R.I.P.
     */
    
    private final QueryQueue queue = new QueryQueue();
    
    private final Executor executor = new Executor() {
        
        public void execute(Runnable command) {
            queue.enqueue((GoalState) command);
        }
    };
    
    protected final Map<Goal<?>, GoalState> activeGoalStates = new HashMap<Goal<?>, GoalState>();
    
    private AnalysisStats stats = new AnalysisStats();
    
    private int totalGoals;
    
    private int unfinishedGoals;
    //    private List<Long> runTimes;
    private byte[] memoryHog;
    
    <R extends Result> GoalState lookupGoalState(Goal<R> goal) {
        GoalState state = activeGoalStates.get(goal);
        if (state == null)
            state = createAndPutGoalState(goal);
        return state;
    }
    
    protected <R extends Result> GoalState createAndPutGoalState(Goal<R> goal) {
        GoalState state = createGoalState(goal);
        activeGoalStates.put(goal, state);
        return state;
    }
    
    protected <R extends Result> GoalState createGoalState(Goal<R> goal) {
        return new GoalState(goal);
    }
    
    public AnalysisStats clearStats() {
        AnalysisStats old = stats;
        stats = new AnalysisStats();
        return old;
    }
    
    //    public void evaluate(Continuation continuation) {
    //        continuation.provideSubgoals(new SubgoalRequestor() {
    //            
    //            public void subgoal(Goal goal) {
    //                if (!start(goal, null))
    //                    return;
    //                queue.enqueue(new QQ(goal));
    //            }
    //            
    //        });
    //    }
    
    public <R extends Result> R evaluate(Goal<R> goal) {
        return evaluate(goal, -1);
    }
    
    @SuppressWarnings("unchecked")
    public <R extends Result> R evaluate(Goal<R> goal, int timeoutMillis) {
        long start = System.currentTimeMillis();
        try {
            queue.clear();
            totalGoals = 0;
            if (unfinishedGoals > 0)
                System.err.println("unfinishedGoals was > 0");
            unfinishedGoals = 0;
            activeGoalStates.clear();
            toBeDone.clear();
            leaves.clear();
            Result cachedResult = lookupResultInCache(goal, null);
            if (cachedResult != null) {
                stats.cacheHit(goal);
                stats.rootGoalDone(System.currentTimeMillis() - start);
                return (R) cachedResult;
            }
            GoalState state = lookupGoalState(goal);
            executeQueue(timeoutMillis);
            
            if (unfinishedGoals > 0)
                throw new SomeGoalsNotFinalized().add("unfinished_goals", unfinishedGoals).add("root_goal",
                        goal);
            else if (unfinishedGoals < 0)
                throw new AssertionError("FUCK FUCK FUCK");
            return (R) state.slot.result();
        } finally {
            stats.rootGoalDone(System.currentTimeMillis() - start);
            //            System.out.println(rootGoalCount + " root goals, executeQueue() " + timeSpentInExecuteQueue
            //                    + ", run() " + timeSpentInRun + ", evaluate() " + timeSpentInEvaluate
            //                    + " including cached " + timeSpentInEvaluateCached + " (in " + cachedRootGoalCount
            //                    + " cached root goals)");
        }
    }
    
    public void executeQueue(int timeoutMillis) {
        if (memoryHog == null)
            memoryHog = new byte[64 * 1024]; // 64K should be enough for everyone
        memoryHog[0] = 42;
        long start = System.currentTimeMillis();
        long fin = (timeoutMillis <= 0 ? -1 : System.currentTimeMillis() + timeoutMillis);
        GoalState current;
        int loopNo = 0;
        while ((current = queue.poll()) != null) {
            //           System.out.println("RUNNING " + current);
            if (loopNo++ == 50) {
                loopNo = 0;
                long freeMemory = computeFreeMemory();
                if (freeMemory < 20 * 1024 * 1024) {
                    Runtime.getRuntime().gc();
                    freeMemory = computeFreeMemory();
                    if (freeMemory < 40 * 1024 * 1024) {
                        timeLimitReached();
                        break;
                    }
                }
            }
            try {
                current.run();
            } catch (RuntimeException e) {
                Bugs.bug(new GoalEvaluationFailed(e, current.goal));
                current.setPruned();
            } catch (AssertionError e) {
                Bugs.bug(new GoalEvaluationFailed(e, current.goal));
                current.setPruned();
            } catch (OutOfMemoryError e) {
                memoryHog = null;
                Runtime.getRuntime().gc();
                Bugs.bug(e);
                timeLimitReached();
                break;
            }
            if (fin > 0 && System.currentTimeMillis() > fin) {
                timeLimitReached();
                break;
            }
        }
        if (unfinishedGoals > 0)
            System.err.println("ACHTUNG UNFINISHED GOALS ATTACK!!!!!");
        else if (unfinishedGoals < 0)
            System.err.println("FUCK FUCK FUCK");
        stats.queueRunDone(System.currentTimeMillis() - start);
        //        if (runTimes != null)
        //            runTimes.add(duration);
    }
    
    private long computeFreeMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long potentiallyFreeMemory = (maxMemory == Long.MAX_VALUE ? runtime.freeMemory() : maxMemory
                - usedMemory);
        return potentiallyFreeMemory;
    }
    
    private void timeLimitReached() {
        System.out.println(" *** ANALYSIS TIMEOUT *** ");
        GoalState current;
        while ((current = queue.poll()) != null)
            prune(current);
    }
    
    protected abstract void prune(GoalState current);
    
    public abstract boolean isCached(Goal<?> goal);
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(super.toString());
        return res.toString();
    }
    
    protected abstract Result lookupResultInCache(Goal<?> goal, GoalState parentState);
    
    protected <R extends Result> R createRecursiveResult(GoalState parentState, Goal<R> goal) {
        return goal.createRecursiveResult();
    }
    
    static class QueryQueue implements QueryEnqueuer {
        
        private final Queue<GoalState> queue = new LinkedList<GoalState>();
        
        public void enqueue(GoalState query) {
            queue.add(query);
        }
        
        public GoalState poll() {
            return queue.poll();
        }
        
        public void clear() {
            queue.clear();
        }
        
    }
    
    public interface QueryEnqueuer {
        
        void enqueue(GoalState query);
        
    }
    
    static class SomeGoalsNotFinalized extends Failure {
        
        private static final long serialVersionUID = 1L;
        
    }
    
    public void setRunStatisticsTarget(List<Long> runTimes) {
        //        this.runTimes = runTimes;
    }
    
}
