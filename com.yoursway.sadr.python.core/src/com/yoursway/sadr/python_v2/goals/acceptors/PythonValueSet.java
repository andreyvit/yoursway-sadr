package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.Collection;
import java.util.Iterator;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.Acceptor;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public class PythonValueSet extends Acceptor implements Iterable<RuntimeObject> {
    
    private final ValueInfoBuilder builder = new ValueInfoBuilder(); //FIXME Build value info in getResult().
    private PythonConstruct callingConstruct = null;
    
    public static final PythonValueSet EMPTY = new PythonValueSet();
    
    public PythonValueSet() {
    }
    
    public PythonValueSet(RuntimeObject result, Krocodile context) {
        addResult(result, context);
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
    
    public void addResult(RuntimeObject result) {
        if (null == result)
            throw new IllegalStateException("There should be no null items in results!");
        
        if (callingConstruct != null && ((PythonObject) result).getDecl() == null) {//TODO move get/set decl to PythonConstruct interface?
            ((PythonObject) result).setDecl(callingConstruct);
        }
        
        builder.add(result.getType(), result);
    }
    
    public void addResult(RuntimeObject result, Krocodile context) {
        addResult(result);
    }
    
    public void addResults(PythonValueSet r) {
        for (Value value : r.getResult().containedValues()) {
            addResult((RuntimeObject) value, null);
        }
        
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    public Iterator<RuntimeObject> iterator() {
        Collection<Value> values = builder.build().containedValues();
        final Iterator<Value> iterator = values.iterator();
        return new Iterator<RuntimeObject>() {
            
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            public RuntimeObject next() {
                return (RuntimeObject) iterator.next();
            }
            
            public void remove() {
                //do nothing
            }
        };
    }
    
    public boolean isEmpty() {
        return builder.isEmpty();
    }
}
