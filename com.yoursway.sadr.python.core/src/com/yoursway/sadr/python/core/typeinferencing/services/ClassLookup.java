package com.yoursway.sadr.python.core.typeinferencing.services;

import java.util.Set;

import com.yoursway.sadr.blocks.integer_literals.IntegerTypesSupport;
import com.yoursway.sadr.blocks.integer_literals.RuntimeModelWithIntegerTypes;
import com.yoursway.sadr.python.core.runtime.PythonClassType;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;

public interface ClassLookup {
    
    PythonClassType lookupClass(String name);
    
    PythonClassType findClass(String name);
    
    StandardTypes standardTypes();
    
    PythonClassType[] allClasses();
    
    PythonClassType[] findClassesWithMethod(String method);
    
    Set<PythonClassType> findClassesByMethods(String[] methods);
    
    IntegerTypesSupport integerTypesSupport();
    
    RuntimeModelWithIntegerTypes runtimeModelWithIntegerTypes();
    
}
