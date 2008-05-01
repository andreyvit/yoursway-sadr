package com.yoursway.sadr.python_v2.model;

import java.util.Map;
import java.util.Set;

/**
 * Represents a runtime object. Supports instance attributes name resolution.
 */

public interface RuntimeObject {
    
    RuntimeObject getType();
    
    RuntimeObject getAttribute(String name);
    
    void setAttribute(String name, RuntimeObject object);
    
    /**
     * @return objects' names (instance attributes).
     */
    Set<String> getAttributeNames();

    public Map<String, RuntimeObject> getDict();
}
