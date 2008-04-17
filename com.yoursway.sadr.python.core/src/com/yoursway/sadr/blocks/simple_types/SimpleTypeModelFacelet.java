package com.yoursway.sadr.blocks.simple_types;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.utils.facelets.Facelet;

public interface SimpleTypeModelFacelet extends Facelet<RuntimeModel> {
    
    void addSimpleType(PythonSimpleType simpleType);
    
}
