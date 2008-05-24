package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class ResultsCollector extends Synchronizer {
    private final Map<Object, RuntimeObject> results;
    private final Context context;
    private boolean adding = true;
    
    public ResultsCollector(int capacity, Context context) {
        this.context = context;
        results = new HashMap<Object, RuntimeObject>(capacity);
    }
    
    public List<IGoal> addSubgoals(List<PythonConstruct> items) {
        List<IGoal> subgoals = new ArrayList<IGoal>(items.size());
        int item = 0;
        for (PythonConstruct construct : items) {
            IGoal evaluate = construct.evaluate(context, createAcceptor(item++));
            subgoals.add(evaluate);
        }
        return subgoals;
    }
    
    public IGoal addSubgoal(PythonConstruct construct, Object name) {
        return construct.evaluate(context, createAcceptor(name));
    }
    
    protected Map<Object, RuntimeObject> getResults() {
        return results;
    }
    
    final public <K> void subgoalCompleted(Object name, IGrade<K> grade) {
    }
    
    final private <K> void subgoalDone(Object name, IGrade<K> grade) {
        if (adding)
            throw new IllegalStateException(
                    "Done signal not possible in init stage. You forgot to run startCollecting()");
        subgoalCompleted(name, grade);
        super.subgoalDone(grade);
    }
    
    public PythonValueSetAcceptor createAcceptor(final Object name) {
        if (!adding)
            throw new IllegalStateException("Adding not available in waiting stage.");
        counter++;
        if (results.containsKey(name)) {
            throw new IllegalArgumentException("Key " + name + " already found.");
        }
        results.put(name, null);
        return new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                RuntimeObject result = getResultByContext(context);
                results.put(name, result);
                if (grade.isDone())
                    subgoalDone(name, grade);
            }
        };
    }
    
    final public void startCollecting() {
        adding = false;
        if (counter <= 0)
            completed(Grade.DONE);
    }
    
}
