package com.yoursway.sadr.python_v2.goals.acceptors;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.succeeder.IGrade;

/**
 * Used to collect results from multiple acceptors.
 */
public class PvsaDelegate<Acceptor extends PythonValueSetAcceptor> extends PythonValueSetAcceptor {
    
    private final IncrementableSynchronizer<PythonValueSetAcceptor> incSync;
    
    public PvsaDelegate(IncrementableSynchronizer<PythonValueSetAcceptor> incSync, Krocodile context) {
        super(context);
        this.incSync = incSync;
        incSync.addAcceptor(this);
    }
    
    @Override
    public <T> void checkpoint(IGrade<T> grade) {
        super.checkpoint(grade);
        incSync.subgoalDone(grade);
    }
    
    @Override
    protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
        for (Value val : this.getResult().containedValues()) {
            incSync.getTotalResultAcceptor().addResult((RuntimeObject) val, activeContext);//FIXME dirty contextual hack.
        }
    }
    
}
