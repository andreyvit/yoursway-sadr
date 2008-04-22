package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.Map;

public class LexicalScopeImpl implements LexicalScope {
    
    private final Map<String, RuntimeObject> namesToObjects = new HashMap<String, RuntimeObject>();
    
    public RuntimeObject lookup(String name) {
        return namesToObjects.get(name);
    }
    
    public void setName(String name, RuntimeObject object) {
        namesToObjects.put(name, object);
    }
    
}
