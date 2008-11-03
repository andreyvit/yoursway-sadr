package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class ResultsCollector extends Synchronizer {
    private final Map<Object, MutableValueSet> results;
    private final Krocodile context;
    private boolean adding = true;
    
    public ResultsCollector(int capacity, Krocodile context) {
        this.context = context;
        results = new HashMap<Object, MutableValueSet>(capacity);
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
    
    private Map<Object, ValueSet> getResults() {
        Map<Object, ValueSet> objectToValueSet = new HashMap<Object, ValueSet>(results.size());
        for (Entry<Object, MutableValueSet> entry : results.entrySet()) {
            objectToValueSet.put(entry.getKey(), entry.getValue().build());
        }
        return objectToValueSet;
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
        if (results.get(name) == null)
            results.put(name, new MutableValueSet());
        return new PythonValueSetAcceptor(context) {
            @Override
            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                results.get(name).add(result);
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
    
    private <T> void fixResult(Iterator<Entry<Object, ValueSet>> tupleElementIter,
            Map<Object, RuntimeObject> results, IGrade<T> grade) {
        if (!tupleElementIter.hasNext()) {
            processResultTuple(results, grade);
            return;
        }
        Entry<Object, ValueSet> entry = tupleElementIter.next();
        Collection<Value> values = entry.getValue().containedValues();
        for (Value value : values) {
            results.put(entry.getKey(), (RuntimeObject) value);// XXX ugly freaky stupid cast! remove asap!
            fixResult(tupleElementIter, results, grade);
        }
    }
    
    @Override
    public final <T> void completed(IGrade<T> grade) {
        Map<Object, RuntimeObject> concreteResults = new HashMap<Object, RuntimeObject>();
        Set<Entry<Object, ValueSet>> entrySet = getResults().entrySet();
        //TODO        if (entrySet.isEmpty())
        //TODO            processResultTuple(concreteResults, grade);
        fixResult(entrySet.iterator(), concreteResults, grade);
        allResultsProcessed(grade);
    }
    
    abstract public <T> void allResultsProcessed(IGrade<T> grade);
    
    abstract protected <T> void processResultTuple(Map<Object, RuntimeObject> results, IGrade<T> grade);
    
}
