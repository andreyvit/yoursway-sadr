/**
 * 
 */
package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public final class PythonVariableDelegatingAcceptor extends PythonVariableAcceptor {
    /**
     * 
     */
    private final PythonValueSetAcceptor acceptor;
    private final Krocodile context;
    
    PythonVariableDelegatingAcceptor(PythonValueSetAcceptor acceptor, Krocodile context) {
        this.acceptor = acceptor;
        this.context = context;
    }
    
    @Override
    public void addResult(String key, RuntimeObject value) {
        acceptor.addResult(value, context);
    }
    
    public <T> void checkpoint(IGrade<T> grade) {
        acceptor.checkpoint(grade);
    }
}