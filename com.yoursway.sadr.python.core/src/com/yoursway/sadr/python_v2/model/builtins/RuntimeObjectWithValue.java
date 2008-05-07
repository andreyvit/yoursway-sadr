package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

//FIXME: move to building blocks
public interface RuntimeObjectWithValue<ValueType> extends RuntimeObject {
    public ValueType getValue();
}
