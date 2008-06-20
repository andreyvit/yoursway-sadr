package com.yoursway.sadr.blocks.dedicated_calls;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;

public interface SimpleTypesSupport {
    
    SimpleType newSimpleType(RuntimeModel model, String name);
    
    RuntimeModelWithSimpleTypes facelet(RuntimeModel model);
    
}
