package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

public interface PythonClass extends RuntimeObject {
    
    public abstract List<PythonClass> getSuperClasses();
    
    public abstract void setSuperClasses(List<PythonClass> supers);
    
}