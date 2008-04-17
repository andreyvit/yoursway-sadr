package com.yoursway.sadr.python.core.typeinferencing.services;

import java.util.Set;

import com.yoursway.sadr.blocks.integer_literals.IntegerTypesSupport;
import com.yoursway.sadr.blocks.integer_literals.RuntimeModelWithIntegerTypes;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;

public interface ClassLookup {
    
    PythonClass lookupClass(String name);
    
    PythonClass findClass(String name);
    
    StandardTypes standardTypes();
    
    PythonClass[] allClasses();
    
    PythonClass[] findClassesWithMethod(String method);
    
    Set<PythonClass> findClassesByMethods(String[] methods);
    
    IntegerTypesSupport integerTypesSupport();
    
    RuntimeModelWithIntegerTypes runtimeModelWithIntegerTypes();
    
}
