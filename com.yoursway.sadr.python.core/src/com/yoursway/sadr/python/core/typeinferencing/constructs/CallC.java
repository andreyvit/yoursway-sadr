package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class CallC extends PythonConstructImpl<PythonCallExpression> {
    
    private final List<PythonConstruct> args;
    private final PythonConstruct func;
    
    CallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
        if (node.getArgs() == null) {
            args = Collections.emptyList();
        } else {
            args = PythonConstructFactory.wrap(node.getArgs().getChilds(), sc);
        }
        func = PythonConstructFactory.wrap(node.getFunction(), sc);
        List<PythonConstruct> childs = new ArrayList<PythonConstruct>();
        setChildConstructs(childs);
    }
    
    @Override
    protected void wrapEnclosedChildren() {
    }
    
    abstract public PythonConstruct getReceiver();
    
    public List<PythonConstruct> getArgs() {
        return args;
    }
    
    @Override
    public IGoal evaluate(final Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                final List<String> kwnames = new ArrayList<String>();
                ResultsCollector rc = new ResultsCollector(args.size() + 2, context) {
                    @Override
                    public <T> void completed(IGrade<T> grade) {
                        //FIXME
                        
                        //TODO: if receiver is PythonUserClassType, run modified CallResolver.callMethod,
                        //if it's unsuccessful, run FindClassMethodGoal. Repeat for superclasses.
                        //that's how we find callable.
                        //then we run CallResolver.callFunction
                        //if receiver was none, it was evaluated to a callable.
                        //then just run callFunction with it
                        //if bound function wasn't resolved (generic situation), but was present,.. 2BC
                        
                        //                        String expressionName = getName(expression);
                        //                        schedule(new FindClassMethodGoal(, name, synchronizer.createAcceptor(getContext()),
                        //                                getContext()));
                        //callee method object is result number zero
                        //                        CallResolver.callClassMethod(klass, expressionName, actualArguments, acceptor, context)
                        
                        List<RuntimeObject> results = getResults();
                        RuntimeObject method = results.get(0);
                        int index = 1;
                        PythonArguments real = new PythonArguments();
                        for (PythonConstruct arg : args) {
                            if (arg instanceof CallArgumentC) {
                                RuntimeObject result = results.get(index++);
                                CallArgumentC argC = (CallArgumentC) arg;
                                if (argC.getStar() == PythonArgument.NOSTAR) {
                                    real.getArgs().add(result);
                                } else if (argC.getStar() == PythonArgument.STAR) {
                                    real.setLastArg(result);
                                } else if (argC.getStar() == PythonArgument.DOUBLESTAR) {
                                    real.setLastKwarg(result);
                                }
                            }
                        }
                        
                        for (PythonConstruct arg : args) {
                            if (arg instanceof AssignmentC) {
                                AssignmentC assignmentC = (AssignmentC) arg;
                                RuntimeObject result = results.get(index++);
                                real.getKwargs().put(assignmentC.getName(), result);
                            }
                        }
                        
                        FunctionObject function = null;
                        if (method instanceof FunctionObject) {
                            function = (FunctionObject) method;
                        } else if (method instanceof PythonClassType) {
                            function = (FunctionObject) method.getAttribute("__call__");
                        }
                        if (function == null) {
                            throw new IllegalStateException("Unable to find callable " + func
                                    + ", resolved to " + method);
                        }
                        schedule(CallResolver.callFunction(function, real, acceptor, getContext()));
                        //                        if (expression instanceof MethodCallC) {
                        //                            function.bind(InstanceType.createSelf(expression));
                        //                            actualArguments.add(0, new InstanceValue(null, null));
                        //                        }
                    }
                    
                };
                
                schedule(func.evaluate(getContext(), rc.createAcceptor()));
                
                if (getReceiver() != null) { //evaluate receiver as "self"
                    schedule(getReceiver().evaluate(getContext(), rc.createAcceptor()));
                }
                
                for (PythonConstruct arg : args) {
                    if (!(arg instanceof AssignmentC)) {
                        schedule(arg.evaluate(context, rc.createAcceptor()));
                    }
                }
                //assignment are keywords, should be the last ones
                for (PythonConstruct arg : args) {
                    if (arg instanceof AssignmentC) {
                        AssignmentC assignmentC = (AssignmentC) arg;
                        kwnames.add(assignmentC.getName());
                        schedule(assignmentC.rhs().evaluate(context, rc.createAcceptor()));
                    }
                }
                rc.startCollecting();
            }
            
            //            protected String getName(final CallC var) {
            //                String name = null;
            //                if (var instanceof MethodCallC) {
            //                    name = var.node().getMethodName();
            //                } else if (var instanceof ProcedureCallC) {
            //                    name = var.node().getProcedureName();
            //                }
            //                return name;
            //            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + CallC.this.toString();
            }
        };
    }
}
