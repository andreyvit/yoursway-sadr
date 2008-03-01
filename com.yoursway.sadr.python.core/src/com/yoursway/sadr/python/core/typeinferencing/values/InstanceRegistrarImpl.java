package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;

public class InstanceRegistrarImpl implements InstanceRegistrar {
    
    private int nextId = 1;
    
    public synchronized int registerInstance(InstanceValue value) {
        return nextId++;
    }
    
}
