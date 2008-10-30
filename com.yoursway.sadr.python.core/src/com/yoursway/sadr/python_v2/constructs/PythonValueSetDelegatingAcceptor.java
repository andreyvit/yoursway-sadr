/**
 * 
 */
package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGrade;

final class PythonValueSetDelegatingAcceptor extends PythonValueSetAcceptor {
    private final String name;
    private final PythonVariableAcceptor acceptor;
    
    PythonValueSetDelegatingAcceptor(Context activeContext, String name,
            PythonVariableAcceptor acceptor) {
        super(activeContext);
        this.name = name;
        this.acceptor = acceptor;
    }
    
    @Override
    protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
        if (result != null) {
            acceptor.addResult(name, result);
        }
    }
}