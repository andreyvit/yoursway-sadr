package com.yoursway.sadr.ruby.core.typeinferencing.values;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSet;

public class InstanceValue extends AbstractValue implements ValueTraits {
    
    private final RubyClass klass;
    
    private final Map<String, ValueSet> fieldValues = new HashMap<String, ValueSet>();
    
    private final int id;
    
    public InstanceValue(RubyClass klass, InstanceRegistrar registrar) {
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
