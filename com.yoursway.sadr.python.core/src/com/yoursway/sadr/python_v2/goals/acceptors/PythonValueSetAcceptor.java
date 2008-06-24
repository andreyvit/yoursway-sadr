package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class PythonValueSetAcceptor implements IAcceptor {
    
    private final Map<Context, MutableValueSet> contextToValues = new HashMap<Context, MutableValueSet>();
    private final ValueInfoBuilder builder = new ValueInfoBuilder(); //FIXME Build value info in getResult().
    protected final Context activeContext;
    private PythonConstruct callingConstruct = null;
    
    public PythonValueSetAcceptor(Context activeContext) {
        this.activeContext = activeContext;
    }
    
    /**
     * Sets a most specific construct which creates the instances being added as
     * results.
     * 
     * @param callingConstruct
     */
    public void setCallingCostruct(PythonConstruct callingConstruct) {
        this.callingConstruct = callingConstruct;
    }
    
    public void addResult(RuntimeObject result, Context context) {
        if (null == result)
            throw new IllegalStateException("There should be no null items in results!");
        
        if (callingConstruct != null && ((PythonObject) result).getDecl() == null) {//TODO move get/set decl to PythonConstruct interface?
            ((PythonObject) result).setDecl(callingConstruct);
        }
        
        builder.add(result.getType(), result);
        if (contextToValues.get(context) == null)
            contextToValues.put(context, new MutableValueSet());
        contextToValues.get(context).add(result);
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
        MutableValueSet resultByContext = getResultByContext(activeContext);
        if (null == resultByContext) {
            acceptIndividualResult(null, grade);
            return;
        }
        for (Iterator<Value> valueIter = resultByContext.containedValues().iterator(); valueIter.hasNext();) {
            RuntimeObject item = (RuntimeObject) valueIter.next(); //FIXME ugly cast.  Make it type safe.
            if (null == item)
                throw new IllegalStateException("There should be no null items in results!");
            acceptIndividualResult(item, grade);
        }
    }
    
    //TODO it isn't the most elegant solution - make it pretty.
    /**
     * Is called by the checkpoint implementation for each individual result of
     * the result set in <code>activeContext</code>.
     */
    protected abstract <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade);
}
