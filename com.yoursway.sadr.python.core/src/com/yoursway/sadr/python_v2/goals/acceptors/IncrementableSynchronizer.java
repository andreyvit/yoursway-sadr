package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.Collection;
import java.util.LinkedList;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class IncrementableSynchronizer extends Synchronizer {
    
    private final Collection<PythonValueSetAcceptor> acceptors = new LinkedList<PythonValueSetAcceptor>();
    private final PythonValueSetAcceptor totalResultAcceptor;
    private Object object;
    
    public IncrementableSynchronizer(PythonValueSetAcceptor totalResultAcceptor) {
        this.totalResultAcceptor = totalResultAcceptor;
    }
    
    public PythonValueSetAcceptor createAcceptor(final Context context) {
        PythonValueSetAcceptor res = new PythonValueSetAcceptor(context) {
            @Override
            public <T> void checkpoint(IGrade<T> grade) {
                super.checkpoint(grade);
                subgoalDone(grade);
            }
            
            @Override
            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                for (Value val : this.getResult().containedValues()) {
                    totalResultAcceptor.addResult((RuntimeObject) val, context);//FIXME dirty contextual hack.
                }
            }
            
        };
        this.acceptors.add(res);
        counter++;
        return res;
    }
    
    //TODO replace increment-decrement mechanism with something more safe.
    public void increment() {
        assert counter >= 0;
        ++counter;
    }
    
    public void decrement() {
        assert counter > 0;
        if (--counter <= 0)
            completed(Grade.DONE);
    }
    
    public void attachObject(Object object) {
        this.object = object;
    }
    
    protected Object getAttachedObject() {
        return this.object;
    }
}
