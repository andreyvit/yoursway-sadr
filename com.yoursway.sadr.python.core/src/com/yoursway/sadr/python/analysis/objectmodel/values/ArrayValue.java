package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;
import com.yoursway.sadr.python.analysis.objectmodel.types.TypeType;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.MutableValueSet;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class ArrayValue extends PythonValue {
    
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
    
    @Override
    public PythonType getType() {
        return TypeType.instance;
    }
}
