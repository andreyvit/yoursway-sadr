package com.yoursway.sadr.core;


public class AnalysisEngine {
    
    static final class SubqueryCreator implements SubgoalRequestor {
        
        private final QueryEnqueuer enqueuer;
        
        private QQ last;
        
        public SubqueryCreator(QueryEnqueuer enqueuer) {
            this.enqueuer = enqueuer;
        }
        
        public void subgoal(Goal goal, Subject subject) {
            last = new QQ(enqueuer, goal, subject);
            enqueuer.enqueue(last);
        }
        
        public void done(Query whenDone) {
            if (last != null)
                last.setWhenDone(whenDone);
            else
                enqueuer.enqueue(whenDone);
        }
        
    }
    
    static abstract class Q extends Query implements ContinuationRequestor {

        protected final QueryEnqueuer enqueuer;
        
        protected final Goal goal;
        
        private Query whenDone;

        public Q(QueryEnqueuer enqueuer, Goal goal) {
            this.enqueuer = enqueuer;
            this.goal = goal;
        }
        
        public Q(Q continuationOf) {
            this(continuationOf.enqueuer, continuationOf.goal);
            this.whenDone = continuationOf.whenDone;
            continuationOf.whenDone = null;
        }
        
        void setWhenDone(Query whenDone) {
            if (whenDone != null)
                throw new IllegalStateException("setWhenDone can oly be called once");
            this.whenDone = whenDone;
        }
        
        @Override
        public void evaluate() {
            try {
                doEvaluate();
            } finally {
                if (whenDone != null)
                    enqueuer.enqueue(whenDone);
            }
        }

        public void subgoal(Continuation cont) {
            SubqueryCreator creator = new SubqueryCreator(enqueuer);
            cont.provideSubgoals(creator);
            creator.done(new QQQ(this, cont));
        }
        
        public void propagate(Subject subject) {
            enqueuer.enqueue(new QQ(enqueuer, goal, subject));
        }

        protected abstract void doEvaluate();
        
    }
    
    static final class QQ extends Q  {

        private final Subject subject;

        public QQ(QueryEnqueuer enqueuer, Goal goal, Subject subject) {
            super(enqueuer, goal);
            this.subject = subject;
        }

        @Override
        public void doEvaluate() {
            subject.process(goal, this);
        }

    }
    
    static final class QQQ extends Q {
        
        private final Continuation continuation;

        public QQQ(Q continuationOf, Continuation continuation) {
            super(continuationOf);
            this.continuation = continuation;
        }

        @Override
        public void doEvaluate() {
            continuation.done(this);
        }
        
    }
    
    private QueryQueue queue = new QueryQueue();
    
    public void evaluate(Goal goal, Subject subject) {
        queue.enqueue(new QQ(queue, goal, subject));
        Query current;
        while ((current = queue.poll()) != null) {
            current.evaluate();
        }
    }
    
}
