/**
 * 
 */
package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGrade;

public final class PythonVariableDelegatingAcceptor extends PythonVariableAcceptor {
    /**
     * 
     */
    private final PythonValueSetAcceptor acceptor;
    private final Context context;
    
    PythonVariableDelegatingAcceptor(PythonValueSetAcceptor acceptor, Context context) {
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