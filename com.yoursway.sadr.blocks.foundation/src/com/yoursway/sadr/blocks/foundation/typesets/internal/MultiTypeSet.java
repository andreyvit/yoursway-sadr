package com.yoursway.sadr.blocks.foundation.typesets.internal;

import java.util.Arrays;
import java.util.Collection;

import com.yoursway.sadr.blocks.foundation.types.Type;

public class MultiTypeSet extends AbstractTypeSet {
    
    private final Type[] types;
    
    private int hashCode = -1;
    
    public MultiTypeSet(Collection<Type> types) {
        if (types.isEmpty())
            throw new IllegalArgumentException("types should not be empty");
        this.types = types.toArray(new Type[types.size()]);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(15 * types.length);
        builder.append('(');
        for (Type type : types)
            (builder.length() > 1 ? builder.append(", ") : builder).append(type);
        builder.append(')');
        return builder.toString();
    }
    
    public Collection<Type> containedTypes() {
        return Arrays.asList(types);
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
        result = prime * result + Arrays.hashCode(types);
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
        final MultiTypeSet other = (MultiTypeSet) obj;
        if (!Arrays.equals(types, other.types))
            return false;
        return true;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
}
