package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;

public class InstanceRegistrar {
    /**
     * Identifies an instance that was not created by user code.
     */
    public static final int BUILTIN_INSTANCE_ID = 0;
    
    private static int nextId = 1;
    
    private InstanceRegistrar() {
    }
    
    public static synchronized int registerInstance(RuntimeObject value) {
        return nextId++;
    }
    
}
