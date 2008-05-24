package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.constructs.CallC;

public final class InstanceType extends PythonClassType {
    private final PythonClassType klass;
    
    public InstanceType(PythonClassType klass) {
        this.klass = this;
    }
    
    public PythonClassType runtimeClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass.describe();
    }
    
    public static InstanceType createSelf(CallC expression) {
        // TODO Auto-generated method stub
        return null;
    }
}