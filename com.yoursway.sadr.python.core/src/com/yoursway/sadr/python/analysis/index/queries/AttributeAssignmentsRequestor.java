package com.yoursway.sadr.python.analysis.index.queries;

import com.yoursway.sadr.python.analysis.index.data.AssignmentInfo;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;

public interface AttributeAssignmentsRequestor {
    
    void assignment(AssignmentInfo info, PythonFileC fileC);
    
}
