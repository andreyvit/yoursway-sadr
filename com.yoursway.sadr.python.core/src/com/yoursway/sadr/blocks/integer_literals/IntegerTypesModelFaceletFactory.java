package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.utils.facelets.FaceletFactory;

public class IntegerTypesModelFaceletFactory implements
        FaceletFactory<IntegerTypesModelFacelet, RuntimeModel> {
    
    private final IntegerTypesConfig config;
    
    public IntegerTypesModelFaceletFactory(IntegerTypesConfig config) {
        this.config = config;
    }
    
    public IntegerTypesModelFacelet create(RuntimeModel model) {
        return new IntegerTypesModelFaceletImpl(model, config);
    }
    
}
