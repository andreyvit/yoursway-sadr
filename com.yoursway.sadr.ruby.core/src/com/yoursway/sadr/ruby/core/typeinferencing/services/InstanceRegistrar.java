package com.yoursway.sadr.ruby.core.typeinferencing.services;

import com.yoursway.sadr.ruby.core.typeinferencing.values.InstanceValue;

public interface InstanceRegistrar {
    
    int registerInstance(InstanceValue value);
    
}