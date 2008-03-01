package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.python.core.runtime.RubySimpleType;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.types.Type;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSet;

public class TypeAnalyzer {
    
    private boolean isInt;
    
    private boolean isString;
    
    public TypeAnalyzer(TypeSet typeSet, ClassLookup lookup) {
        StandardTypes standard = lookup.standardTypes();
        for (Type type : typeSet.containedTypes())
            if (type instanceof SimpleType) {
                RubySimpleType simpleType = ((SimpleType) type).dtlSimpleType();
                if (simpleType == standard.intType())
                    isInt = true;
                else if (simpleType == standard.stringType())
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
