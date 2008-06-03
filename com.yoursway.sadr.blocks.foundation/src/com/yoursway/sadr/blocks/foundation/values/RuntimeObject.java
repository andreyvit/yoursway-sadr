package com.yoursway.sadr.blocks.foundation.values;

import java.util.Map;

import com.yoursway.sadr.blocks.foundation.types.Type;

/**
 * Represents a runtime object. Supports instance attributes name resolution.
 */

//FIXME: move to building blocks
public interface RuntimeObject extends Type, Value {
    
    RuntimeObject getType();
    
    RuntimeObject getAttribute(String name);
    
    Map<String, RuntimeObject> getAttributes();
    
    public <T> T convertValue(RuntimeObject type);
}
