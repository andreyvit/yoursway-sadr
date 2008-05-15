package com.yoursway.sadr.python.core.runtime.std;

import com.yoursway.sadr.blocks.simple_types.SimpleType;
import com.yoursway.sadr.python.core.runtime.PythonClass;

public interface StandardTypes {
    
    PythonClassType objectClass();
    
    SimpleType stringType();
    
    SimpleType nilType();
    
    SimpleType boolType();
    
}