package com.yoursway.sadr.python.core.typeinferencing.types;

import com.yoursway.sadr.blocks.foundation.types.AbstractType;
import com.yoursway.sadr.python.core.runtime.PythonClass;

public class InstanceType extends AbstractType {
    
    private final PythonClass klass;
    
    public InstanceType(PythonClass klass) {
        this.klass = klass;
    }
    
    public PythonClass runtimeClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass.toString();
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
        final InstanceType other = (InstanceType) obj;
        if (klass == null) {
            if (other.klass != null)
                return false;
        } else if (!klass.equals(other.klass))
            return false;
        return true;
    }
    
}
