package com.yoursway.sadr.python_v2.goals.internal;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.goals.CallReturnValueGoal;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonLambdaExpressionC;
import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IGoal;

public final class CallResolver {
    public static IGoal callMethod(RuntimeObject receiver, String methodName, List<RuntimeObject> actualArgs,
            PythonValueSetAcceptor acceptor, Context context) {
        RuntimeObject callable = receiver.getAttribute(methodName);
        if (!(callable instanceof FunctionObject)) {
            throw new IllegalArgumentException("callable should be FunctionObject");
            
        }
        if (receiver.getDict().containsKey(methodName)) {
            return callFunction((FunctionObject) callable, actualArgs, acceptor, context);
        } else {
            actualArgs.add(0, receiver);
            return callFunction((FunctionObject) callable, actualArgs, acceptor, context);
        }
    }
    
    public static IGoal callFunction(final FunctionObject callable, final List<RuntimeObject> args,
            final PythonValueSetAcceptor acceptor, final Context context) {
        if (callable.getDecl() == null) {
            return new Goal() {
                
                @Override
                public CheckpointToken flush() {
                    return null;
                }
                
                public void preRun() {
                    acceptor.addResult(callable.evaluate(args), context);
                    checkpoint(acceptor, Grade.DONE);
                }
                
            };
        } else if (callable.getDecl() instanceof MethodDeclarationC) {
            return new CallReturnValueGoal((MethodDeclarationC) callable.getDecl(), context, acceptor);
        } else if (callable.getDecl() instanceof PythonLambdaExpressionC) {
            return new ExpressionValueGoal(((PythonLambdaExpressionC) callable.getDict()).getExpression(),
                    context, acceptor);
        }
        throw new IllegalStateException("should never reach this place");
    }
}
