package com.yoursway.sadr.ruby.core.typeinferencing.values;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSet;

public class ArrayValue extends AbstractValue implements ValueTraits {
    
    private final List<ValueSet> items = new ArrayList<ValueSet>();
    
    public ArrayValue() {
    }
    
    public ValueSet combinedComponent() {
        MutableValueSet vs = new MutableValueSet();
        for (ValueSet item : items)
            vs.addAll(item);
        return vs.build();
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        for (ValueSet item : items) {
            if (first)
                first = false;
            else
                result.append(", ");
            result.append(item);
        }
        result.append(']');
        return result.toString();
    }
    
    public ValueTraits traits() {
        return this;
    }
    
    public String coherseToString() {
        throw new UnsupportedOperationException();
    }
    
    public boolean cohersibleToString() {
        return false;
    }
    
    public long integerValue() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isInteger() {
        return false;
    }
    
    public boolean coherseToBoolean() {
        throw new UnsupportedOperationException();
    }
    
    public boolean cohersibleToBoolean() {
        return false;
    }
    
}
