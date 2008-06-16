package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.goals.acceptors.IncrementableSynchronizer;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class IterationGoal extends ContextSensitiveGoal {
    protected final IncrementableSynchronizer incSync;
    private final PythonValueSetAcceptor resultsAcceptor;
    
    public IterationGoal(final PythonValueSetAcceptor resultsAcceptor, Context context) {
        super(context);
        this.resultsAcceptor = resultsAcceptor;
        incSync = new IncrementableSynchronizer(resultsAcceptor) {
            @Override
            public <T> void completed(IGrade<T> grade) {
                updateGrade(resultsAcceptor, Grade.DONE);
            }
        };
    }
    
    protected abstract IterationGoal iteration();
    
    public void preRun() {
        final IterationGoal iteration = iteration();
        IAcceptor nextIter = new IAcceptor() {
            
            public <T> void checkpoint(IGrade<T> grade) {
                if (null != iteration)
                    schedule(iteration);
            }
            
        };
        updateGrade(nextIter, Grade.DONE);
    }
    
    public PythonValueSetAcceptor resultsAcceptor() {
        return resultsAcceptor;
    }
}
