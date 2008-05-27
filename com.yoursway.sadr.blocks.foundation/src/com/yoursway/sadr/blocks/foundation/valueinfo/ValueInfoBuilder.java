package com.yoursway.sadr.blocks.foundation.valueinfo;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.blocks.foundation.wildcards.Wildcard;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class ValueInfoBuilder {
    
    private final MutableValueSet valueSetBuilder = new MutableValueSet();
    
    public ValueInfoBuilder addValue(Value value) {
        valueSetBuilder.add(value);
        return this;
    }
    
    public ValueInfoBuilder add(Type type, Value value) {
        valueSetBuilder.add(value);
        return this;
    }
    
    public ValueInfoBuilder add(ValueInfo result) {
        valueSetBuilder.addAll(result.getValueSet());
        return this;
    }
    
    public ValueInfoBuilder addValues(ValueSet valueSet) {
        valueSetBuilder.addAll(valueSet);
        return this;
    }
    
    public ValueInfoBuilder add(Wildcard wildcard, ValueInfo result) {
        addValues(result.getValueSet());
        return this;
    }
    
    public ValueInfo build() {
        ValueSet vs = valueSetBuilder.build();
        return new ValueInfo(vs);
    }
    
    public boolean isEmpty() {
        return valueSetBuilder.isEmpty();
    }
}
