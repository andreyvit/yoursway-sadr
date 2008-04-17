package com.yoursway.sadr.blocks.foundation;

import com.yoursway.utils.facelets.GemstoneDefinitionBuilder;

public interface BuildingBlock {
    
    void contributeToRuntimeModel(GemstoneDefinitionBuilder<RuntimeModel> schema);
    
}
