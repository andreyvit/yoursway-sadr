package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Sinner;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.ArrayWildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.StarWildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSet;

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
    
    public void addResultOf(ValueInfoGoal goal, Sinner victim) {
        add(goal.result(victim));
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
            builder.add(replaceWildcard(wildcard, type));
        return builder.build();
    }
    
    private static Type replaceWildcard(Wildcard wildcard, Type replacement) {
        if (wildcard == StarWildcard.INSTANCE)
            return replacement;
        else if (wildcard instanceof ArrayWildcard) {
            ArrayWildcard arr = (ArrayWildcard) wildcard;
            Type replaced = replaceWildcard(arr.component(), replacement);
            return new ArrayType(replaced);
        }
        throw new AssertionError("Unreachable in MutableTypeSetResultImpl.replaceWildcard");
    }
    
}
