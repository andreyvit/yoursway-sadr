package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class SimpleSynchronizer extends Synchronizer {
    private boolean adding = true;
    
    @Override
    final public <K> void subgoalDone(IGrade<K> grade) {
        if (adding)
            throw new IllegalStateException("Done signal not possible in init stage.");
        super.subgoalDone(grade);
    }
    
    public IAcceptor createAcceptor() {
        if (!adding)
            throw new IllegalStateException("Adding not available in waiting stage.");
        ++counter;
        return new IAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                if (grade.isDone())
                    SimpleSynchronizer.this.subgoalDone(grade);
            }
        };
    }
    
    final public void startCollecting() {
        adding = false;
        if (counter <= 0)
            completed(Grade.DONE);
    }
    
}
