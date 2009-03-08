package com.yoursway.sadr.blocks.foundation.values;

public abstract class AbstractValue implements Value {
	@Override
	public String toString() {
	    return describe();
	}
	
	public abstract String describe();
}
