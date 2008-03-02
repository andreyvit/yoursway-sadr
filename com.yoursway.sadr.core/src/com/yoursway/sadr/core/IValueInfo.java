package com.yoursway.sadr.core;

import com.yoursway.sadr.engine.Result;

public interface IValueInfo extends Result {
    
    boolean equals(Object obj);
    
    int hashCode();
    
    boolean isEmpty();
    
    String[] describePossibleTypes();
    
    String[] describePossibleValues();
    
}
