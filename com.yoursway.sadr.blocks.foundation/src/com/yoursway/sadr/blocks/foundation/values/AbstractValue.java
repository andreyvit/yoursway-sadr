package com.yoursway.sadr.blocks.foundation.values;

import com.yoursway.sadr.blocks.foundation.types.Type;

public abstract class AbstractValue implements Value {
	public Type getType() {
		throw new UnsupportedOperationException("This operation is not available");
	}
	
	@Override
	public String toString() {
	    return describe();
	}

}
