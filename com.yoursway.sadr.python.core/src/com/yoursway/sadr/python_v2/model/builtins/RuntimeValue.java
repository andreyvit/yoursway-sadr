package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//FIXME: move to building blocks
public interface RuntimeValue<ValueType extends Value> extends RuntimeObject {
    public ValueType getValue();
}
