package com.yoursway.sadr.python_v2.constructs;

import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.ResolveNameToObjectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class ArrayAccessC extends PythonConstructImpl<PythonArrayAccessExpression> {
    
    private final PythonConstruct array;
    private final PythonConstruct index;
    private final int ARRAY = 0, INDEX = 1;
    
    ArrayAccessC(Scope sc, PythonArrayAccessExpression node) {
        super(sc, node);
        array = getPostChildren().get(ARRAY);
        index = getPostChildren().get(INDEX).getPostChildren().get(0);
    }
    
    public PythonConstruct array() {
        return array;
    }
    
    @Override
    public IGoal evaluate(final Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                ResultsCollector rc = new ResultsCollector(2, context) {
                    @Override
                    protected <T> void processResultTuple(Map<Object, RuntimeObject> results, IGrade<T> grade) {
                        RuntimeObject arrayObject = results.get(0);
                        PythonArguments args = new PythonArguments(results.get(1));
                        if (arrayObject.getType() instanceof PythonClassType) {
                            schedule(CallResolver.callMethod(arrayObject, "__getitem__", args, acceptor,
                                    context, ArrayAccessC.this));
                        }
                    }
                };
                schedule(new ResolveNameToObjectGoal((VariableReferenceC) array, context, rc
                        .createAcceptor(0)));
                schedule(rc.addSubgoal(index, 1));
                rc.startCollecting();
            }
        };
    }
    
}
