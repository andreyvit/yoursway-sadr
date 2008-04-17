package com.yoursway.sadr.blocks.simple_types;

import com.yoursway.sadr.blocks.foundation.AbstractBuildingBlock;
import com.yoursway.sadr.blocks.foundation.AnalysisSchemaBuilder;
import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;

public class SimpleTypesBuildingBlock extends AbstractBuildingBlock {
    
    public void contributeToModel(PythonRuntimeModel model) {
    }
    
    @SuppressWarnings("unchecked")
    public void contributeToSchema(AnalysisSchemaBuilder builder) {
        builder.runtimeModelDef().addFacelet(new SimpleTypeModelFaceletFactory(),
                SimpleTypeModelFacelet.class);
    }
}
