/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;

final public class PassResultGoal extends ExpressionValueGoal {
    private final RuntimeObject result;
    
    public PassResultGoal(Krocodile context, PythonValueSetAcceptor acceptor, RuntimeObject result) {
        super(context, acceptor);
        this.result = result;
    }
    
    public void preRun() {
        if (result != null) {
            acceptor.addResult(result, getContext());
        }
        updateGrade(acceptor, Grade.DONE);
    }
}