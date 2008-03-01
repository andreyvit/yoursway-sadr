package com.yoursway.sadr.ruby.core.typeinferencing.typesets;

import static com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetFactory.typeSetWith;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal.AbstractTypeSet;

public class TypeSetBuilder extends AbstractTypeSet {
    
    private final Set<Type> types = new HashSet<Type>(5);
    
    public void add(Type type) {
        types.add(type);
    }
    
    public void addAll(TypeSet types) {
        for (Type type : types.containedTypes())
            add(type);
    }
    
    public boolean isEmpty() {
        return types.isEmpty();
    }
    
    public TypeSet build() {
        return typeSetWith(types);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(15 * types.size());
        builder.append("~(");
        for (Type type : types)
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
        final TypeSetBuilder other = (TypeSetBuilder) obj;
        if (types == null) {
            if (other.types != null)
                return false;
        } else if (!types.equals(other.types))
            return false;
        return true;
    }
    
    public Collection<Type> containedTypes() {
        return types;
    }
}
