package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.PythonDictExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.DictType;
import com.yoursway.sadr.python_v2.model.builtins.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class PythonDictExpressionC extends PythonConstructImpl<PythonDictExpression> implements
        PythonConstruct {
    
    private final List<PythonConstruct> args;
    
    public PythonDictExpressionC(Scope sc, PythonDictExpression node) {
        super(sc, node);
        args = getPostChildren();
    }
    
    public List<PythonConstruct> getArgs() {
        return args;
    }
    
    @Override
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                final List<PythonConstruct> args = getArgs();
                ResultsCollector rc = new ResultsCollector(args.size(), context) {
                    @Override
                    protected <T> void processResultTuple(Map<Object, RuntimeObject> actualArguments,
                            IGrade<T> grade) {
                        PythonValue<DictValue> dictObject = DictType.wrap();
                        HashMap<RuntimeObject, RuntimeObject> dict = dictObject.getValue().getDict();
                        for (int i = 0; i < args.size() / 2; i++) {
                            RuntimeObject key = actualArguments.get(i * 2);
                            RuntimeObject value = actualArguments.get(i * 2 + 1);
                            dict.put(key, value);
                        }
                        acceptor.addResult(dictObject, context);
                        updateGrade(acceptor, Grade.DONE);
                    }
                };
                
                //arguments objects are placed right after function object
                schedule(rc.addSubgoals(args));
                rc.startCollecting();
            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + PythonDictExpressionC.this.toString();
            }
        };
    }
}
