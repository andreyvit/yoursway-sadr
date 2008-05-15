package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class ResultsCollector extends Synchronizer {
    List<RuntimeObject> results;
    private final Context context;
    private int itemCount;
    
    public ResultsCollector(int capacity, Context context) {
        this.context = context;
        itemCount = 0;
        results = new ArrayList<RuntimeObject>(capacity);
    }
    
    public List<IGoal> addSubgoals(List<PythonConstruct> items) {
        List<IGoal> subgoals = new ArrayList<IGoal>(items.size());
        for (PythonConstruct construct : items) {
            IGoal evaluate = construct.evaluate(context, createAcceptor());
            subgoals.add(evaluate);
        }
        return subgoals;
    }
    
    protected List<RuntimeObject> getResults() {
        return results;
    }
    
    public PythonValueSetAcceptor createAcceptor() {
        final int item = itemCount++;
        counter++;
        results.add(null);
        return new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                RuntimeObject result = getResultByContext(context);
                results.set(item, result);
                ResultsCollector.this.subgoalDone(grade);
            }
        };
    }
    
}
