package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonTupleExpression;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonObjectWithValue;
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
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                List<PythonConstruct> args = getChildConstructs();
                ResultsCollector rc = new ResultsCollector(args.size(), context) {
                    @Override
                    public <T> void completed(IGrade<T> grade) {
                        List<RuntimeObject> actualArguments = getResults();
                        PythonObjectWithValue<TupleValue> tupleObject = TupleType
                                .wrap(actualArguments);
                        acceptor.addResult(tupleObject, context);
                        updateGrade(acceptor, Grade.DONE);
                    }
                };
                
                //arguments objects are placed right after function object
                for (PythonConstruct arg : args) {
                    schedule(arg.evaluate(context, rc.createAcceptor()));
                }
                rc.startCollecting();
            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + PythonTupleExpressionC.this.toString();
            }
        };
    }
}
