package com.yoursway.sadr.engine;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.IdentityArrayListHashMultiMap;

/**
 * Continuations evaluator. Just invoke new
 * AnalysisEngine().execute(yourSimpleContinuation) and it will provide
 * scheduler for this continuation and all tasks scheduled by it.
 */
public class AnalysisEngine {
    
    private static final int MAX_ITERATIONS = 1000;
    
    abstract class Addition {
        
        public abstract boolean shouldCreateQuery();
        
        public abstract void register(InitialGoalQuery qq, QueryImpl parent);
        
    }
    
    final class DontAddAddition extends Addition {
        @Override
        public void register(InitialGoalQuery qq, QueryImpl parent) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean shouldCreateQuery() {
            return false;
        }
    }
    
    final class AddAddition extends Addition {
        @Override
        public void register(InitialGoalQuery qq, QueryImpl parent) {
            //            System.out.println("START of:           " + qq.goal());
            //            System.out.println("    ^-- because of: " + parent.goal());
            queue.enqueue(qq);
        }
        
        @Override
        public boolean shouldCreateQuery() {
            return true;
        }
    }
    
    public final class SameExistsAddition extends Addition {
        
        private final Goal activeGoal;
        
        public SameExistsAddition(Goal activeGoal) {
            this.activeGoal = activeGoal;
        }
        
        @Override
        public void register(InitialGoalQuery qq, QueryImpl parent) {
            //            System.out.println("WAITING:            " + qq.goal());
            //            System.out.println("    ^-- because of: " + parent.goal());
            sameGoals.put(activeGoal, qq.goal);
        }
        
        @Override
        public boolean shouldCreateQuery() {
            return true;
        }
        
    }
    
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
        
        private final QueryImpl oldQuery;
        private final SubgoalsProvider provider;
        private GoalState writableGoalState;
        
        public SubqueryCreator(SubgoalsProvider provider, QueryImpl oldQuery) {
            this.provider = provider;
            this.oldQuery = oldQuery;
        }
        
        public void subgoal(Goal goal) {
            Addition addition = start(goal, oldQuery);
            if (addition.shouldCreateQuery()) {
                GoalState newState = new GoalState(writableGoalState, goal);
                addition.register(new InitialGoalQuery(newState), oldQuery);
            }
        }
        
        public void runAndPossiblyAddSubgoals(GoalState writableGoalState) {
            this.writableGoalState = writableGoalState;
            provider.provideSubgoals(this);
        }
        
    }
    
    interface PossibleSubgoalsAdder {
        
        void runAndPossiblyAddSubgoals(GoalState writableGoalState);
        
    }
    
    class GoalState {
        
        private static final int DONE_COUNT = -1;
        
        private static final int ADDING_SUBGOALS = 1000000;
        
        private final GoalState parentGoal;
        
        private final Goal goal;
        
        /**
         * Either a count of subgoals, or <code>DONE_COUNT</code> when this goal
         * is done, or <code>ADDING_SUBGOALS</code> plus a count of subgoals
         * during execution of <code>allowStateChangeAndRun</code>.
         */
        private int subgoalCount = 0;
        
        private Query allChildrenFinishedCallback;
        
        // (subgoalCount > 0) <=> (job != null)
        
        public GoalState(Goal goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.parentGoal = null;
            this.goal = goal;
        }
        
        public GoalState(GoalState parentGoal, Goal goal) {
            if (parentGoal == null)
                throw new NullPointerException("parentGoal is null");
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.parentGoal = parentGoal;
            this.goal = goal;
            parentGoal.subgoalAdded();
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
                if (subgoalCount == 0)
                    queue.enqueue(allChildrenFinishedCallback);
                else if (subgoalCount > 0)
                    this.allChildrenFinishedCallback = allChildrenFinishedCallback;
                else
                    throw new AssertionError(
                            "Invalid value of the internal subgoal count (after runAndPossiblyAddSubgoals)");
            }
            
        }
        
        public void runOnBehalfOfThisGoal(Runnable runnable) {
            stats.startingEvaluation(goal);
            runnable.run();
            stats.finishedEvaluation(goal);
        }
        
        public void markAsDone() {
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
            if (parentGoal != null)
                parentGoal.subgoalFinished(goal);
            try {
                goal.done();
            } finally {
                finished(goal);
            }
        }
        
        void subgoalAdded() {
            if (subgoalCount < ADDING_SUBGOALS)
                if (subgoalCount == DONE_COUNT)
                    throw new IllegalStateException("Cannot add subgoals when the goal is done");
                else
                    throw new IllegalStateException("Cannot add subgoals from outside allowStateChangeAndRun");
            ++subgoalCount;
        }
        
        void subgoalFinished(Goal subgoal) {
            if (subgoalCount == DONE_COUNT)
                throw new IllegalStateException("Subgoal cannot be finished when the goal is already done");
            if (subgoalCount == 0)
                throw new IllegalStateException("This goal has no subgoals");
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
            return "<" + subgoalCount + ">" + goal;
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
            goal.allowStateChangeAndRun(new SubqueryCreator(cont, this), new ContinuationQuery(goal, cont));
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
            return goal.goal;
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
    
    private final Map<Goal, Goal> activeGoals = new HashMap<Goal, Goal>();
    
    private final GoalDebug debug = new GoalDebug();
    
    private final Map<Goal, Result> contextFreeCache = new HashMap<Goal, Result>();
    
    private final Map<Goal, ContextRelation> contextRelationsCache = new HashMap<Goal, ContextRelation>();
    
    private final Map<GoalContext, Result> contextSensitiveCache = new HashMap<GoalContext, Result>();
    
    private final AbstractMultiMap<Goal, GoalState> sameGoals = new IdentityArrayListHashMultiMap<Goal, GoalState>();
    
    private AnalysisStats stats = new AnalysisStats();
    
    public AnalysisStats clearStats() {
        AnalysisStats old = stats;
        stats = new AnalysisStats();
        return old;
    }
    
    private Addition start(Goal goal, QueryImpl parent) {
        Goal parentGoal = parent == null ? null : parent.goal();
        Result cachedResult = contextFreeCache.get(goal);
        stats.starting(goal);
        if (cachedResult != null) {
            goal.copyAnswerFrom(cachedResult);
            stats.cacheHit(goal);
            return new DontAddAddition();
        }
        Goal activeGoal = activeGoals.get(goal);
        if (activeGoal != null
                && !(contextRelationsCache.containsKey(goal))
                && (!goal.hasComplexUnnaturalRelationshipWithRecursion() || recursionDepth(goal, parent) > MAX_ITERATIONS)) {
            if (isRecursive(goal, parent)) {
                goal.causesRecursion();
                debug.recursive(goal, parentGoal);
                stats.recursiveGoal(goal);
                return new DontAddAddition();
            } else {
                return new SameExistsAddition(activeGoal);
            }
        } else {
            activeGoals.put(goal, goal);
            debug.starting(goal, parentGoal);
            return new AddAddition();
        }
    }
    
    private boolean isRecursive(Goal goal, QueryImpl parent) {
        for (GoalState q = parent.goal; q != null; q = q.parentGoal) {
            Goal activeGoal = q.goal;
            if (activeGoal != null && goal.hashCode() == activeGoal.hashCode())
                if (goal.equals(activeGoal))
                    return true;
        }
        return false;
    }
    
    private int recursionDepth(Goal goal, QueryImpl parent) {
        int depth = 0;
        for (GoalState q = parent.goal; q != null; q = q.parentGoal) {
            Goal activeGoal = q.goal;
            if (activeGoal != null && goal.hashCode() == activeGoal.hashCode())
                if (goal.equals(activeGoal))
                    ++depth;
        }
        return depth;
    }
    
    public void finished(Goal goal) {
        storeIntoCache(goal);
        stats.calculatedGoal(goal);
        activeGoals.remove(goal);
        debug.finished(goal);
        
        for (GoalState qq : sameGoals.get(goal)) {
            qq.goal.copyAnswerFrom(goal);
            qq.markAsDone();
        }
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
        Addition addition = start(goal, null);
        if (!addition.shouldCreateQuery())
            return;
        queue.enqueue(new InitialGoalQuery(new GoalState(goal)));
        executeQueue();
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
