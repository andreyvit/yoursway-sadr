package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.python.core.runtime.std.NewMethod;

public class PythonMetaClass extends PythonBasicClass {
    
    private final PythonClass klass;
    
    public PythonMetaClass(PythonClass klass, PythonRuntimeModel model) {
        this.klass = klass;
        new NewMethod(this, model);
    }
    
    public PythonClass instanceClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass + "!";
    }
    
    @Override
    public PythonBasicClass superclassOfTheSameKind() {
        PythonClass sup = klass.superclass();
        return (sup != null ? sup.metaClass() : null);
    }
    
}
