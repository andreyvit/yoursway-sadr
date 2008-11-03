package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.PythonTupleExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.TupleType;
import com.yoursway.sadr.python_v2.model.builtins.TupleValue;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class PythonTupleExpressionC extends PythonConstructImpl<PythonTupleExpression> implements
        PythonConstruct {
    
    public PythonTupleExpressionC(Scope sc, PythonTupleExpression node) {
        super(sc, node);
    }
    
    @Override
    public IGoal evaluate(final Krocodile context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                List<PythonConstruct> args = getPostChildren();
                ResultsCollector rc = new ResultsCollector(args.size(), context) {
                    @Override
                    protected <T> void processResultTuple(Map<Object, RuntimeObject> actualArguments,
                            IGrade<T> grade) {
                        List<RuntimeObject> arguments = new ArrayList<RuntimeObject>();
                        for (int i = 0; i < actualArguments.size(); i++) {
                            arguments.add(actualArguments.get(i));
                        }
                        PythonValue<TupleValue> tupleObject = TupleType.wrap(arguments);
                        acceptor.addResult(tupleObject, context);
                    }
                    
                    @Override
                    public <T> void allResultsProcessed(IGrade<T> grade) {
                        updateGrade(acceptor, grade);
                    }
                };
                
                //arguments objects are placed right after function object
                schedule(rc.addSubgoals(args));
                rc.startCollecting();
            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + PythonTupleExpressionC.this.toString();
            }
        };
    }
}
