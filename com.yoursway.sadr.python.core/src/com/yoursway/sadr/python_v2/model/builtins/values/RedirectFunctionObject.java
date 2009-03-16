package com.yoursway.sadr.python_v2.model.builtins.values;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallRedirectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;

public final class RedirectFunctionObject extends BuiltinFunctionObject {
    private final String methodName;
    
    public RedirectFunctionObject(String name, String methodName) {
        super(name);
        this.methodName = methodName;
    }
    
    @Override
    public PythonValueSet call(final Krocodile context, final RuntimeArguments args) {
        return new CallRedirectGoal(context, args, methodName, getDecl()).evaluate();
    }
}