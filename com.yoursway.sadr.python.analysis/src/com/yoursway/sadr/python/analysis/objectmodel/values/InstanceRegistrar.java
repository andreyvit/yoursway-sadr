package com.yoursway.sadr.python.analysis.objectmodel.values;

import com.yoursway.sadr.blocks.foundation.values.Value;

public class InstanceRegistrar {
    /**
     * Identifies an instance that was not created by user code.
     */
    public static final int BUILTIN_INSTANCE_ID = 0;
    
    private static int nextId = 1;
    
    private InstanceRegistrar() {
    }
    
    public static synchronized int registerInstance(Value value) {
        return nextId++;
    }
    
}
