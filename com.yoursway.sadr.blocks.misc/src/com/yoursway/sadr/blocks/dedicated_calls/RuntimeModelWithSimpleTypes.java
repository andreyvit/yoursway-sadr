package com.yoursway.sadr.blocks.dedicated_calls;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.utils.gemstones.Facelet;

public interface RuntimeModelWithSimpleTypes extends Facelet<RuntimeModel> {
    
    String findSimpleType(String name);
    
}
