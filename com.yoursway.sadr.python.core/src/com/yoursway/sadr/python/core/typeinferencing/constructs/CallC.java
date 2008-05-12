package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.ResolveNameToObjectGoal;
import com.yoursway.sadr.python_v2.goals.Synchronizer;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.ClassStub;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.InstanceType;
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
    
    public List<PythonConstruct> getArgs() {
        return args;
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                final CallC expression = CallC.this;
                List<PythonConstruct> args = expression.getArgs();
                final List<RuntimeObject> actualArguments = new ArrayList<RuntimeObject>(args.size());
                final FunctionObject[] function = new FunctionObject[] { null };
                final Synchronizer syncronizer = new Synchronizer(args.size() + 1) {
                    //arguments and the object being called 
                    @Override
                    public <T> void allDone(IGrade<T> grade) {
                        IGoal callFunction;
                        if (expression instanceof MethodCallC) {
                            actualArguments.add(0, new InstanceType(new ClassStub(expression.node())));
                            callFunction = CallResolver.callFunction(function[0], actualArguments, acceptor,
                                    getContext());
                        } else {
                            callFunction = CallResolver.callFunction(function[0], actualArguments, acceptor,
                                    getContext());
                        }
                        schedule(callFunction);
                    }
                };
                
                schedule(new ResolveNameToObjectGoal(expression, new PythonValueSetAcceptor() {
                    
                    public <T> void checkpoint(IGrade<T> grade) {
                        if (getResultByContext(getContext()) instanceof FunctionObject) {
                            function[0] = (FunctionObject) getResultByContext(getContext());
                            syncronizer.subgoalDone(grade);
                        }
                    }
                }, getContext()));
                
                for (int i = 0; i < args.size(); i++) {
                    final int pos = i;
                    PythonConstruct arg = args.get(i);
                    actualArguments.add(null);
                    schedule(arg.evaluate(getContext(), new PythonValueSetAcceptor() {
                        public <T> void checkpoint(IGrade<T> grade) {
                            actualArguments.set(pos, getResultByContext(getContext()));
                            syncronizer.subgoalDone(grade);
                        }
                    }));
                }
                
            }
            
            @Override
            public String describe() {
                String basic = super.describe();
                return basic + "\nfor expression " + CallC.this.toString();
            }
        };
    }
}
