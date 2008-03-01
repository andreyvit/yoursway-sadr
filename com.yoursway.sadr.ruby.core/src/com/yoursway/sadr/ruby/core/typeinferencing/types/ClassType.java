package com.yoursway.sadr.ruby.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;

public class ClassType extends AbstractType {
    
    private final RubyClass klass;
    
    public ClassType(RubyClass klass) {
        this.klass = klass;
    }
    
    public RubyClass runtimeClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass.toString();
    }
    
    public void findMethodsByPrefix(String prefix, Collection<RubyMethod> methods) {
        klass.findMethodsByPrefix(prefix, methods);
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        klass.findMethod(name, requestor);
    }
    
    public String describe() {
        return klass.name();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((klass == null) ? 0 : klass.hashCode());
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
        final ClassType other = (ClassType) obj;
        if (klass == null) {
            if (other.klass != null)
                return false;
        } else if (!klass.equals(other.klass))
            return false;
        return true;
    }
    
}
