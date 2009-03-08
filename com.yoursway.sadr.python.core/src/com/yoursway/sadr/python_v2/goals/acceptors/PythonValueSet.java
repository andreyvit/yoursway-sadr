package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.Collection;
import java.util.Iterator;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.Acceptor;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public class PythonValueSet extends Acceptor implements Iterable<PythonObject> {
    
    private final ValueInfoBuilder builder = new ValueInfoBuilder(); //FIXME Build value info in getResult().
    private PythonConstruct callingConstruct = null;
    
    public static final PythonValueSet EMPTY = new PythonValueSet();
    
    public PythonValueSet() {
    }
    
    public PythonValueSet(PythonObject result, Krocodile context) {
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
    
    public void addResult(PythonObject result) {
        if (null == result)
            throw new IllegalStateException("There should be no null items in results!");
        
        if (callingConstruct != null && result.getDecl() == null) {
            //TODO move get/set decl to PythonConstruct interface?
            result.setDecl(callingConstruct);
        }
        
        builder.add(result.getType(), result);
    }
    
    public void addResult(PythonObject result, Krocodile context) {
        addResult(result);
    }
    
    public void addResults(PythonValueSet r) {
        for (Value value : r.getResult().containedValues()) {
            addResult((PythonObject) value, null);
        }
        
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    public Iterator<PythonObject> iterator() {
        Collection<Value> values = builder.build().containedValues();
        final Iterator<Value> iterator = values.iterator();
        return new Iterator<PythonObject>() {
            
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            public PythonObject next() {
                return (PythonObject) iterator.next();
            }
            
            public void remove() {
                //do nothing
            }
        };
    }
    
    public boolean isEmpty() {
        return builder.isEmpty();
    }
    
    @Override
    public String toString() {
        return builder.toString();
    }
}
