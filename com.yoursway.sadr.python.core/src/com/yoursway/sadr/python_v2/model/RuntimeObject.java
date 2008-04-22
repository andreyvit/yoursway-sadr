package com.yoursway.sadr.python_v2.model;

public interface RuntimeObject {
    
    RuntimeObject getType();
    
    RuntimeObject getAttribute(String name);
    
    void setAttribute(String name, RuntimeObject object);
    
}
