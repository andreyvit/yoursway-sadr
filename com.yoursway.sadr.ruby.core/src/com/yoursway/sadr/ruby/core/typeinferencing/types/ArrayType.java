package com.yoursway.sadr.ruby.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;

public class ArrayType extends AbstractType {
    
    private final Type component;
    
    public ArrayType(Type component) {
        this.component = component;
    }
    
    public Type component() {
        return component;
    }
    
    @Override
    public String toString() {
        return component.toString() + "[]";
    }
    
    public String describe() {
        return component.describe() + "[]";
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
    }
    
    public void findMethodsByPrefix(String prefix, Collection<RubyMethod> methods) {
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((component == null) ? 0 : component.hashCode());
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
        final ArrayType other = (ArrayType) obj;
        if (component == null) {
            if (other.component != null)
                return false;
        } else if (!component.equals(other.component))
            return false;
        return true;
    }
    
}
