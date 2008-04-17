package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.simple_types.PythonSimpleType;

public class IntegerTypesModelFaceletImpl implements IntegerTypesModelFacelet {
    
    private final RuntimeModel model;
    private final IntegerTypesConfig config;
    private PythonSimpleType intType;
    private PythonSimpleType longType;
    
    public IntegerTypesModelFaceletImpl(RuntimeModel model, IntegerTypesConfig config) {
        this.model = model;
        this.config = config;
    }
    
    public void initializeFacelet() {
        intType = new PythonSimpleType(model, config.getIntTypeName());
        longType = new PythonSimpleType(model, config.getLongTypeName());
    }
    
    public PythonSimpleType intType() {
        return intType;
    }
    
    public PythonSimpleType longType() {
        return longType;
    }
    
}
