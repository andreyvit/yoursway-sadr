package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.AbstractBuildingBlock;
import com.yoursway.sadr.blocks.foundation.AnalysisSchemaBuilder;
import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.simple_types.SimpleTypesSupport;
import com.yoursway.utils.facelets.FaceletFactory;
import com.yoursway.utils.facelets.GemstoneDefinitionBuilder;
import com.yoursway.utils.facelets.Slot;

public class IntegerTypesSupportImpl extends AbstractBuildingBlock implements IntegerTypesSupport {
    
    final IntegerTypesConfig config;
    final SimpleTypesSupport simpleTypesSupport;
    private Slot<RuntimeModelWithIntegerTypes> modelSlot;
    
    private IntegerTypesSupportImpl(AnalysisSchemaBuilder builder, IntegerTypesConfig config, SimpleTypesSupport simpleTypesSupport) {
        if (builder == null)
            throw new NullPointerException("builder is null");
        if (config == null)
            throw new NullPointerException("config is null");
        if (simpleTypesSupport == null)
            throw new NullPointerException("simpleTypesSupport is null");
        this.config = config;
        this.simpleTypesSupport = simpleTypesSupport;
        builder.addBuildingBlock(this);
    }
    
    public static IntegerTypesSupport create(AnalysisSchemaBuilder builder, IntegerTypesConfig config, SimpleTypesSupport simpleTypesSupport) {
        return new IntegerTypesSupportImpl(builder, config, simpleTypesSupport);
    }
    
    @Override
    public void contributeToRuntimeModel(GemstoneDefinitionBuilder<RuntimeModel> schema) {
        modelSlot = schema.addFacelet(new IntegerTypesModelFaceletFactory(),
                RuntimeModelWithIntegerTypes.class);
    }
    
    public class IntegerTypesModelFaceletFactory implements
            FaceletFactory<RuntimeModelWithIntegerTypes, RuntimeModel> {
        
        public RuntimeModelWithIntegerTypes create(RuntimeModel model) {
            return new RuntimeModelWithIntegerTypesImpl(model, IntegerTypesSupportImpl.this);
        }
        
    }
    
    public RuntimeModelWithIntegerTypes facelet(RuntimeModel model) {
        return model.get(modelSlot);
    }
}
