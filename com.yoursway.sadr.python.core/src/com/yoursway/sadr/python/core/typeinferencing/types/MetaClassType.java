package com.yoursway.sadr.python.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.python.core.runtime.RubyMetaClass;
import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;

public class MetaClassType extends AbstractType {
    
    private final RubyMetaClass klass;
    
    public MetaClassType(RubyMetaClass klass) {
        this.klass = klass;
    }
    
    public RubyMetaClass runtimeMetaClass() {
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
        return klass.instanceClass().name();
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
        final MetaClassType other = (MetaClassType) obj;
        if (klass == null) {
            if (other.klass != null)
                return false;
        } else if (!klass.equals(other.klass))
            return false;
        return true;
    }
    
}