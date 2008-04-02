package com.yoursway.sadr.engine;

import java.util.HashMap;
import java.util.Map;

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
        
        public abstract void register(QQ qq, Q parent);
        
    }
    
    final class DontAddAddition extends Addition {
        @Override
        public void register(QQ qq, Q parent) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean shouldCreateQuery() {
            return false;
        }
    }
    
    final class AddAddition extends Addition {
        @Override
        public void register(QQ qq, Q parent) {
            System.out.println("START of:           " + qq.goal());
            System.out.println("    ^-- because of: " + parent.goal());
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
        public void register(QQ qq, Q parent) {
            System.out.println("WAITING:            " + qq.goal());
            System.out.println("    ^-- because of: " + parent.goal());
            sameGoals.put(activeGoal, qq);
        }
        
        @Override
        public boolean shouldCreateQuery() {
            return true;
        }
        
    }
    
    private static final int CONTINUATION_CREATED = -1;
    
    private static final int DONE_CALLED = -2;
    
    static final class GoalContinuationContractViolation extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        public GoalContinuationContractViolation(String methodName, Goal goal) {
            super(String.format("%s did not create a continuation or call done, goal %s", methodName, String
                    .valueOf(goal)));
        }
        
    }
    
    final class SubqueryCreator implements SubgoalRequestor {
        
        private final Q parent;
        private final QQQ whenDone;
        private boolean anyChildrenCreated = false;
        
        public SubqueryCreator(Q parent, QQQ whenDone) {
            this.parent = parent;
            this.whenDone = whenDone;
        }
        
        public void subgoal(Goal goal) {
            Addition addition = start(goal, parent);
            if (addition.shouldCreateQuery()) {
                QQ qq = new QQ(goal);
                qq.setParent(whenDone);
                whenDone.incrementChildrenCount();
                anyChildrenCreated = true;
                addition.register(qq, parent);
            }
        }
        
        public void done() {
            if (!anyChildrenCreated) {
                System.out.println("ENQUEUE of:         " + whenDone.goal());
                System.out.println("    ^-- because no children were created");
                queue.enqueue(whenDone);
            }
        }
        
    }
    
    abstract class Q extends Query implements ContinuationScheduler {
        
        protected final Goal goal;
        
        private Query parent = null;
        
        private int childrenCountOrState = 0;
        
        public Q(Goal goal) {
            this.goal = goal;
        }
        
        public Q(Q continuationOf) {
            this(continuationOf.goal);
            this.parent = continuationOf.parent;
            continuationOf.continuationCreated();
        }
        
        private void continuationCreated() {
            if (childrenCountOrState > 0)
                throw new IllegalStateException("continuationCreated with childrenCount > 0");
            childrenCountOrState = CONTINUATION_CREATED;
        }
        
        private boolean isContinuationCreated() {
            return childrenCountOrState == CONTINUATION_CREATED;
        }
        
        void setParent(Query parent) {
            if (this.parent != null)
                throw new IllegalStateException("setWhenDone can oly be called once");
            this.parent = parent;
        }
        
        @Override
        public void evaluate() {
            stats.startingEvaluation(goal);
            pleaseEvaluate();
            maybeDone(); // TODO wrap in finally
            stats.finishedEvaluation(goal);
        }
        
        protected void maybeDone() {
            if (!isContinuationCreated()) {
                if (childrenCountOrState != DONE_CALLED)
                    signalContinueOrDoneViolation();
                handleFinished();
            }
        }
        
        public void handleFinished() {
            goal.done();
            storeIntoCache();
            finished(goal, (parent == null ? null : parent.goal()));
            signalFinishedToParent();
            for (QQ qq : sameGoals.get(goal)) {
                qq.goal().copyAnswerFrom(goal);
                qq.handleFinished();
            }
        }
        
        protected void signalFinishedToParent() {
            if (parent != null)
                ((Q) parent).decrementChildrenCount(goal);
        }
        
        protected abstract void signalContinueOrDoneViolation();
        
        protected abstract void storeIntoCache();
        
        private void decrementChildrenCount(Goal reason) {
            if (childrenCountOrState <= 0)
                throw new IllegalStateException("childrenCount should be greater than zero");
            if (--childrenCountOrState == 0) {
                System.out.println("ENQUEUE of:         " + goal);
                System.out.println("    ^-- because of: " + reason);
                queue.enqueue(this);
            }
        }
        
        public ContinuationRequestorCalledToken schedule(Continuation cont) {
            QQQ qqq = new QQQ(this, cont);
            SubqueryCreator creator = new SubqueryCreator(this, qqq);
            cont.provideSubgoals(creator);
            creator.done();
            return DumbReturnValue.instance();
        }
        
        public ContinuationRequestorCalledToken schedule(SimpleContinuation cont) {
            queue.enqueue(new ContinuationQuery(cont));
            return DumbReturnValue.instance();
        }
        
        public ContinuationRequestorCalledToken done() {
            if (isContinuationCreated())
                throw new IllegalStateException(
                        "ContinuationRequestor.done() called after creating a continuation");
            if (childrenCountOrState != 0)
                throw new AssertionError("ContinuationRequestor.done() called when childrenCount > 0");
            childrenCountOrState = DONE_CALLED;
            return DumbReturnValue.instance();
        }
        
        @Override
        public void recursive() {
            maybeDone();
        }
        
        @Override
        public Q parent() {
            return (Q) parent;
        }
        
        public Query currentQuery() {
            return this;
        }
        
        protected abstract void pleaseEvaluate();
        
        @Override
        public Goal goal() {
            return goal;
        }
        
        public void incrementChildrenCount() {
            ++childrenCountOrState;
        }
        
    }
    
    abstract class QWithContextDependence extends Q {
        
        public QWithContextDependence(Goal goal) {
            super(goal);
        }
        
        public QWithContextDependence(Q continuationOf) {
            super(continuationOf);
        }
        
        @Override
        protected void storeIntoCache() {
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
    
    final class QQ extends QWithContextDependence {
        
        public QQ(Goal goal) {
            super(goal);
        }
        
        @Override
        protected void pleaseEvaluate() {
            ContextRelation contextRelation = contextRelationsCache.get(goal);
            ContinuationScheduler requestor = this;
            evaluateWithRequestorAndContextRelation(contextRelation, requestor);
        }
        
        private void evaluateWithRequestorAndContextRelation(final ContextRelation contextRelation,
                ContinuationScheduler requestor) {
            if (contextRelation != null) {
                contextRelation.createSecondaryContext(goal, requestor, new ContextRequestor() {
                    
                    public void execute(GoalContext context, ContinuationScheduler requestor) {
                        Result result = contextSensitiveCache.get(context);
                        if (result != null) {
                            goal.copyAnswerFrom(result);
                            contextRelation.addTo(goal);
                        } else {
                            evaluateWithRequestor(requestor);
                        }
                    }
                    
                });
            } else {
                evaluateWithRequestor(requestor);
            }
        }
        
        private ContinuationRequestorCalledToken evaluateWithRequestor(ContinuationScheduler rq) {
            return goal.evaluate(rq);
        }
        
        @Override
        protected void signalContinueOrDoneViolation() {
            throw new GoalContinuationContractViolation(goal.getClass().getName() + ".evaluate()", goal);
        }
    }
    
    final class QQQ extends QWithContextDependence {
        
        private final Continuation continuation;
        
        public QQQ(Q continuationOf, Continuation continuation) {
            super(continuationOf);
            this.continuation = continuation;
        }
        
        @Override
        protected void pleaseEvaluate() {
            continuation.done(this);
        }
        
        @Override
        protected void signalContinueOrDoneViolation() {
            throw new GoalContinuationContractViolation(continuation.getClass().getName() + ".done()", goal);
        }
        
    }
    
    /**
     * FIXME: remove this НАХРЕН
     * 
     * @author mag
     * 
     */
    final class ContinuationQuery extends Q {
        
        private final SimpleContinuation continuation;
        
        public ContinuationQuery(SimpleContinuation continuation) {
            //super(new DummyGoal());
            super((Goal) null);
            this.continuation = continuation;
        }
        
        @Override
        public Q parent() {
            return null;
        }
        
        @Override
        public void recursive() {
        }
        
        @Override
        public void evaluate() {
            continuation.run(this);
        }
        
        @Override
        protected void signalContinueOrDoneViolation() {
            throw new GoalContinuationContractViolation(continuation.getClass().getName() + ".run()", goal);
        }
        
        @Override
        protected void storeIntoCache() {
        }
        
        @Override
        protected void pleaseEvaluate() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    private final QueryQueue queue = new QueryQueue();
    
    private final Map<Goal, Goal> activeGoals = new HashMap<Goal, Goal>();
    
    private final GoalDebug debug = new GoalDebug();
    
    private final Map<Goal, Result> contextFreeCache = new HashMap<Goal, Result>();
    
    private final Map<Goal, ContextRelation> contextRelationsCache = new HashMap<Goal, ContextRelation>();
    
    private final Map<GoalContext, Result> contextSensitiveCache = new HashMap<GoalContext, Result>();
    
    private final AbstractMultiMap<Goal, QQ> sameGoals = new IdentityArrayListHashMultiMap<Goal, QQ>();
    
    private AnalysisStats stats = new AnalysisStats();
    
    public AnalysisStats clearStats() {
        AnalysisStats old = stats;
        stats = new AnalysisStats();
        return old;
    }
    
    private Addition start(Goal goal, Q parent) {
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
    
    private boolean isRecursive(Goal goal, Q parent) {
        for (Q q = parent; q != null; q = q.parent()) {
            Goal activeGoal = q.goal();
            if (goal.hashCode() == activeGoal.hashCode())
                if (goal.equals(activeGoal))
                    return true;
        }
        return false;
    }
    
    private int recursionDepth(Goal goal, Q parent) {
        int depth = 0;
        for (Q q = parent; q != null; q = q.parent()) {
            Goal activeGoal = q.goal();
            if (goal.hashCode() == activeGoal.hashCode())
                if (goal.equals(activeGoal))
                    ++depth;
        }
        return depth;
    }
    
    public void finished(Goal goal, Goal parent) {
        stats.calculatedGoal(goal);
        activeGoals.remove(goal);
        debug.finished(goal, parent);
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
        queue.enqueue(new QQ(goal));
        executeQueue();
    }
    
    public void executeQueue() {
        Query current;
        while ((current = queue.poll()) != null) {
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
    
    public void execute(SimpleContinuation continuation) {
        queue.enqueue(new ContinuationQuery(continuation));
        executeQueue();
    }
}
