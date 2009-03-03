package com.yoursway.sadr.blocks.foundation.values;

import java.util.Map;

import com.yoursway.sadr.blocks.foundation.types.Type;

/**
 * Represents a runtime object. Supports instance attributes name resolution.
 */

//FIXME: move to building blocks
public interface RuntimeObject extends Type, Value {
    
    RuntimeObject getType();
    
    RuntimeObject getScopedAttribute(String name);
    
    Map<String, RuntimeObject> getAttributes();
    
    public <T extends AbstractValue> T convertValue(Class<T> type);
}
