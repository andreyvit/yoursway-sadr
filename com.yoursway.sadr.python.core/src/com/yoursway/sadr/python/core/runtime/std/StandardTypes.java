package com.yoursway.sadr.python.core.runtime.std;

import com.yoursway.sadr.blocks.simple_types.PythonSimpleType;
import com.yoursway.sadr.python.core.runtime.PythonClass;

public interface StandardTypes {
    
    PythonClass objectClass();
    
    PythonSimpleType intType();
    
    PythonSimpleType stringType();
    
    PythonSimpleType nilType();
    
    PythonSimpleType boolType();
    
    PythonSimpleType longType();
    
}