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
    
    public ResultsCollector(int capacity) {
        results = new ArrayList<RuntimeObject>(capacity);
    }
    
    public List<IGoal> addSubgoals(List<PythonConstruct> items, Context context) {
        List<IGoal> subgoals = new ArrayList<IGoal>(items.size());
        for (PythonConstruct construct : items) {
            IGoal evaluate = construct.evaluate(context, createAcceptor(context));
            subgoals.add(evaluate);
        }
        return subgoals;
    }
    
    protected List<RuntimeObject> getResults() {
        return results;
    }
    
    public PythonValueSetAcceptor createAcceptor(final Context context) {
        final int item = counter++;
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
