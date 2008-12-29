package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.Iterator;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.Acceptor;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class PythonValueSetAcceptor extends Acceptor {
    
    private final ValueInfoBuilder builder = new ValueInfoBuilder(); //FIXME Build value info in getResult().
    private PythonConstruct callingConstruct = null;
    
    public PythonValueSetAcceptor() {
    }
    
    public PythonValueSetAcceptor(Acceptor parent) {
        super(parent);
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
    
    public void addResult(RuntimeObject result, Krocodile context) {
        if (null == result)
            throw new IllegalStateException("There should be no null items in results!");
        
        if (callingConstruct != null && ((PythonObject) result).getDecl() == null) {//TODO move get/set decl to PythonConstruct interface?
            ((PythonObject) result).setDecl(callingConstruct);
        }
        
        builder.add(result.getType(), result);
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    @Override
    public <T> void checkpoint(IGrade<T> grade) {
        ValueInfo resultByContext = getResult();
        if (resultByContext.isEmpty()) {
            acceptIndividualResult(null, grade);
            return;
        }
        for (Iterator<Value> valueIter = resultByContext.containedValues().iterator(); valueIter.hasNext();) {
            RuntimeObject item = (RuntimeObject) valueIter.next(); //FIXME ugly cast.  Make it type safe.
            if (null == item)
                throw new IllegalStateException("There should be no null items in results!");
            acceptIndividualResult(item, grade);
        }
        super.checkpoint(grade);
    }
    
    //TODO it isn't the most elegant solution - make it pretty.
    /**
     * Is called by the checkpoint implementation for each individual result of
     * the result set in <code>activeContext</code>.
     */
    protected abstract <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade);
}
