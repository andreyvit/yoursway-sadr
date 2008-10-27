package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.ListType;
import com.yoursway.sadr.python_v2.model.builtins.ListValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class PythonListExpressionC extends PythonConstructImpl<PythonListExpression> implements
        PythonConstruct {
    
    public PythonListExpressionC(Scope sc, PythonListExpression node) {
        super(sc, node);
    }
    
    @Override
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
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
                        PythonValue<ListValue> listObject = ListType.wrap(arguments);
                        acceptor.addResult(listObject, context);
                        updateGrade(acceptor, Grade.DONE);
                    }
                };
                
                schedule(rc.addSubgoals(args));
                rc.startCollecting();
            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + PythonListExpressionC.this.toString();
            }
        };
    }
}
