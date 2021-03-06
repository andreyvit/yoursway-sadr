package com.yoursway.sadr.ruby.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.RubySimpleType;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;

public class SimpleType extends AbstractType {
    
    private final RubySimpleType simpleType;
    
    public SimpleType(RubySimpleType simpleType) {
        this.simpleType = simpleType;
    }
    
    @Override
    public String toString() {
        return simpleType.name();
    }
    
    public void findMethodsByPrefix(String prefix, Collection<RubyMethod> methods) {
    }
    
    public RubySimpleType dtlSimpleType() {
        return simpleType;
    }
    
    public String describe() {
        return simpleType.name();
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((simpleType == null) ? 0 : simpleType.hashCode());
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
        final SimpleType other = (SimpleType) obj;
        if (simpleType == null) {
            if (other.simpleType != null)
                return false;
        } else if (!simpleType.equals(other.simpleType))
            return false;
        return true;
    }
    
}
