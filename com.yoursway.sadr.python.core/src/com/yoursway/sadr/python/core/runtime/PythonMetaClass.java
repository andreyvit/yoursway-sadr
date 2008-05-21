package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.python.core.runtime.std.NewMethod;

public class PythonMetaClass extends PythonBasicClass {
    
    private final PythonMetaType klass;
    
    public PythonMetaClass(PythonMetaType klass, PythonRuntimeModel model) {
        this.klass = klass;
        new NewMethod(this, model);
    }
    
    public PythonMetaType instanceClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass + "!";
    }
    
    @Override
    public PythonBasicClass superclassOfTheSameKind() {
        PythonMetaType sup = klass.superclass();
        return (sup != null ? sup.metaClass() : null);
    }
    
}
