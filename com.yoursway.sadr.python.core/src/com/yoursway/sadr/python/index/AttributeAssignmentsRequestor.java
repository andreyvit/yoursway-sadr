package com.yoursway.sadr.python.index;

import com.yoursway.sadr.python.constructs.PythonFileC;
import com.yoursway.sadr.python.model.AssignmentInfo;

public interface AttributeAssignmentsRequestor {
    
    void assignment(AssignmentInfo info, PythonFileC fileC);
    
}
