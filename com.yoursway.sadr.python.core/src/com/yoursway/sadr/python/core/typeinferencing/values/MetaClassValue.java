package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.ValueTraits;
import com.yoursway.sadr.python.core.runtime.PythonMetaClass;

public class MetaClassValue extends AbstractValue implements ValueTraits {
    
    private final PythonMetaClass metaClass;
    
    public MetaClassValue(PythonMetaClass metaClass) {
        this.metaClass = metaClass;
    }
    
    public String describe() {
        return metaClass.toString();
    }
    
    public ValueTraits traits() {
        return this;
    }
}
