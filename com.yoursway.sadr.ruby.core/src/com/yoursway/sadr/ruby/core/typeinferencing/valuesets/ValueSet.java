package com.yoursway.sadr.ruby.core.typeinferencing.valuesets;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;

public interface ValueSet {
    
    Collection<Value> containedValues();
    
    String[] describePossibleValues();
    
}
