package com.yoursway.sadr.python_v2.goals.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PassResultGoal;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonLambdaExpressionC;
import com.yoursway.sadr.python_v2.goals.CallReturnValueGoal;
import com.yoursway.sadr.python_v2.goals.CreateInstanceGoal;
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
    private static void assertCallable(RuntimeObject callable) {
        if (callable == null) {
            throw new IllegalArgumentException("callable is null");
        }
        if (!(callable instanceof FunctionObject)) {
            throw new IllegalArgumentException("callable is not FunctionObject but "
                    + callable.getClass().getSimpleName());
        }
    }
    
    public static IGoal callMethod(final RuntimeObject receiver, final String methodName,
            final PythonArguments args, final PythonValueSetAcceptor acceptor, final Context context) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                PythonValueSetAcceptor findAcceptor = new PythonValueSetAcceptor(context) {
                    
                    @Override
                    protected <T> void acceptIndividualResult(RuntimeObject callable, IGrade<T> grade) {
                        assertCallable(callable);
                        args.getArgs().add(0, receiver);
                        schedule(callFunction((FunctionObject) callable, args, acceptor, context));
                    }
                };
                schedule(findMethod(receiver, methodName, findAcceptor, context));
            }
        };
    }
    
    public static IGoal findMethod(RuntimeObject receiver, String methodName,
            PythonValueSetAcceptor acceptor, Context context) {
        if (receiver == null) {
            throw new IllegalStateException("Receiver is null!");
        }
        RuntimeObject callable = receiver.getAttribute(methodName); // look for builtins
        if (callable == null && receiver.getType() instanceof PythonUserClassType) { // look into class definition
            PythonUserClassType userClass = (PythonUserClassType) receiver.getType();
            return new FindClassMethodGoal(userClass.getDecl(), methodName, acceptor, context);
        }
        return new PassResultGoal(context, acceptor, callable);
    }
    
    public static IGoal callFunction(final RuntimeObject callable, final PythonArguments args,
            final PythonValueSetAcceptor acceptor, final Context context) {
        assertCallable(callable);
        return callFunction((FunctionObject) callable, args, acceptor, context);
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
                        if (arg == null || arg.getRef() == null) {
                            throw new IllegalArgumentException("weird argument");
                        }
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
