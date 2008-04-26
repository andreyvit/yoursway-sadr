package com.yoursway.sadr.python_v2.model;

import java.util.Map;
import java.util.Set;

public class PythonObject implements RuntimeObject {
    
    private Map<String, RuntimeObject> attributes;
    private PythonClass type;
    
    protected RuntimeObject lookupInSuperclasses(String name) {
        for (PythonClass cls : type.getSuperClasses()) {
            RuntimeObject object = cls.getDict().get(name);
            if (object != null)
                return object;
        }
        return null;
    }
    
    protected Map<String, RuntimeObject> getDict() {
        return attributes;
    }
    
    public PythonObject(PythonClass type) {
        assert type != null;
        this.type = type;
    }
    
    public RuntimeObject getAttribute(String name) {
        //TODO try __getattribute__ execution.
        if (!attributes.containsKey(name)) {
            // TODO try __getattr__ execution
            return lookupInSuperclasses(name);
        }
        return attributes.get(name);
    }
    
    public void setAttribute(String name, RuntimeObject object) {
        assert null != name && null != object;
        //TODO try __setattr__ execution
        attributes.put(name, object);
    }
    
    public RuntimeObject getType() {
        return type;
    }
    
    public void setType(PythonClass type) {
        this.type = type;
    }
    
    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }
    
}
