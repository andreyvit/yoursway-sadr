/**
 * 
 */
package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallRedirectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public final class RedirectFunctionObject extends SyncFunctionObject {
    private final String methodName;
    
    RedirectFunctionObject(String name, String methodName) {
        super(name);
        this.methodName = methodName;
    }
    
    @Override
    public PythonValueSet call(final Krocodile context, final PythonArguments args) {
        return new CallRedirectGoal(context, args, methodName, getDecl()).evaluate();
    }
}