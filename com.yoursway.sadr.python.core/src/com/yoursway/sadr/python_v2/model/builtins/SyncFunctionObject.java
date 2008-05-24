package com.yoursway.sadr.python_v2.model.builtins;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGoal;

public class SyncFunctionObject extends FunctionObject {
    
    public SyncFunctionObject(String name) {
        super(name);
    }
    
    @Override
    public IGoal evaluateGoal(PythonValueSetAcceptor acceptor, final Context context,
            final PythonArguments args) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                RuntimeObject result = SyncFunctionObject.this.evaluate(args);
                acceptor.addResult(result, context);
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                return "ExpressionValueGoal for function " + SyncFunctionObject.this.name();
            }
        };
    }
    
    public RuntimeObject evaluate(PythonArguments args) {
        throw new NotImplementedException();
    }
}
