package com.yoursway.sadr.blocks.foundation;

import com.yoursway.utils.facelets.GemstoneDefinition;
import com.yoursway.utils.facelets.GemstoneDefinitionImpl;

public class AnalysisSchemaBuilderImpl implements AnalysisSchemaBuilder {
    
    private final GemstoneDefinition<RuntimeModel> runtimeModelDef = GemstoneDefinitionImpl.create();
    
    public GemstoneDefinition<RuntimeModel> runtimeModelDef() {
        return runtimeModelDef;
    }
    
    public void addBuildingBlock(BuildingBlock block) {
        block.contributeToSchema(this);
    }
    
}
