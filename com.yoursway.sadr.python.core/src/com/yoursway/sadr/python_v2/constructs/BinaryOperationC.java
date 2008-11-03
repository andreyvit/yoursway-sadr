package com.yoursway.sadr.python_v2.constructs;

import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PassResultGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class BinaryOperationC extends BinaryC {
    
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private final String leftOpName;
    private final String rightOpName;
    
    BinaryOperationC(Scope sc, BinaryExpression node) {
        super(sc, node);
        this.leftOpName = lbinoplist.get(node.getOperator());
        this.rightOpName = rbinoplist.get(node.getOperator());
    }
    
    @Override
    public IGoal evaluate(final Krocodile context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                ResultsCollector rc = new ResultsCollector(2, context) {
                    @Override
                    protected <K> void processResultTuple(Map<Object, RuntimeObject> results, IGrade<K> grade) {
                        final RuntimeObject left = results.get(LEFT);
                        final RuntimeObject right = results.get(RIGHT);
                        if (left == null || right == null) {
                            schedule(new PassResultGoal(context, acceptor, null));
                            return;
                        }
                        final PythonArguments args = new PythonArguments(left, right);
                        final PythonValueSetAcceptor rightFound = new PythonValueSetAcceptor(context) {
                            
                            @Override
                            protected <T> void acceptIndividualResult(RuntimeObject callable, IGrade<T> grade) {
                                if (callable == null) {
                                    schedule(new PassResultGoal(context, acceptor, null));
                                } else {
                                    schedule(CallResolver.callFunction(callable, args, acceptor,
                                            getContext(), BinaryOperationC.this));
                                }
                            }
                        };
                        PythonValueSetAcceptor leftFound = new PythonValueSetAcceptor(context) {
                            
                            @Override
                            protected <T> void acceptIndividualResult(RuntimeObject callable, IGrade<T> grade) {
                                if (callable == null) {
                                    schedule(CallResolver.findMethod(right, rightOpName, rightFound, context));
                                } else {
                                    schedule(CallResolver.callFunction(callable, args, acceptor,
                                            getContext(), BinaryOperationC.this));
                                }
                            }
                        };
                        schedule(CallResolver.findMethod(left, leftOpName, leftFound, context));
                    }
                    
                    @Override
                    public <T> void allResultsProcessed(IGrade<T> grade) {
                        
                    }
                };
                schedule(rc.addSubgoal(getLeft(), LEFT));
                schedule(rc.addSubgoal(getRight(), RIGHT));
                rc.startCollecting();
            }
            
        };
    }
}
