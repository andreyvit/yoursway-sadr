package com.yoursway.sadr.blocks.simple_types;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.utils.gemstones.Facelet;

public interface RuntimeModelWithSimpleTypes extends Facelet<RuntimeModel> {
    
    String findSimpleType(String name);
    
}
