package com.yoursway.sadr.python.core.typeinferencing.valuesets.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.Value;

public class MultiValueSet extends AbstractValueSet {
    
    private final Value[] values;
    
    private int hashCode = -1;
    
    public MultiValueSet(Collection<Value> values) {
        if (values.size() < 1)
            throw new IllegalArgumentException("Use ZeroValueSet for empty value sets!");
        this.values = values.toArray(new Value[values.size()]);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(15 * values.length);
        builder.append('(');
        for (Value type : values)
            (builder.length() > 1 ? builder.append(", ") : builder).append(type);
        builder.append(')');
        return builder.toString();
    }
    
    public Collection<Value> containedValues() {
        return Arrays.asList(values);
    }
    
    @Override
    public int hashCode() {
        if (hashCode == -1) {
            hashCode = calcHashCode();
            if (hashCode == -1)
                hashCode = 0;
        }
        return hashCode;
    }
    
    private int calcHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(values);
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MultiValueSet other = (MultiValueSet) obj;
        if (!Arrays.equals(values, other.values))
            return false;
        return true;
    }
    
    public String[] describePossibleValues() {
        List<String> result = new ArrayList<String>();
        for (Value value : containedValues())
            result.add(value.describe());
        Collections.sort(result);
        return result.toArray(new String[result.size()]);
    }
    
}
