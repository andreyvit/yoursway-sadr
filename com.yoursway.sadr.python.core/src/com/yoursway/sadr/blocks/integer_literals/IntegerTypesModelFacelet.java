package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.simple_types.PythonSimpleType;
import com.yoursway.utils.facelets.Facelet;

public interface IntegerTypesModelFacelet extends Facelet<RuntimeModel> {
    
    PythonSimpleType intType();
    
    PythonSimpleType longType();
    
}
