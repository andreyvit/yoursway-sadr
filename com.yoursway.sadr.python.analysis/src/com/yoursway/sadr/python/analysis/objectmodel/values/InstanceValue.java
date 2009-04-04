package com.yoursway.sadr.python.analysis.objectmodel.values;

import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;

public class InstanceValue extends PythonValue {
    
    private final PythonType type;
    
    public InstanceValue(PythonType receiverType) {
        this.type = receiverType;
    }
    
    @Override
    public PythonType getType() {
        return type;
    }
    
    @Override
    public String describe() {
        return "<" + getType().describe() + ">";
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
        InstanceValue other = (InstanceValue) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
    
}
