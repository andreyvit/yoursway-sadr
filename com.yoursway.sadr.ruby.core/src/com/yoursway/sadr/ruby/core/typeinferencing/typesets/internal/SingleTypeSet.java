package com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal;

import java.util.Collection;
import java.util.Collections;

import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;

public class SingleTypeSet extends AbstractTypeSet {
    
    private final Type type;
    
    public SingleTypeSet(Type type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "(" + type + ")";
    }
    
    public Collection<Type> containedTypes() {
        return Collections.singletonList(type);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        final SingleTypeSet other = (SingleTypeSet) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
}
