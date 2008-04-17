package com.yoursway.sadr.blocks.simple_types;

import com.yoursway.sadr.blocks.foundation.AbstractBuildingBlock;
import com.yoursway.sadr.blocks.foundation.AnalysisSchemaBuilder;
import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.utils.facelets.FaceletFactory;
import com.yoursway.utils.facelets.GemstoneDefinitionBuilder;
import com.yoursway.utils.facelets.Slot;

public class SimpleTypesSupportImpl extends AbstractBuildingBlock implements SimpleTypesSupport {
    
    private Slot<RuntimeModelWithSimpleTypes> runtimeModelSlot;
    
    private SimpleTypesSupportImpl(AnalysisSchemaBuilder builder) {
        builder.addBuildingBlock(this);
    }
    
    public static SimpleTypesSupport create(AnalysisSchemaBuilder builder) {
        return new SimpleTypesSupportImpl(builder);
    }
    
    @Override
    public void contributeToRuntimeModel(GemstoneDefinitionBuilder<RuntimeModel> schema) {
        runtimeModelSlot = schema.addFacelet(new ModelFaceletFactory(), RuntimeModelWithSimpleTypes.class);
    }
    
    public SimpleType newSimpleType(RuntimeModel model, String name) {
        return new SimpleTypeImpl(impl(model), name);
    }
    
    private static class ModelFaceletFactory implements
            FaceletFactory<RuntimeModelWithSimpleTypes, RuntimeModel> {
        
        public RuntimeModelWithSimpleTypes create(RuntimeModel model) {
            return new RuntimeModelWithSimpleTypesImpl(model);
        }
        
    }
    
    public RuntimeModelWithSimpleTypes facelet(RuntimeModel model) {
        return model.get(runtimeModelSlot);
    }
    
    public RuntimeModelWithSimpleTypesImpl impl(RuntimeModel model) {
        return (RuntimeModelWithSimpleTypesImpl) facelet(model);
    }
    
}
