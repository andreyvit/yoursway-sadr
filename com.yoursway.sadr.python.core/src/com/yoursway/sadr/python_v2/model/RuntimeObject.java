package com.yoursway.sadr.python_v2.model;

import java.util.Map;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.values.Value;

/**
 * Represents a runtime object. Supports instance attributes name resolution.
 */

//FIXME: move to building blocks
public interface RuntimeObject extends Type, Value {
    
    RuntimeObject getType();
    
    RuntimeObject getAttribute(String name);
    
    Map<String, RuntimeObject> getAttributes();
    
    InstanceHistory instanceHistory();
    
    public <T> T convertValue(RuntimeObject type);
    
}
