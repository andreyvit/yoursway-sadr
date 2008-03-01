package com.yoursway.sadr.ruby.core.typeinferencing.valuesets.internal;

import java.util.Collection;
import java.util.Collections;

import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;

public class ZeroValueSet extends AbstractValueSet {
    
    public ZeroValueSet() {
    }
    
    @Override
    public String toString() {
        return "()";
    }
    
    public Collection<Value> containedValues() {
        return Collections.emptyList();
    }
    
    public String[] describePossibleValues() {
        return new String[0];
    }
    
}
