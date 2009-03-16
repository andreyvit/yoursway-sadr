package com.yoursway.sadr.python.index;

import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonFileC;

public interface AssignmentsRequestor {
    
    void assignment(PythonConstruct rhs, PythonFileC fileC);
    
}
