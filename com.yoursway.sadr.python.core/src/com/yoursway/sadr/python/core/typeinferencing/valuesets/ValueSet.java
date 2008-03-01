package com.yoursway.sadr.python.core.typeinferencing.valuesets;

import java.util.Collection;

import com.yoursway.sadr.python.core.typeinferencing.values.Value;

public interface ValueSet {
    
    Collection<Value> containedValues();
    
    String[] describePossibleValues();
    
}
