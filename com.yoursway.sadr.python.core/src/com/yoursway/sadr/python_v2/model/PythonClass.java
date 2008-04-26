package com.yoursway.sadr.python_v2.model;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python_v2.model.builtins.Builtins;

public class PythonClass extends PythonObject implements RuntimeObject {
    
    private List<PythonClass> supers;
    
    public PythonClass() {
        super(Builtins.TYPE);
        supers = new ArrayList<PythonClass>(1);
        supers.add(Builtins.OBJECT);
    }
    
    public PythonClass(List<PythonClass> supers) {
        super(Builtins.TYPE);
        this.supers = supers;
    }
    
    public List<PythonClass> getSuperClasses() {
        return supers;
    }
    
    public void setSuperClasses(List<PythonClass> supers) {
        this.supers = supers;
    }
    
    @Override
    protected RuntimeObject lookupInSuperclasses(String name) {
        for (PythonClass cls : this.supers) {
            RuntimeObject object = cls.getDict().get(name);
            if (object != null)
                return object;
        }
        return null;
    }
    
}