package com.yoursway.sadr.python.analysis.index.queries;

import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;

public interface AssignmentsRequestor {
    
    void assignment(Bnode rhs, PythonFileC fileC);
    
}
