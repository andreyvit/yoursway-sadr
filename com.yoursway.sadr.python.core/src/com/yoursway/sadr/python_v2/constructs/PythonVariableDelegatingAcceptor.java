/**
 * 
 */
package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.succeeder.IGrade;

public final class PythonVariableDelegatingAcceptor extends PythonVariableAcceptor {
    /**
     * 
     */
    private final PythonValueSet acceptor;
    private final Krocodile context;
    
    public PythonVariableDelegatingAcceptor(PythonValueSet acceptor, Krocodile context) {
        this.acceptor = acceptor;
        this.context = context;
    }
    
    @Override
    public void addResult(String key, PythonObject value) {
        acceptor.addResult(value, context);
    }
    
    public <T> void checkpoint(IGrade<T> grade) {
        acceptor.checkpoint(grade);
    }
}