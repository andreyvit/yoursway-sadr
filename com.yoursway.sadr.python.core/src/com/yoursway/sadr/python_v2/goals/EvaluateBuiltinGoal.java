/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.Goal;

public final class EvaluateBuiltinGoal extends Goal {
    private final List<RuntimeObject> args;
    private final FunctionObject callable;
    private final Context context;
    private final PythonValueSetAcceptor acceptor;
    
    public EvaluateBuiltinGoal(List<RuntimeObject> args, FunctionObject callable, Context context,
            PythonValueSetAcceptor acceptor) {
        this.args = args;
        this.callable = callable;
        this.context = context;
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        acceptor.addResult(callable.evaluate(args), context);
        updateGrade(acceptor, Grade.DONE);
    }
    
    @Override
    protected String describe() {
        return "Evaluating function " + callable.name();
    }
}