package com.yoursway.sadr.python.core.typeinferencing.values;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.ValueTraits;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class InstanceValue extends AbstractValue implements ValueTraits {
    
    private final PythonClass klass;
    
    private final Map<String, ValueSet> fieldValues = new HashMap<String, ValueSet>();
    
    private final int id;
    
    public InstanceValue(PythonClass klass, InstanceRegistrar registrar) {
        this.klass = klass;
        id = registrar.registerInstance(this);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(klass.toString()).append("#").append(id).append(" <");
        boolean first = true;
        for (Map.Entry<String, ValueSet> entry : fieldValues.entrySet()) {
            if (first)
                first = false;
            else
                result.append(", ");
            result.append(entry.getKey() + " = " + entry.getValue());
        }
        result.append('>');
        return result.toString();
    }
    
    public String describe() {
        return toString();
    }
}
