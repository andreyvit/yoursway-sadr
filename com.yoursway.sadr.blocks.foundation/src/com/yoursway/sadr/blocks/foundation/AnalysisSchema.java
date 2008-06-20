package com.yoursway.sadr.blocks.foundation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import com.yoursway.utils.gemstones.GemstoneDefinition;

public class AnalysisSchema {
    
    private final Collection<BuildingBlock> blocks;
    
    private final GemstoneDefinition<RuntimeModel> runtimeModel;
    
    public AnalysisSchema(Collection<BuildingBlock> blocks, GemstoneDefinition<RuntimeModel> runtimeModel) {
        this.blocks = newArrayList(blocks);
        this.runtimeModel = runtimeModel;
    }
    
    public GemstoneDefinition<RuntimeModel> forRuntimeModel() {
        return runtimeModel;
    }
    
    @Override
    public String toString() {
        return "Schema(" + blocks + ")";
    }
    
}
