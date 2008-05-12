package com.yoursway.sadr.python_v2.goals.internal;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.goals.CallReturnValueGoal;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.ContextImpl;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonLambdaExpressionC;
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
                
                public void preRun() {
                    acceptor.addResult(callable.evaluate(args), context);
                    updateGrade(acceptor, Grade.DONE);
                }
                
                @Override
                protected String describe() {
                    return "Evaluating function " + callable.name();
                }
                
            };
        } else if (callable.getDecl() instanceof MethodDeclarationC) {
            MethodDeclarationC decl = (MethodDeclarationC) callable.getDecl();
            final Context actualArguments = new ContextImpl(decl.node().getArguments(), args);
            return new CallReturnValueGoal((MethodDeclarationC) callable.getDecl(), actualArguments, context,
                    acceptor);
        } else if (callable.getDecl() instanceof PythonLambdaExpressionC) {
            PythonLambdaExpressionC decl = (PythonLambdaExpressionC) callable.getDecl();
            Context actualArguments = new ContextImpl(decl.node().getArguments(), args);
            return ((PythonLambdaExpressionC) callable
            .getDecl()).getExpression().evaluate(actualArguments, acceptor);
        }
        throw new IllegalStateException("should never reach this place");
    }
}
