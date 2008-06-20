package com.yoursway.sadr.blocks.foundation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import com.yoursway.utils.gemstones.GemstoneDefinitionBuilder;

public class AnalysisSchemaBuilder {
    
    private final Collection<BuildingBlock> blocks = newArrayList();
    
    private final GemstoneDefinitionBuilder<RuntimeModel> runtimeModelSchema = GemstoneDefinitionBuilder.create();
    
    public void addBuildingBlock(BuildingBlock block) {
        blocks.add(block);
        block.contributeToRuntimeModel(runtimeModelSchema);
    }
     
    public AnalysisSchema build() {
        return new AnalysisSchema(blocks, runtimeModelSchema.build());
    }
    
}
