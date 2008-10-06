package com.yoursway.sadr.engine;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

/**
 * Continuations evaluator. Just invoke new
 * AnalysisEngine().execute(yourSimpleContinuation) and it will provide
 * scheduler for this continuation and all tasks scheduled by it.
 */
public class AnalysisEngine {
    
    static final class GoalContinuationContractViolation extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        public GoalContinuationContractViolation(String methodName, Goal<?> goal) {
            super(String.format("%s did not create a continuation or call done, goal %s", methodName, String
                    .valueOf(goal)));
        }
        
    }
    
    static final class SimpleContinuationMustNotCallDone extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        public SimpleContinuationMustNotCallDone(SimpleContinuation continuation) {
            super(String.format("Simple continuations must not call done, but %s did", continuation));
        }
    }
    
    final class SubqueryCreator implements PossibleSubgoalsAdder, SubgoalRequestor {
        
        private final SubgoalsProvider provider;
        private GoalState writableGoalState;
        
        public SubqueryCreator(SubgoalsProvider provider) {
            this.provider = provider;
        }
        
        @SuppressWarnings("unchecked")
        public <R extends Result> Slot<R> subgoal(Goal<R> goal) {
            Result cachedResult = cache.get(goal);
            if (cachedResult != null) {
                stats.cacheHit(goal);
                return new SlotImpl<R>((R) cachedResult);
            }
            if (writableGoalState.findGoalStateByGoal(goal) != null) {
                debug.recursive(goal);
                stats.recursiveGoal(goal);
                return new SlotImpl<R>(goal.createRecursiveResult());
            }
            stats.starting(goal);
            GoalState state = createGoalState(goal);
            state.addParent(writableGoalState);
            return (Slot<R>) state.slot;
        }
        
        public void runAndPossiblyAddSubgoals(GoalState writableGoalState) {
            this.writableGoalState = writableGoalState;
            provider.provideSubgoals(this);
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
    
    class GoalState {
        
        private static final int DONE_COUNT = -1;
        
        private static final int ADDING_SUBGOALS = 1000000;
        
        private final Collection<GoalState> parentStates = newArrayList();
        
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
        
        private Query allChildrenFinishedCallback;
        
        private int monotonicallyIncreasingSubgoalCount = 0;
        
        private final Goal<?> goal;
        
        private final SlotImpl<?> slot;
        
        public <R extends Result> GoalState(Goal<R> goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.source = GoalSource.ROOT;
            this.goal = goal;
            ++magicNumber;
            ++secondMagicNumber;
            secondMagicSet.add(this);
            queue.enqueue(new InitialGoalQuery(this));
            slot = new SlotImpl<R>();
        }
        
        public void addParent(GoalState parentState) {
            if (parentState == null)
                throw new NullPointerException("parentState is null");
            parentStates.add(parentState);
            parentState.subgoalAdded(this);
        }
        
        public void allowStateChangeAndRun(PossibleSubgoalsAdder adder, Query allChildrenFinishedCallback) {
            if (allChildrenFinishedCallback == null)
                throw new NullPointerException("allChildrenFinishedCallback is null");
            
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
            try {
                adder.runAndPossiblyAddSubgoals(this);
            } finally {
                if (subgoalCount >= ADDING_SUBGOALS)
                    subgoalCount -= ADDING_SUBGOALS;
                if (subgoalCount == 0) {
                    toBeDone.add(((ContinuationQuery) allChildrenFinishedCallback).goal);
                    queue.enqueue(allChildrenFinishedCallback);
                } else if (subgoalCount > 0)
                    this.allChildrenFinishedCallback = allChildrenFinishedCallback;
                else
                    throw new AssertionError(
                            "Invalid value of the internal subgoal count (after runAndPossiblyAddSubgoals)");
            }
            
        }
        
        public void runOnBehalfOfThisGoal(Runnable runnable) {
            stats.startingEvaluation(goal);
            ++evalCount;
            runnable.run();
            stats.finishedEvaluation(goal);
            stats.finishedQuery(goal);
        }
        
        public GoalState findGoalStateByGoal(Goal<?> goal) {
            if (this.goal.hashCode() == goal.hashCode() && this.goal.equals(goal))
                return this;
            for (GoalState parent : parentStates) {
                GoalState result = parent.findGoalStateByGoal(goal);
                if (result != null)
                    return result;
            }
            return null;
        }
        
        @SuppressWarnings("unchecked")
        public void markAsDone(Result result) {
            if (source == GoalSource.DUPLICATE)
                System.out.println("GoalState.markAsDone(" + this + ")");
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
        
        private void goalFinished() {
            //            System.out.println("FINISHED " + goal);
            --secondMagicNumber;
            secondMagicSet.remove(this);
            activeGoalStates.remove(goal);
            for (GoalState parent : parentStates)
                parent.subgoalFinished(this);
            finished(this);
        }
        
        void subgoalAdded(GoalState state) {
            if (subgoalCount < ADDING_SUBGOALS)
                if (subgoalCount == DONE_COUNT)
                    throw new IllegalStateException("Cannot add subgoals when the goal is done");
                else
                    throw new IllegalStateException("Cannot add subgoals from outside allowStateChangeAndRun");
            ++subgoalCount;
            ++monotonicallyIncreasingSubgoalCount;
            children.add(state);
        }
        
        void subgoalFinished(GoalState state) {
            if (subgoalCount == DONE_COUNT)
                throw new IllegalStateException("Subgoal cannot be finished when the goal is already done");
            if (subgoalCount == 0)
                throw new IllegalStateException("This goal has no subgoals");
            children.remove(state);
            doneChildren.add(state);
            if (--subgoalCount == 0) {
                try {
                    queue.enqueue(allChildrenFinishedCallback);
                } finally {
                    allChildrenFinishedCallback = null;
                }
            }
        }
        
        @Override
        public String toString() {
            return "<" + subgoalCount + "," + parentStates.size() + ">" + goal;
        }
        
    }
    
    /**
     * R.I.P., Q
     */
    abstract class QueryImpl extends Query implements ContinuationScheduler, Runnable {
        
        protected final GoalState goal;
        
        public QueryImpl(GoalState goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.goal = goal;
        }
        
        @Override
        public void evaluate() {
            goal.runOnBehalfOfThisGoal(this);
        }
        
        public void run() {
            ContinuationRequestorCalledToken token = pleaseEvaluate();
            if (token == null)
                signalContinueOrDoneViolation();
        }
        
        protected abstract void signalContinueOrDoneViolation();
        
        public ContinuationRequestorCalledToken schedule(Continuation cont) {
            goal.allowStateChangeAndRun(new SubqueryCreator(cont), new ContinuationQuery(goal, cont));
            return DumbReturnValue.instance();
        }
        
        public ContinuationRequestorCalledToken schedule(SimpleContinuation cont) {
            queue.enqueue(new SimpleContinuationQuery(goal, cont));
            return DumbReturnValue.instance();
        }
        
        public ContinuationRequestorCalledToken done(Result result) {
            goal.markAsDone(result);
            return DumbReturnValue.instance();
        }
        
        public Query currentQuery() {
            return this;
        }
        
        protected abstract ContinuationRequestorCalledToken pleaseEvaluate();
        
    }
    
    /**
     * R.I.P., QQ
     */
    final class InitialGoalQuery extends QueryImpl {
        
        public InitialGoalQuery(GoalState goal) {
            super(goal);
        }
        
        @Override
        protected ContinuationRequestorCalledToken pleaseEvaluate() {
            return goal.goal.evaluate(this);
        }
        
        @Override
        protected void signalContinueOrDoneViolation() {
            throw new GoalContinuationContractViolation(goal.goal.getClass().getName() + ".evaluate()",
                    goal.goal);
        }
        
        @Override
        public String toString() {
            return "IGQ() with " + goal;
        }
        
    }
    
    /**
     * R.I.P., QQQ
     */
    final class ContinuationQuery extends QueryImpl {
        
        private final Continuation continuation;
        
        public ContinuationQuery(GoalState goal, Continuation continuation) {
            super(goal);
            Assert.isNotNull(continuation);
            this.continuation = continuation;
        }
        
        @Override
        protected ContinuationRequestorCalledToken pleaseEvaluate() {
            toBeDone.remove(continuation);
            return continuation.done(this);
        }
        
        @Override
        protected void signalContinueOrDoneViolation() {
            throw new GoalContinuationContractViolation(continuation.getClass().getName() + ".done()",
                    goal.goal);
        }
        
        @Override
        public String toString() {
            return "CQ(" + continuation.getClass().getName() + ") with " + goal;
        }
        
    }
    
    /**
     * R.I.P., QQQQ
     */
    final class SimpleContinuationQuery extends QueryImpl {
        
        private final SimpleContinuation continuation;
        
        public SimpleContinuationQuery(GoalState goal, SimpleContinuation continuation) {
            super(goal);
            if (continuation == null)
                throw new NullPointerException("continuation is null");
            this.continuation = continuation;
        }
        
        @Override
        public ContinuationRequestorCalledToken pleaseEvaluate() {
            return continuation.run(this);
        }
        
        @Override
        protected void signalContinueOrDoneViolation() {
            throw new GoalContinuationContractViolation(continuation.getClass().getName() + ".run()",
                    goal.goal);
        }
        
        @Override
        public String toString() {
            return "SCQ(" + continuation.getClass().getName() + ") with " + goal;
        }
        
    }
    
    /**
     * R.I.P., QQQQ_
     */
    
    private final QueryQueue queue = new QueryQueue();
    
    private final Map<Goal<?>, GoalState> activeGoalStates = new HashMap<Goal<?>, GoalState>();
    
    private final GoalDebug debug = new GoalDebug();
    
    private final Map<Goal<?>, Result> cache = new HashMap<Goal<?>, Result>();
    
    private AnalysisStats stats = new AnalysisStats();
    
    private int magicNumber;
    
    private int secondMagicNumber;
    
    <R extends Result> GoalState createGoalState(Goal<R> goal) {
        GoalState state = activeGoalStates.get(goal);
        if (state == null) {
            state = new GoalState(goal);
            activeGoalStates.put(goal, state);
        }
        return state;
    }
    
    public AnalysisStats clearStats() {
        AnalysisStats old = stats;
        stats = new AnalysisStats();
        return old;
    }
    
    public void finished(GoalState state) {
        Goal<?> goal = state.goal;
        storeIntoCache(goal, state.slot.result());
        stats.calculatedGoal(goal);
        debug.finished(goal);
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
    
    @SuppressWarnings("unchecked")
    public <R extends Result> R evaluate(Goal<R> goal) {
        queue.clear();
        magicNumber = 0;
        secondMagicNumber = 0;
        secondMagicSet.clear();
        activeGoalStates.clear();
        toBeDone.clear();
        leaves.clear();
        // TODO: check cache
        GoalState state = createGoalState(goal);
        executeQueue();
        for (GoalState g : secondMagicSet) {
            if (g.subgoalCount == 0) {
                System.out.println(g.monotonicallyIncreasingSubgoalCount + " " + g.evalCount + " " + g.source
                        + " " + g);
            } else {
            }
        }
        if (secondMagicNumber > 0)
            try {
                throw new AssertionError(secondMagicNumber
                        + " goals have not been finalized when calculating " + goal);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        return (R) state.slot.result();
    }
    
    public void executeQueue() {
        Query current;
        while ((current = queue.poll()) != null) {
            //            System.out.println("RUNNING " + current);
            current.evaluate();
        }
    }
    
    public boolean isCached(Goal<?> goal) {
        return cache.containsKey(goal);
    }
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(super.toString());
        return res.toString();
    }
    
    void storeIntoCache(Goal<?> goal, Result result) {
        if (!goal.cachable())
            return;
        cache.put(goal, result);
    }
    
}
