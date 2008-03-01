package com.yoursway.sadr.ruby.core.typeinferencing.values;

import com.yoursway.sadr.ruby.core.runtime.RubyMetaClass;

public class MetaClassValue extends AbstractValue implements ValueTraits {
    
    private final RubyMetaClass metaClass;
    
    public MetaClassValue(RubyMetaClass metaClass) {
        this.metaClass = metaClass;
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        return metaClass.toString();
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
