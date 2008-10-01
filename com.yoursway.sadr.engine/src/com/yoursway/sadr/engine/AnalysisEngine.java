package com.yoursway.sadr.engine;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
        
        public GoalContinuationContractViolation(String methodName, Goal goal) {
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
        
        public void subgoal(Goal goal) {
            if (writableGoalState.findGoalStateByGoal(goal) != null) {
                goal.causesRecursion();
                debug.recursive(goal);
                stats.recursiveGoal(goal);
                return;
            }
            GoalState state = createGoalState(goal);
            if (state == null)
                return;
            state.addParentAndGoal(writableGoalState, goal);
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
        
        private final List<Goal> goals = newArrayList();
        
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
        
        public GoalState() {
            this.source = GoalSource.ROOT;
            ++magicNumber;
            ++secondMagicNumber;
            secondMagicSet.add(this);
        }
        
        public void addParentAndGoal(GoalState parentState, Goal goal) {
            if (parentState == null)
                throw new NullPointerException("parentState is null");
            parentStates.add(parentState);
            parentState.subgoalAdded(this);
            addGoal(goal);
        }
        
        public void addGoal(Goal goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            boolean isFirstParent = goals.isEmpty();
            if (isFirstParent) {
                activeGoalStates.put(goal, this);
            } else if (!goal.equals(mainGoal()))
                throw new IllegalArgumentException("Can only add equal goals to GoalState");
            goals.add(goal);
            if (isFirstParent)
                queue.enqueue(new InitialGoalQuery(this));
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
            if (goals.isEmpty())
                throw new IllegalStateException("Cannot run on behalf of an empty GoalState");
            Goal goal = mainGoal();
            stats.startingEvaluation(goal);
            ++evalCount;
            runnable.run();
            stats.finishedEvaluation(goal);
        }
        
        public GoalState findGoalStateByGoal(Goal goal) {
            for (Goal myGoal : goals)
                if (goal.equals(myGoal))
                    return this;
            for (GoalState parent : parentStates) {
                GoalState result = parent.findGoalStateByGoal(goal);
                if (result != null)
                    return result;
            }
            return null;
        }
        
        public void markAsDone() {
            if (source == GoalSource.DUPLICATE)
                System.out.println("GoalState.markAsDone(" + this + ")");
            if (subgoalCount >= ADDING_SUBGOALS)
                throw new IllegalStateException(
                        "Cannot mark the goal as done from inside allowStateChangeAndRun");
            if (subgoalCount > 0)
                throw new IllegalStateException(
                        "Cannot mark the goal as done when there are pending subgoals");
            subgoalCount = DONE_COUNT;
            goalFinished();
        }
        
        private void goalFinished() {
            //            System.out.println("FINISHED " + goal);
            --secondMagicNumber;
            secondMagicSet.remove(this);
            activeGoalStates.remove(mainGoal());
            for (GoalState parent : parentStates)
                parent.subgoalFinished(this);
            try {
                Goal mainGoal = mainGoal();
                for (Goal goal : goals) {
                    if (goal != mainGoal)
                        goal.copyAnswerFrom(mainGoal);
                    goal.done();
                }
            } finally {
                finished(this);
            }
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
            if (goals.isEmpty())
                return "<<empty-state>>";
            return "<" + subgoalCount + "," + goals.size() + ">" + mainGoal();
        }
        
        public Goal mainGoal() {
            return goals.get(0);
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
        
        public ContinuationRequestorCalledToken done() {
            goal.markAsDone();
            return DumbReturnValue.instance();
        }
        
        @Override
        public void recursive() {
            goal.markAsDone();
        }
        
        public Query currentQuery() {
            return this;
        }
        
        protected abstract ContinuationRequestorCalledToken pleaseEvaluate();
        
        @Override
        public Goal goal() {
            return goal.mainGoal();
        }
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
            return goal.mainGoal().evaluate(this);
        }
        
        @Override
        protected void signalContinueOrDoneViolation() {
            throw new GoalContinuationContractViolation(goal.mainGoal().getClass().getName() + ".evaluate()",
                    goal.mainGoal());
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
            throw new GoalContinuationContractViolation(continuation.getClass().getName() + ".done()", goal
                    .mainGoal());
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
            throw new GoalContinuationContractViolation(continuation.getClass().getName() + ".run()", goal
                    .mainGoal());
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
    
    private final Map<Goal, GoalState> activeGoalStates = new HashMap<Goal, GoalState>();
    
    private final GoalDebug debug = new GoalDebug();
    
    private final Map<Goal, Result> contextFreeCache = new HashMap<Goal, Result>();
    
    private final Map<Goal, ContextRelation> contextRelationsCache = new HashMap<Goal, ContextRelation>();
    
    private final Map<GoalContext, Result> contextSensitiveCache = new HashMap<GoalContext, Result>();
    
    private AnalysisStats stats = new AnalysisStats();
    
    private int magicNumber;
    
    private int secondMagicNumber;
    
    public AnalysisStats clearStats() {
        AnalysisStats old = stats;
        stats = new AnalysisStats();
        return old;
    }
    
    GoalState createGoalState(Goal goal) {
        Result cachedResult = contextFreeCache.get(goal);
        stats.starting(goal);
        if (cachedResult != null) {
            goal.copyAnswerFrom(cachedResult);
            stats.cacheHit(goal);
            return null;
        }
        GoalState activeGoalState = activeGoalStates.get(goal);
        if (activeGoalState != null)
            return activeGoalState;
        return new GoalState();
    }
    
    public void finished(GoalState state) {
        Goal goal = state.mainGoal();
        storeIntoCache(goal);
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
    
    public void evaluate(Goal goal) {
        queue.clear();
        magicNumber = 0;
        secondMagicNumber = 0;
        secondMagicSet.clear();
        activeGoalStates.clear();
        toBeDone.clear();
        leaves.clear();
        GoalState state = createGoalState(goal);
        if (state == null)
            return;
        state.addGoal(goal);
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
    }
    
    public void executeQueue() {
        Query current;
        while ((current = queue.poll()) != null) {
            //            System.out.println("RUNNING " + current);
            current.evaluate();
            Goal goal = current.goal();
            if (goal != null)
                stats.finishedQuery(goal);
        }
    }
    
    public boolean isCached(Goal goal) {
        return contextFreeCache.containsKey(goal);
    }
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(super.toString());
        return res.toString();
    }
    
    void storeIntoCache(Goal goal) {
        if (!goal.cachable())
            return;
        if (goal.isContextFree())
            contextFreeCache.put(goal, goal.roughResult());
        else {
            ContextRelation relation = goal.contextRelation();
            
            ContextRelation existingRelation = contextRelationsCache.get(goal);
            if (existingRelation == null)
                contextRelationsCache.put(goal, relation);
            else if (!existingRelation.equals(relation))
                contextRelationsCache.put(goal, existingRelation.extend(relation));
            
            GoalContext context = relation.createPrimaryContext(goal);
            contextSensitiveCache.put(context, goal.roughResult());
        }
    }
    
}
