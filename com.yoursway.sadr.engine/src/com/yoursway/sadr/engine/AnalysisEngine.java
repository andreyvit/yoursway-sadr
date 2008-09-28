package com.yoursway.sadr.engine;

import static com.yoursway.utils.broadcaster.BroadcasterFactory.newBroadcaster;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import com.yoursway.sadr.engine.spi.CacheImpl;
import com.yoursway.sadr.engine.spi.GoalEngine;
import com.yoursway.sadr.engine.spi.GoalEngineImpl;
import com.yoursway.sadr.engine.spi.GoalState;
import com.yoursway.sadr.engine.spi.GoalStateFactory;
import com.yoursway.sadr.engine.spi.PossibleSubgoalsAdder;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.IdentityArrayListHashMultiMap;
import com.yoursway.utils.EventSource;
import com.yoursway.utils.broadcaster.Broadcaster;

/**
 * Continuations evaluator. Just invoke new
 * AnalysisEngine().execute(yourSimpleContinuation) and it will provide
 * scheduler for this continuation and all tasks scheduled by it.
 */
public class AnalysisEngine implements GoalEngine {
    
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
                GoalState newState = createState(writableGoalState, goal);
                addition.register(new InitialGoalQuery(newState), oldQuery);
            }
        }
        
        public void runAndPossiblyAddSubgoals(GoalState writableGoalState) {
            this.writableGoalState = writableGoalState;
            provider.provideSubgoals(this);
        }
        
    }
    
    /**
     * R.I.P., Q
     */
    abstract class QueryImpl implements Query, ContinuationScheduler, Runnable {
        
        protected final GoalState goal;
        
        public QueryImpl(GoalState goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.goal = goal;
        }
        
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
        
        public Query currentQuery() {
            return this;
        }
        
        protected abstract ContinuationRequestorCalledToken pleaseEvaluate();
        
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
    
    private final AbstractMultiMap<Goal, GoalState> sameGoals = new IdentityArrayListHashMultiMap<Goal, GoalState>();
    
    private final Broadcaster<AnalysisEngineListener> broadcaster = newBroadcaster(AnalysisEngineListener.class);
    
    private final GoalStateFactory stateFactory;
    
    public AnalysisEngine() {
        GoalEngine ge = new GoalEngineImpl(queue);
        stateFactory = new CacheImpl(ge);
    }
    
    public EventSource<AnalysisEngineListener> events() {
        return broadcaster;
    }
    
    private AnalysisStats stats = new AnalysisStats();
    
    public AnalysisStats clearStats() {
        AnalysisStats old = stats;
        stats = new AnalysisStats();
        return old;
    }
    
    GoalState createState(GoalState parentState, Goal goal) {
        GoalState state = stateFactory.createState(parentState, goal);
        if (parentState != null && state.goal != goal) {
            GoalState previousState = parentState.findStateByGoal(goal);
            if (previousState != null) {
                broadcaster.fire().recursiveGoal(parentState, goal, duplicate);
                goal.causesRecursion();
            } else {
                // TODO
                sameGoals.put(goal, parentState);
            }
            return null;
        }
        return state;
    }
    
    public void evaluate(Goal goal) {
        Addition addition = start(goal, null);
        if (!addition.shouldCreateQuery())
            return;
        queue.enqueue(new InitialGoalQuery(createGoalState(goal)));
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
    
    public void enqueueComputation(Query query) {
    }
    
    public void finishedGoal(GoalState state) {
        Goal goal = state.goal;
        storeIntoCache(goal);
        stats.calculatedGoal(goal);
        activeGoals.remove(goal);
        debug.finished(goal);
        
        for (GoalState qq : sameGoals.get(goal)) {
            qq.goal.copyAnswerFrom(goal);
            qq.markAsDone();
        }
    }
    
}
