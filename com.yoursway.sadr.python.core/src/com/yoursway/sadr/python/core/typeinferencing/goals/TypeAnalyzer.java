package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSet;
import com.yoursway.sadr.blocks.simple_types.SimpleType;
import com.yoursway.sadr.blocks.simple_types.SimpleTypeItem;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;

public class TypeAnalyzer {
    
    private boolean isInt;
    
    private boolean isString;
    
    public TypeAnalyzer(TypeSet typeSet, ClassLookup lookup) {
        SimpleType intType = lookup.runtimeModelWithIntegerTypes().intType();
        SimpleType stringType = lookup.standardTypes().stringType();
        for (Type type : typeSet.containedTypes())
            if (type instanceof SimpleTypeItem) {
                SimpleType simpleType = ((SimpleTypeItem) type).simpleType();
                if (simpleType == intType)
                    isInt = true;
                else if (simpleType == stringType)
                    isString = true;
            }
    }
    
    public boolean isInt() {
        return isInt;
    }
    
    public boolean isString() {
        return isString;
    }
    
    public boolean cohersibleToString() {
        return isInt || isString;
    }
    
}
