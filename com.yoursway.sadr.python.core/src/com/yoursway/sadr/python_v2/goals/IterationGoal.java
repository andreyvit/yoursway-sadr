package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.goals.acceptors.IncrementableSynchronizer;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class IterationGoal<AcceptorType extends IAcceptor> extends ContextSensitiveGoal {
    protected final IncrementableSynchronizer<AcceptorType> incSync;
    private final AcceptorType resultsAcceptor;
    
    public IterationGoal(final AcceptorType resultsAcceptor, Context context) {
        super(context);
        this.resultsAcceptor = resultsAcceptor;
        incSync = new IncrementableSynchronizer<AcceptorType>(resultsAcceptor) {
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
    
    protected abstract IterationGoal<AcceptorType> iteration();
    
    public void preRun() {
        final IterationGoal<AcceptorType> iteration = iteration();
        incSync.attachObject(iteration);
        incSync.increment();
        incSync.decrement();
    }
    
    public AcceptorType resultsAcceptor() {
        return resultsAcceptor;
    }
}
