package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.ResolveNameToObjectGoal;
import com.yoursway.sadr.python_v2.goals.ResultsCollector;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class CallC extends PythonConstructImpl<PythonCallExpression> {
    
    private List<PythonConstruct> args;
    
    CallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
        if (node.getArgs() == null) {
            args = Collections.emptyList();
        } else {
            args = PythonConstructFactory.wrap(sc, node.getArgs().getChilds());
        }
        
    }
    
    abstract public PythonConstruct getReceiver();
    
    public List<PythonConstruct> getArgs() {
        return args;
    }
    
    @Override
    public IGoal evaluate(final Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                final CallC expression = CallC.this;
                List<PythonConstruct> args = expression.getArgs();
                ResultsCollector synchronizer = new ResultsCollector(args.size() + 2) {
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
                        
                        List<RuntimeObject> actualArguments = getResults();
                        FunctionObject function = (FunctionObject) actualArguments.remove(0);
                        //                        if (expression instanceof MethodCallC) {
                        //                            function.bind(InstanceType.createSelf(expression));
                        //                            actualArguments.add(0, new InstanceValue(null, null));
                        //                        }
                        schedule(CallResolver.callFunction(function, actualArguments, acceptor, getContext()));
                    }
                    
                };
                
                schedule(new ResolveNameToObjectGoal(expression, synchronizer.createAcceptor(getContext()),
                        getContext()));
                
                if (getReceiver() != null) { //evaluate receiver as "self"
                    schedule(getReceiver().evaluate(getContext(), synchronizer.createAcceptor(getContext())));
                }
                
                //arguments objects are placed right after function object
                schedule(synchronizer.addSubgoals(args, getContext()));
                
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
