package com.yoursway.sadr.python_v2.model.builtins;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

/**
 * Represents a class type object. Supports class attributes name resolution.
 */
public class PythonClassImpl extends PythonObject implements PythonClass {
    
    private List<PythonClass> supers;
    
    public PythonClassImpl() {
        super(Builtins.TYPE);
        supers = new ArrayList<PythonClass>(1);
        supers.add(Builtins.OBJECT);
    }
    
    public PythonClassImpl(List<PythonClass> supers) {
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