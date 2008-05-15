package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.python.core.runtime.std.NewMethod;

public class PythonMetaClass extends PythonBasicClass {
    
    private final PythonClassType klass;
    
    public PythonMetaClass(PythonClassType klass, PythonRuntimeModel model) {
        this.klass = klass;
        new NewMethod(this, model);
    }
    
    public PythonClassType instanceClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass + "!";
    }
    
    @Override
    public PythonBasicClass superclassOfTheSameKind() {
        PythonClassType sup = klass.superclass();
        return (sup != null ? sup.metaClass() : null);
    }
    
}
