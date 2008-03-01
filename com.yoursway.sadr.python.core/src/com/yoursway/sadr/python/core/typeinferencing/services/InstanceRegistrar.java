package com.yoursway.sadr.python.core.typeinferencing.services;

import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;

public interface InstanceRegistrar {
    
    int registerInstance(InstanceValue value);
    
}