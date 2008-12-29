package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PassResultGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class UnaryOperationC extends UnaryC implements PythonConstruct {
    private final String opName;
    
    UnaryOperationC(Scope sc, UnaryExpression node) {
        super(sc, node);
        opName = oplist.get(node.getOperator());
    }
    
    @Override
    public IGoal evaluate(final Krocodile context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                final PythonArguments args = new PythonArguments();
                
                final PythonValueSetAcceptor methodFound = new PythonValueSetAcceptor() {
                    
                    @Override
                    protected <T> void acceptIndividualResult(RuntimeObject callable, IGrade<T> grade) {
                        if (callable == null) {
                            schedule(new PassResultGoal(context, acceptor, null));
                        } else {
                            schedule(CallResolver.callFunction(callable, args, acceptor, getKrocodile(),
                                    UnaryOperationC.this));
                        }
                    }
                };
                
                PythonValueSetAcceptor evaluated = new PythonValueSetAcceptor() {
                    
                    @Override
                    protected <T> void acceptIndividualResult(RuntimeObject left, IGrade<T> grade) {
                        if (left == null) {
                            schedule(new PassResultGoal(context, acceptor, null));
                        } else {
                            args.getArgs().add(left);
                            schedule(CallResolver.findMethod(left, opName, methodFound, context));
                        }
                    }
                };
                schedule(getLeft().evaluate(context, evaluated));
            }
            
        };
    }
}
