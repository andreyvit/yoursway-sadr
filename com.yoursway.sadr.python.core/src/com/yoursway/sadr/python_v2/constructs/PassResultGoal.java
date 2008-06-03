/**
 * 
 */
package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;

final public class PassResultGoal extends ExpressionValueGoal {
    private final RuntimeObject result;
    
    public PassResultGoal(Context context, PythonValueSetAcceptor acceptor, RuntimeObject result) {
        super(context, acceptor);
        this.result = result;
    }
    
    public void preRun() {
        if (result != null) {
            acceptor.addResult(result, getContext());
            updateGrade(acceptor, Grade.DONE);
        }
    }
}