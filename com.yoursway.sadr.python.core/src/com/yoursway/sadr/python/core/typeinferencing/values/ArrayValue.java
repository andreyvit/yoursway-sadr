package com.yoursway.sadr.python.core.typeinferencing.values;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class ArrayValue extends AbstractValue {
    
    private final List<ValueSet> items = new ArrayList<ValueSet>();
    
    public ArrayValue() {
    }
    
    public ValueSet combinedComponent() {
        MutableValueSet vs = new MutableValueSet();
        for (ValueSet item : items)
            vs.addAll(item);
        return vs.build();
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
}
