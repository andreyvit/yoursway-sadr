package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.simple_types.SimpleType;
import com.yoursway.utils.facelets.FaceletImpl;

public class RuntimeModelWithIntegerTypesImpl extends FaceletImpl<RuntimeModel> implements
        RuntimeModelWithIntegerTypes {
    
    private final IntegerTypesSupportImpl block;
    
    private SimpleType intType;
    
    private SimpleType longType;
    
    public RuntimeModelWithIntegerTypesImpl(RuntimeModel gemstone, IntegerTypesSupportImpl block) {
        super(gemstone);
        if (block == null)
            throw new NullPointerException("block is null");
        this.block = block;
    }
    
    @Override
    public void initializeFacelet() {
        intType = block.simpleTypesSupport.newSimpleType(gemstone(), block.config.intTypeName);
        longType = block.simpleTypesSupport.newSimpleType(gemstone(), block.config.longTypeName);
    }
    
    public SimpleType intType() {
        return intType;
    }
    
    public SimpleType longType() {
        return longType;
    }
    
}
