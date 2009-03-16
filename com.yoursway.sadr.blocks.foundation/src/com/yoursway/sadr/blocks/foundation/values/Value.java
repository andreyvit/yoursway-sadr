package com.yoursway.sadr.blocks.foundation.values;

import com.yoursway.sadr.blocks.foundation.types.Type;

public interface Value {
    Type getType();
    
	String describe();
}
