package com.yoursway.sadr.python.analysis.index.queries;

import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;

public interface AssignmentsRequestor {
    
    void assignment(PythonConstruct rhs, PythonFileC fileC);
    
}
