package com.yoursway.sadr.python.core.runtime.std;

import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonSimpleType;

public interface StandardTypes {
    
    PythonClass objectClass();
    
    PythonSimpleType intType();
    
    PythonSimpleType stringType();
    
    PythonSimpleType nilType();
    
    PythonSimpleType boolType();
    
}