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
    
    //    /**
    //     * @deprecated to be removed from public interface or replaced with
    //     *             generating method.
    //     * @param name
    //     *            name to set
    //     * @param object
    //     *            value
    //     */
    //    @Deprecated
    void setAttribute(String name, RuntimeObject object);
    
    //    
    //    /**
    //     * @return objects' names (instance attributes).
    //     */
    //    Set<String> getAttributeNames();
    
    Map<String, RuntimeObject> getDict();
    
    InstanceHistory instanceHistory();
}
