package com.yoursway.sadr.python_v2.goals.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonLambdaExpressionC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallReturnValueGoal;
import com.yoursway.sadr.python_v2.goals.CreateInstanceGoal;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PassResultGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.model.ContextImpl;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
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
            final PythonArguments args, final PythonValueSetAcceptor acceptor, final Krocodile context,
            final PythonConstruct callingConstruct) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                PythonValueSetAcceptor findAcceptor = new PythonValueSetAcceptor() {
                    
                    @Override
                    protected <T> void acceptIndividualResult(RuntimeObject callable, IGrade<T> grade) {
                        assertCallable(callable);
                        args.getArgs().add(0, receiver);
                        schedule(callFunction((FunctionObject) callable, args, acceptor, context,
                                callingConstruct));
                    }
                };
                schedule(findMethod(receiver, methodName, findAcceptor, context));
            }
        };
    }
    
    public static IGoal findMethod(RuntimeObject receiver, String methodName,
            PythonValueSetAcceptor acceptor, Krocodile context) {
        if (receiver == null) {
            throw new IllegalStateException("Receiver is null!");
        }
        System.out.println("Looking for: " + receiver.toString() + "." + methodName);
        RuntimeObject callable = receiver.getAttribute(methodName); // instance attributes
        // look into class definition
        if (callable == null && receiver.getType() instanceof PythonClassType) {
            PythonClassType klass = (PythonClassType) receiver.getType();
            if (!(klass != null && klass.getDecl() instanceof ClassDeclarationC))
                return new PassResultGoal(context, acceptor, null);//TODO empty result
            MethodDeclarationC declaredMethod = ((ClassDeclarationC) klass.getDecl())
                    .findDeclaredMethod(methodName);
            if (null == declaredMethod) {
                return new PassResultGoal(context, acceptor, null);//TODO empty result
            }
            return declaredMethod.evaluate(context, acceptor);
        }
        return new PassResultGoal(context, acceptor, callable);
    }
    
    public static IGoal callFunction(final RuntimeObject callable, final PythonArguments args,
            final PythonValueSetAcceptor acceptor, final Krocodile context, PythonConstruct callingConstruct) {
        assertCallable(callable);
        return callFunction((FunctionObject) callable, args, acceptor, context, callingConstruct);
    }
    
    @SuppressWarnings("unchecked")
    public static IGoal callFunction(final FunctionObject callable, final PythonArguments args,
            final PythonValueSetAcceptor acceptor, final Krocodile crocodile, PythonConstruct callingConstruct) {
        if (callable == null) {
            throw new IllegalArgumentException("Callable is null");
        }
        acceptor.setCallingCostruct(callingConstruct);
        final PythonConstruct declC = callable.getDecl();
        if (declC == null) {
            return callable.evaluateGoal(acceptor, crocodile, args);
        } else if (declC instanceof MethodDeclarationC) {
            final MethodDeclarationC methodDeclC = (MethodDeclarationC) declC;
            final List<PythonArgument> realArgs = methodDeclC.node().getArguments();
            final Krocodile actualArguments = new Krocodile(Krocodile.EMPTY, declC, new ContextImpl(realArgs,
                    args));
            return new ExpressionValueGoal(actualArguments, acceptor) {
                public void preRun() {
                    ResultsCollector rc = new ResultsCollector(0, Krocodile.EMPTY) {
                        @Override
                        protected <T> void processResultTuple(Map<Object, RuntimeObject> results,
                                IGrade<T> grade) {
                            for (PythonArgument arg : realArgs) {
                                String name = arg.getName();
                                if (actualArguments.getActualArgument(name) == null) {
                                    RuntimeObject value = results.get(name);
                                    actualArguments.put(name, value);
                                }
                            }
                            schedule(new CallReturnValueGoal(methodDeclC, actualArguments, crocodile,
                                    acceptor));
                        }
                        
                        @Override
                        public <T> void allResultsProcessed(IGrade<T> grade) {
                            // TODO Auto-generated method stub
                            
                        }
                    };
                    for (PythonArgument arg : realArgs) {
                        if (arg == null || arg.getRef() == null) {
                            throw new IllegalArgumentException("weird argument");
                        }
                        String name = arg.getName();
                        if (actualArguments.getActualArgument(name) == null) {
                            PythonConstruct init = methodDeclC.getArgInit(name);
                            if (init == null) {
                                throw new IllegalArgumentException("missing argument " + name
                                        + " in function " + methodDeclC.displayName());
                            }
                            //FIXME: change to context that was right before function call
                            IGoal evaluate = init.evaluate(Krocodile.EMPTY, rc.createAcceptor(name));
                            schedule(evaluate);
                        }
                    }
                    rc.startCollecting();
                }
                
            };
        } else if (declC instanceof ClassDeclarationC) {
            final ClassDeclarationC classDeclarationC = (ClassDeclarationC) declC;
            return new CreateInstanceGoal(classDeclarationC, callingConstruct, args, crocodile, acceptor);//TODO instance creator
        } else if (declC instanceof PythonLambdaExpressionC) {
            PythonLambdaExpressionC lambdaC = (PythonLambdaExpressionC) declC;
            List<PythonArgument> realArgs = lambdaC.node().getArguments();
            ContextImpl context = new ContextImpl(realArgs, args);
            Krocodile actualArguments = new Krocodile(Krocodile.EMPTY, lambdaC, context);
            return ((PythonLambdaExpressionC) declC).getExpression().evaluate(actualArguments, acceptor);
        }
        throw new IllegalStateException("should never reach this place");
    }
}
