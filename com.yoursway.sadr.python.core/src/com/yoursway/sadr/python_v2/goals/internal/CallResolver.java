package com.yoursway.sadr.python_v2.goals.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonLambdaExpressionC;
import com.yoursway.sadr.python_v2.goals.CallReturnValueGoal;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.FindClassMethodGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.ContextImpl;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public final class CallResolver {
    public static IGoal callMethod(RuntimeObject receiver, String methodName, PythonArguments args,
            PythonValueSetAcceptor acceptor, Context context) {
        RuntimeObject callable = receiver.getAttribute(methodName);
        if (callable == null && receiver instanceof InstanceValue) {
            PythonUserClassType userClass = (PythonUserClassType) receiver.getType();
            args.getArgs().add(0, receiver);
            return callClassMethod(userClass, methodName, args, acceptor, context);
        }
        if (callable == null) {
            throw new IllegalArgumentException("callable is null in CallResolver.callMethod()");
        }
        if (!(callable instanceof FunctionObject)) {
            throw new IllegalArgumentException("callable is " + callable.getClass().getSimpleName()
                    + " in CallResolver.callMethod()");
        }
        if (receiver.getDict().containsKey(methodName)) {
            return callFunction((FunctionObject) callable, args, acceptor, context);
        } else {
            args.getArgs().add(0, receiver);
            return callFunction((FunctionObject) callable, args, acceptor, context);
        }
    }
    
    public static IGoal callClassMethod(PythonUserClassType receiver, final String methodName,
            final PythonArguments args, final PythonValueSetAcceptor acceptor, final Context context) {
        return new FindClassMethodGoal(receiver.getDecl(), methodName, args, acceptor, context);
    }
    
    @SuppressWarnings("unchecked")
    public static IGoal callFunction(final FunctionObject callable, final PythonArguments args,
            final PythonValueSetAcceptor acceptor, final Context context) {
        if (callable == null) {
            throw new IllegalStateException("Callable is null");
        }
        PythonConstruct declC = callable.getDecl();
        if (declC == null) {
            return callable.evaluateGoal(acceptor, context, args);
        } else if (declC instanceof MethodDeclarationC) {
            final MethodDeclarationC methodDeclC = (MethodDeclarationC) declC;
            final List<PythonArgument> realArgs = methodDeclC.node().getArguments();
            final Context actualArguments = new ContextImpl(realArgs, args);
            return new ExpressionValueGoal(actualArguments, acceptor) {
                public void preRun() {
                    ResultsCollector rc = new ResultsCollector(0, null) {
                        @Override
                        public <T> void completed(IGrade<T> grade) {
                            Map<Object, RuntimeObject> results = getResults();
                            for (PythonArgument arg : realArgs) {
                                String name = arg.getName();
                                if (actualArguments.getActualArgument(name) == null) {
                                    RuntimeObject value = results.get(name);
                                    actualArguments.put(name, value);
                                }
                            }
                            schedule(new CallReturnValueGoal(methodDeclC, actualArguments, context, acceptor));
                            
                        }
                    };
                    for (PythonArgument arg : realArgs) {
                        String name = arg.getName();
                        if (actualArguments.getActualArgument(name) == null) {
                            PythonConstruct init = methodDeclC.getArgInit(name);
                            IGoal evaluate = init.evaluate(null, rc.createAcceptor(name));
                            schedule(evaluate);
                        }
                    }
                    rc.startCollecting();
                }
                
            };
        } else if (declC instanceof ClassDeclarationC) {
            final ClassDeclarationC classDeclarationC = (ClassDeclarationC) declC;
            return new CreateInstanceGoal(classDeclarationC, args, context, acceptor);
        } else if (declC instanceof PythonLambdaExpressionC) {
            PythonLambdaExpressionC lambdaC = (PythonLambdaExpressionC) declC;
            List<PythonArgument> realArgs = lambdaC.node().getArguments();
            Context actualArguments = new ContextImpl(realArgs, args);
            return ((PythonLambdaExpressionC) declC).getExpression().evaluate(actualArguments, acceptor);
        }
        throw new IllegalStateException("should never reach this place");
    }
}
