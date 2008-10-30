/**
 * 
 */
package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.goals.CallRedirectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.succeeder.IGoal;

public final class RedirectFunctionObject extends SyncFunctionObject {
    private final String methodName;
    
    RedirectFunctionObject(String name, String methodName) {
        super(name);
        this.methodName = methodName;
    }
    
    @Override
    public IGoal evaluateGoal(PythonValueSetAcceptor acceptor, final Context context,
            final PythonArguments args) {
        return new CallRedirectGoal(context, acceptor, args, methodName, getDecl());
    }
}