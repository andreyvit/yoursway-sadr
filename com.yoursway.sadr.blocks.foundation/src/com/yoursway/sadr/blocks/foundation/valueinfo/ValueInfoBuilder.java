package com.yoursway.sadr.blocks.foundation.valueinfo;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSet;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSetBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.blocks.foundation.wildcards.Wildcard;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class ValueInfoBuilder {
    
    private final TypeSetBuilder typeSetBuilder = new TypeSetBuilder();
    
    private final MutableValueSet valueSetBuilder = new MutableValueSet();
    
    public ValueInfoBuilder add(Type type) {
        typeSetBuilder.add(type);
        return this;
    }
    
    public ValueInfoBuilder add(Value value) {
        valueSetBuilder.add(value);
        return this;
    }
    
    public ValueInfoBuilder add(Type type, Value value) {
        typeSetBuilder.add(type);
        valueSetBuilder.add(value);
        return this;
    }
    
    public ValueInfoBuilder add(ValueInfo result) {
        typeSetBuilder.addAll(result.getTypeSet());
        valueSetBuilder.addAll(result.getValueSet());
        return this;
    }
    
    public ValueInfoBuilder add(TypeSet typeSet) {
        typeSetBuilder.addAll(typeSet);
        return this;
    }
    
    public ValueInfoBuilder add(ValueSet valueSet) {
        valueSetBuilder.addAll(valueSet);
        return this;
    }
    
    public ValueInfoBuilder add(Wildcard wildcard, ValueInfo result) {
        add(wildcard, unwrap(result));
        add(result.getValueSet());
        return this;
    }
    
    public ValueInfoBuilder add(Wildcard wildcard, TypeSet typeSet) {
        add(replaceWildcard(wildcard, typeSet));
        return this;
    }
    
    public ValueInfo build() {
        TypeSet ts = typeSetBuilder.build();
        ValueSet vs = valueSetBuilder.build();
        return new ValueInfo(ts, vs);
    }
    
    public boolean isEmpty() {
        return typeSetBuilder.isEmpty();
    }
    
    public boolean looksEmpty() {
        if (typeSetBuilder.isEmpty())
            return true;
        String tts = typeSetBuilder.toString();
        if (tts.equals("~(nil)") || tts.equals("~(, nil)"))
            return true;
        return false;
    }
    
    private TypeSet unwrap(ValueInfo result) {
        return result.getTypeSet();
    }
    
    private static TypeSet replaceWildcard(Wildcard wildcard, TypeSet replacement) {
        TypeSetBuilder builder = new TypeSetBuilder();
        for (Type type : replacement.containedTypes())
            builder.add(wildcard.replaceWildcard(type));
        return builder.build();
    }
    
}