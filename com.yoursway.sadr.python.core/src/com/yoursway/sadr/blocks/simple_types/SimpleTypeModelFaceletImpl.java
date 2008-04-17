package com.yoursway.sadr.blocks.simple_types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;

public class SimpleTypeModelFaceletImpl implements SimpleTypeModelFacelet {
    
    private final Collection<PythonSimpleType> simpleTypes = new ArrayList<PythonSimpleType>();
    
    private final Map<String, PythonSimpleType> namesToSimpleTypes = new HashMap<String, PythonSimpleType>();
    
    public SimpleTypeModelFaceletImpl(RuntimeModel model) {
    }
    
    public void addSimpleType(PythonSimpleType simpleType) {
        simpleTypes.add(simpleType);
        namesToSimpleTypes.put(simpleType.name(), simpleType);
    }
    
    public void initializeFacelet() {
    }
    
}
