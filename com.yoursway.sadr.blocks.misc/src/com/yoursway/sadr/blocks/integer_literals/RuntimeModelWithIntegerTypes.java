package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.simple_types.SimpleType;
import com.yoursway.utils.gemstones.Facelet;

public interface RuntimeModelWithIntegerTypes extends Facelet<RuntimeModel> {
    
    SimpleType intType();
    
    SimpleType longType();
    
}
