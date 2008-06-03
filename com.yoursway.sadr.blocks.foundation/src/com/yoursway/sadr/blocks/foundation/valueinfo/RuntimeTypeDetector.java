package com.yoursway.sadr.blocks.foundation.valueinfo;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.values.Value;

public abstract class RuntimeTypeDetector {

	public abstract Type getType(Value value);

}
