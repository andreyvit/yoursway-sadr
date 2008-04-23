package com.yoursway.sadr.python_v2.model;

import java.util.Set;

public interface RuntimeObject {
    
    RuntimeObject getType();
    
    RuntimeObject getAttribute(String name);
    
    void setAttribute(String name, RuntimeObject object);
    
    /**
     * @return objects' names (instance attributes and methods).
     */
    Set<String> getAttributeNames();
}
