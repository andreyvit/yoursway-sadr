package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.goals.acceptors.IncrementableSynchronizer;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGoal;
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
                IGoal iteration = (IGoal) getAttachedObject();
                if (null != iteration)
                    schedule(iteration);
                else
                    updateGrade(resultsAcceptor, Grade.DONE);
            }
        };
    }
    
    protected abstract IterationGoal iteration();
    
    public void preRun() {
        final IterationGoal iteration = iteration();
        incSync.attachObject(iteration);
        IAcceptor nextIter = incSync.createAcceptor(getContext());
        updateGrade(nextIter, Grade.DONE);
    }
    
    public PythonValueSetAcceptor resultsAcceptor() {
        return resultsAcceptor;
    }
}
