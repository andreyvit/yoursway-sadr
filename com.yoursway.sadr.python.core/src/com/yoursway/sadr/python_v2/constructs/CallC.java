package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
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
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class CallC extends PythonConstructImpl<PythonCallExpression> {
    
    private static final String CALLABLE = "func";
    private static final int CALLABLE_INDEX = 0;
    private static final int ARGUMENTS_INDEX = 1;
    private static final String RECEIVER = "receiver";
    private List<PythonConstruct> args;
    private PythonConstruct self;
    private PythonConstruct callable;
    
    @SuppressWarnings("unchecked")
    CallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    abstract public PythonConstruct getReceiver();
    
    public List<PythonConstruct> getArgs() {
        return args;
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        List<PythonConstruct> wrappedChildren = new ArrayList<PythonConstruct>();
        if (node.getArgs() != null) {
            List<ASTNode> argNodes = ((ASTNode) node.getChilds().get(ARGUMENTS_INDEX)).getChilds();
            this.args = PythonConstructFactory.wrap(argNodes, innerScope());
            wrappedChildren.addAll(this.args);
        }
        callable = wrap((ASTNode) node.getChilds().get(CALLABLE_INDEX), innerScope());
        wrappedChildren.add(callable);
        setPreChildren(wrappedChildren);
        setPostChildren(Collections.EMPTY_LIST);
    }
    
    @Override
    public IGoal evaluate(final Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                ResultsCollector rc = new ResultsCollector(args.size() + 2, context) {
                    @Override
                    protected <T> void processResultTuple(Map<Object, RuntimeObject> results, IGrade<T> grade) {
                        RuntimeObject method = results.get(CALLABLE);
                        PythonArguments real = new PythonArguments();
                        if (results.containsKey(RECEIVER)) {
                            real.getArgs().add(results.get(RECEIVER));
                        }
                        for (PythonConstruct arg : args) {
                            if (arg instanceof CallArgumentC) {
                                CallArgumentC argC = (CallArgumentC) arg;
                                PythonConstruct value = (((CallArgumentC) arg).getValue());
                                if (value instanceof AssignmentC) {
                                    AssignmentC assignmentC = (AssignmentC) value;
                                    real.getKwargs().put(assignmentC.getName(), results.get(arg));
                                } else {
                                    RuntimeObject result = results.get(arg);
                                    if (argC.getStar() == PythonArgument.NOSTAR) {
                                        real.getArgs().add(result);
                                    } else if (argC.getStar() == PythonArgument.STAR) {
                                        real.setLastArg(result);
                                    } else if (argC.getStar() == PythonArgument.DOUBLESTAR) {
                                        real.setLastKwarg(result);
                                    }
                                }
                            }
                        }
                        
                        if (method instanceof FunctionObject) {
                            schedule(CallResolver.callFunction((FunctionObject) method, real, acceptor,
                                    getContext()));
                            return;
                        } else if (method instanceof PythonClassType) {
                            schedule(CallResolver
                                    .callMethod(method, "__call__", real, acceptor, getContext()));
                            return;
                        } else if (method instanceof PythonValue<?>) {
                            schedule(CallResolver
                                    .callMethod(method, "__call__", real, acceptor, getContext()));
                            return;
                        }
                        if (method == null) {
                            throw new IllegalStateException("Unable to find callable " + callable
                                    + ", resolved to " + method);
                        }
                    }
                    
                };
                
                schedule(callable.evaluate(getContext(), rc.createAcceptor(CALLABLE)));
                
                if (self != null) {
                    schedule(self.evaluate(getContext(), rc.createAcceptor(RECEIVER)));
                }
                
                for (PythonConstruct arg : args) {
                    if (arg instanceof CallArgumentC) {
                        PythonConstruct value = (((CallArgumentC) arg).getValue());
                        if (value instanceof AssignmentC) {
                            AssignmentC assignmentC = (AssignmentC) value;
                            schedule(assignmentC.rhs().evaluate(context, rc.createAcceptor(arg)));
                        } else {
                            schedule(arg.evaluate(context, rc.createAcceptor(arg)));
                        }
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
