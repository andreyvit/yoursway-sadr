package com.yoursway.sadr.python.core.typeinferencing.valuesets;

import java.util.Collection;
import java.util.Collections;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.internal.MultiValueSet;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.internal.ZeroValueSet;

public class ValueSetFactory {
    
    private static final ValueSet EMPTY = new ZeroValueSet();
    
    public static ValueSet valueSetWith(Value value) {
        return new MultiValueSet(Collections.singletonList(value));
    }
    
    public static ValueSet valueSetWith(Collection<Value> values) {
        if (values.isEmpty())
            return EMPTY;
        else
            return new MultiValueSet(values);
    }
    
    public static ValueSet emptyValueSet() {
        return EMPTY;
    }
    
}
