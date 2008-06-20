package com.yoursway.sadr.blocks.dedicated_calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.utils.gemstones.FaceletImpl;

class RuntimeModelWithSimpleTypesImpl extends FaceletImpl<RuntimeModel> implements
        RuntimeModelWithSimpleTypes {
    
    private final Collection<SimpleType> simpleTypes = new ArrayList<SimpleType>();
    
    private final Map<String, SimpleType> namesToSimpleTypes = new HashMap<String, SimpleType>();
    
    public RuntimeModelWithSimpleTypesImpl(RuntimeModel gemstone) {
        super(gemstone);
    }
    
    public void addSimpleType(SimpleType simpleType) {
        simpleTypes.add(simpleType);
        namesToSimpleTypes.put(simpleType.name(), simpleType);
    }
    
    public String findSimpleType(String name) {
        return null;
    }
    
}
