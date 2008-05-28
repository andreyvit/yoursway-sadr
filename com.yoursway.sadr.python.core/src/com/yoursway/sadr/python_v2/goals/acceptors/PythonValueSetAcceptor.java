package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class PythonValueSetAcceptor implements IAcceptor {
    
    private final Map<Context, MutableValueSet> contextToValues = new HashMap<Context, MutableValueSet>();
    private final ValueInfoBuilder builder = new ValueInfoBuilder(); //FIXME Build value info in getResult().
    protected final Context activeContext;
    
    public PythonValueSetAcceptor(Context activeContext) {
        this.activeContext = activeContext;
    }
    
    public void addResult(RuntimeObject result, Context context) {
        if (result != null) {
            builder.add(result.getType(), result);
            if (contextToValues.get(context) == null)
                contextToValues.put(context, new MutableValueSet());
            contextToValues.get(context).add(result);
        }
    }
    
    public MutableValueSet getResultByContext(Context context) {
        return contextToValues.get(context);
    }
    
    protected void setResults(PythonValueSetAcceptor other) {
        contextToValues.putAll(other.contextToValues);
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    public <T> void checkpoint(IGrade<T> grade) {
        for (Iterator<Value> valueIter = getResultByContext(activeContext).containedValues().iterator(); valueIter
                .hasNext();) {
            acceptIndividualResult((RuntimeObject) valueIter.next(), grade); //FIXME ugly cast.  Make it type safe.
        }
    }
    
    //TODO it isn't the most elegant solution - make it pretty.
    /**
     * Is called by the checkpoint implementation for each individual result of
     * the result set in <code>activeContext</code>.
     */
    protected abstract <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade);
}
