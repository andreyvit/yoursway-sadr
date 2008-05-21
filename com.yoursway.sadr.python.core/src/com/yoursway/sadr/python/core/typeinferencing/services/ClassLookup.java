package com.yoursway.sadr.python.core.typeinferencing.services;

import java.util.Set;

import com.yoursway.sadr.blocks.integer_literals.IntegerTypesSupport;
import com.yoursway.sadr.blocks.integer_literals.RuntimeModelWithIntegerTypes;
import com.yoursway.sadr.python.core.runtime.PythonMetaType;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;

public interface ClassLookup {
    
    PythonMetaType lookupClass(String name);
    
    PythonMetaType findClass(String name);
    
    StandardTypes standardTypes();
    
    PythonMetaType[] allClasses();
    
    PythonMetaType[] findClassesWithMethod(String method);
    
    Set<PythonMetaType> findClassesByMethods(String[] methods);
    
    IntegerTypesSupport integerTypesSupport();
    
    RuntimeModelWithIntegerTypes runtimeModelWithIntegerTypes();
    
}
