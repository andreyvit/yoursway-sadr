package com.yoursway.sadr.python.core.runtime;

public abstract class PythonField extends PythonVariable {
    
    private final PythonBasicClass klass;
    private final String name;
    
    public PythonField(PythonBasicClass klass, String name) {
        this.klass = klass;
        this.name = name;
        klass.addField(this);
    }
    
    public String name() {
        return name;
    }
    
    public PythonBasicClass container() {
        return klass;
    }
    
    @Override
    public String toString() {
        if (klass instanceof PythonMetaClass)
            return klass + ".@@" + name;
        else
            return klass + ".@" + name;
    }
    
}
