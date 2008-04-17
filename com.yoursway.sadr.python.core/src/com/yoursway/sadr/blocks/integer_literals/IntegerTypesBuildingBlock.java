package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.AbstractBuildingBlock;
import com.yoursway.sadr.blocks.foundation.AnalysisSchemaBuilder;
import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;

public class IntegerTypesBuildingBlock extends AbstractBuildingBlock {
    
    private final IntegerTypesConfig config;
    
    public IntegerTypesBuildingBlock(IntegerTypesConfig config) {
        this.config = config;
    }
    
    public void contributeToModel(PythonRuntimeModel model) {
    }
    
    @SuppressWarnings("unchecked")
    public void contributeToSchema(AnalysisSchemaBuilder builder) {
        builder.runtimeModelDef().addFacelet(new IntegerTypesModelFaceletFactory(config),
                IntegerTypesModelFacelet.class);
    }
}
