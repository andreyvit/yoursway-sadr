package com.yoursway.sadr.ruby.core.typeinferencing.valuesets;

import static com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSetFactory.valueSetWith;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.internal.AbstractValueSet;

public class MutableValueSet extends AbstractValueSet {
    
    private final Set<Value> values = new HashSet<Value>(5);
    
    public void add(Value value) {
        values.add(value);
    }
    
    public void addAll(ValueSet types) {
        for (Value value : types.containedValues())
            add(value);
    }
    
    public boolean isEmpty() {
        return values.isEmpty();
    }
    
    public ValueSet build() {
        return valueSetWith(values);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(15 * values.size());
        builder.append('(');
        for (Value type : values)
            (builder.length() > 1 ? builder.append(", ") : builder).append(type);
        builder.append(')');
        return builder.toString();
    }
    
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MutableValueSet other = (MutableValueSet) obj;
        if (!values.equals(other.values))
            return false;
        return true;
    }
    
    public Collection<Value> containedValues() {
        return values;
    }
    
    public String[] describePossibleValues() {
        return null;
    }
    
}
