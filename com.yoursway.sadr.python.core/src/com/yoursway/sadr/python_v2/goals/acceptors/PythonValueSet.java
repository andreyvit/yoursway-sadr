package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.model.types.BooleanType;
import com.yoursway.sadr.python.objects.TypeError;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class PythonValueSet implements Iterable<PythonValue>, IValueInfo, PythonValueSetBuilder {
    
    private final ValueInfoBuilder builder = new ValueInfoBuilder(); //FIXME Build value info in getResult().
    private PythonConstruct callingConstruct = null;
    
    public static final PythonValueSet EMPTY = new PythonValueSet();
    
    public PythonValueSet() {
    }
    
    public PythonValueSet(PythonValue result) {
        addResult(result);
    }
    
    public PythonValueSet(boolean result, PythonDynamicContext context) {
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
    
    public void addResult(PythonValue result) {
        if (null == result)
            throw new IllegalStateException("There should be no null items in results!");
        
        if (callingConstruct != null && result.getDecl() == null) {
            //TODO move get/set decl to PythonConstruct interface?
            result.setDecl(callingConstruct);
        }
        
        builder.add(result.getType(), result);
    }
    
    public void addResult(boolean result, PythonDynamicContext context) {
        addResult(BooleanType.wrap(result));
    }
    
    public void addResults(PythonValueSet r) {
        for (Value value : r.getResult().containedValues()) {
            addResult(((PythonValue) value));
        }
        
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    public Iterator<PythonValue> iterator() {
        Collection<Value> values = builder.build().containedValues();
        final Iterator<Value> iterator = values.iterator();
        return new Iterator<PythonValue>() {
            
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            public PythonValue next() {
                return (PythonValue) iterator.next();
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
    
    public void addException(TypeError typeError) {
        
    }
    
    public String[] describePossibleTypes() {
        throw new UnsupportedOperationException();
    }
    
    public String[] describePossibleValues() {
        throw new UnsupportedOperationException();
    }
    
    @pausable
    public PythonValueSet call(PythonDynamicContext dc) {
        PythonValueSetBuilder result = PythonValueSet.newBuilder();
        for (PythonValue value : this)
            value.call(dc, result);
        return result.build();
    }
    
    public static PythonValueSetBuilder newBuilder() {
        return new PythonValueSet();
    }
    
    public PythonValueSet build() {
        return this;
    }
    
    public void addAll(List<PythonValueSet> results) {
        for (PythonValueSet v : results)
            addResults(v);
    }
    
    public static PythonValueSet merge(List<PythonValueSet> results) {
        PythonValueSetBuilder builder = newBuilder();
        builder.addAll(results);
        PythonValueSet result = builder.build();
        return result;
    }
    
}
