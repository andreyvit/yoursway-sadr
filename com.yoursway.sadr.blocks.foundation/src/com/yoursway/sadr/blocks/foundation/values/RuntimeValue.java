package com.yoursway.sadr.blocks.foundation.values;

public interface RuntimeValue<ValueType extends AbstractValue> extends RuntimeObject {
    public ValueType getValue();
}
