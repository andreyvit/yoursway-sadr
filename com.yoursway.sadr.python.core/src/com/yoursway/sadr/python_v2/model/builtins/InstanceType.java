package com.yoursway.sadr.python_v2.model.builtins;

public final class InstanceType extends PythonClassType implements PythonClass {
    private final PythonClassType klass;
    
    public InstanceType(PythonClass klass) {
        this.klass = this;
    }
    
    public PythonClass runtimeClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass.describe();
    }
}