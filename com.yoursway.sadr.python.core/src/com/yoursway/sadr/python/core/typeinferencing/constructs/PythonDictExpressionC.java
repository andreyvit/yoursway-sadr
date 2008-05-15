package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonDictExpression;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.ResultsCollector;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.DictType;
import com.yoursway.sadr.python_v2.model.builtins.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonObjectWithValue;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class PythonDictExpressionC extends PythonConstructImpl<PythonDictExpression> implements
        PythonConstruct {
    
    private List<PythonConstruct> args;
    
    public PythonDictExpressionC(Scope sc, PythonDictExpression node) {
        super(sc, node);
    }
    
    public List<PythonConstruct> getArgs() {
        return args;
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        args = PythonConstructFactory.wrap(this.node.getChilds(), parentScope());
        setChildConstructs(new ArrayList<PythonConstruct>());
    }
    
    @Override
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                final List<PythonConstruct> args = getArgs();
                ResultsCollector synchronizer = new ResultsCollector(args.size(), context) {
                    @Override
                    public <T> void completed(IGrade<T> grade) {
                        List<RuntimeObject> actualArguments = getResults();
                        PythonObjectWithValue<DictValue> dictObject = DictType.newDictObject();
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
                for (PythonConstruct arg : args) {
                    schedule(arg.evaluate(context, synchronizer.createAcceptor()));
                }
            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + PythonDictExpressionC.this.toString();
            }
        };
    }
}
