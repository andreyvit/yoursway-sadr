package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class ResultsCollector extends Synchronizer {
    private final List<RuntimeObject> results;
    private final Context context;
    private boolean adding = true;
    
    public ResultsCollector(int capacity, Context context) {
        this.context = context;
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
    
    @Override
    final public <T> void subgoalDone(IGrade<T> grade) {
        if (adding)
            throw new IllegalStateException("Done signal not possible in init stage.");
        super.subgoalDone(grade);
    }
    
    public PythonValueSetAcceptor createAcceptor() {
        if (!adding)
            throw new IllegalStateException("Adding not available in waiting stage.");
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
    
    final public void startCollecting() {
        adding = false;
    }
    
}
