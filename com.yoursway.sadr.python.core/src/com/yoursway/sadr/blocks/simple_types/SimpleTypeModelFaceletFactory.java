package com.yoursway.sadr.blocks.simple_types;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.utils.facelets.FaceletFactory;

public class SimpleTypeModelFaceletFactory implements FaceletFactory<SimpleTypeModelFacelet, RuntimeModel> {
    
    public SimpleTypeModelFacelet create(RuntimeModel model) {
        return new SimpleTypeModelFaceletImpl(model);
    }
    
}
