package com.yoursway.sadr.engine;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.IdentityArrayListHashMultiMap;

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
    
    abstract class Q extends Query implements ContinuationRequestor {
        
        protected final Goal goal;
        
        private Query parent = null;
        
        private int childrenCount = 0;
        
        public Q(Goal goal) {
            this.goal = goal;
        }
        
        public Q(Q continuationOf) {
            this(continuationOf.goal);
            this.parent = continuationOf.parent;
            continuationOf.continuationCreated();
        }
        
        private void continuationCreated() {
            if (childrenCount > 0)
                throw new IllegalStateException("continuationCreated with childrenCount > 0");
            childrenCount = CONTINUATION_CREATED;
        }
        
        private boolean isContinuationCreated() {
            return childrenCount < 0;
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
            done(); // TODO wrap in finally
            stats.finishedEvaluation(goal);
        }
        
        private void done() {
            if (!isContinuationCreated()) {
                handleFinished();
            }
        }
        
        public void handleFinished() {
            goal.done();
            storeIntoCache();
            finished(goal, (parent == null ? null : parent.goal()));
            if (parent != null)
                ((Q) parent).decrementChildrenCount(goal);
            for (QQ qq : sameGoals.get(goal)) {
                qq.goal().copyAnswerFrom(goal);
                qq.handleFinished();
            }
        }
        
        protected abstract void storeIntoCache();
        
        private void decrementChildrenCount(Goal reason) {
            if (childrenCount <= 0)
                throw new IllegalStateException("childrenCount should be greater than zero");
            if (--childrenCount == 0) {
                System.out.println("ENQUEUE of:         " + goal);
                System.out.println("    ^-- because of: " + reason);
                queue.enqueue(this);
            }
        }
        
        public void subgoal(Continuation cont) {
            QQQ qqq = new QQQ(this, cont);
            SubqueryCreator creator = new SubqueryCreator(this, qqq);
            cont.provideSubgoals(creator);
            creator.done();
        }
        
        @Override
        public void recursive() {
            done();
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
            ++childrenCount;
        }
        
    }
    
    abstract class QWithKarma extends Q {
        
        public QWithKarma(Goal goal) {
            super(goal);
        }
        
        public QWithKarma(Q continuationOf) {
            super(continuationOf);
        }
        
        @Override
        protected void storeIntoCache() {
            if (!goal.cachable())
                return;
            if (goal.isSaint())
                heaven.put(goal, goal.resultWithoutKarma());
            else {
                Karma karma = goal.karma();
                
                Karma existingKarma = karmaCache.get(goal);
                if (existingKarma == null)
                    karmaCache.put(goal, karma);
                else if (!existingKarma.equals(karma))
                    karmaCache.put(goal, existingKarma.takeMoreBlame(karma));
                
                GoalConfession confession = karma.createPrimaryConfession(goal);
                hell.put(confession, goal.resultWithoutKarma());
            }
        }
    }
    
    final class QQ extends QWithKarma {
        
        public QQ(Goal goal) {
            super(goal);
        }
        
        @Override
        protected void pleaseEvaluate() {
            Karma karma = karmaCache.get(goal);
            ContinuationRequestor requestor = this;
            evaluateWithRequestorAndKarma(karma, requestor);
        }
        
        private void evaluateWithRequestorAndKarma(final Karma karma, ContinuationRequestor requestor) {
            if (karma != null) {
                karma.createSecondaryConfession(goal, requestor, new ConfessionRequestor() {
                    
                    public void execute(GoalConfession confession, ContinuationRequestor requestor) {
                        Result result = hell.get(confession);
                        if (result != null) {
                            goal.copyAnswerFrom(result);
                            karma.putBlameOn(goal);
                        } else {
                            evaluateWithRequestor(requestor);
                        }
                    }
                    
                });
            } else {
                evaluateWithRequestor(requestor);
            }
        }
        
        private void evaluateWithRequestor(ContinuationRequestor rq) {
            goal.evaluate(rq);
        }
        
    }
    
    final class QQQ extends QWithKarma {
        
        private final Continuation continuation;
        
        public QQQ(Q continuationOf, Continuation continuation) {
            super(continuationOf);
            this.continuation = continuation;
        }
        
        @Override
        protected void pleaseEvaluate() {
            continuation.done(this);
        }
        
    }
    
    private final QueryQueue queue = new QueryQueue();
    
    private final Map<Goal, Goal> activeGoals = new HashMap<Goal, Goal>();
    
    private final GoalDebug debug = new GoalDebug();
    
    private final Map<Goal, Result> heaven = new HashMap<Goal, Result>();
    
    private final Map<Goal, Karma> karmaCache = new HashMap<Goal, Karma>();
    
    private final Map<GoalConfession, Result> hell = new HashMap<GoalConfession, Result>();
    
    private final AbstractMultiMap<Goal, QQ> sameGoals = new IdentityArrayListHashMultiMap<Goal, QQ>();
    
    private AnalysisStats stats = new AnalysisStats();
    
    public AnalysisStats clearStats() {
        AnalysisStats old = stats;
        stats = new AnalysisStats();
        return old;
    }
    
    private Addition start(Goal goal, Q parent) {
        Goal parentGoal = parent == null ? null : parent.goal();
        Result cachedResult = heaven.get(goal);
        stats.starting(goal);
        if (cachedResult != null) {
            goal.copyAnswerFrom(cachedResult);
            stats.cacheHit(goal);
            return new DontAddAddition();
        }
        Goal activeGoal = activeGoals.get(goal);
        if (activeGoal != null
                && !(karmaCache.containsKey(goal))
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
        Query current;
        while ((current = queue.poll()) != null) {
            current.evaluate();
            stats.finishedQuery(current.goal());
        }
    }
    
    public boolean isCached(Goal goal) {
        return heaven.containsKey(goal);
    }
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(super.toString());
        return res.toString();
    }
    
}
