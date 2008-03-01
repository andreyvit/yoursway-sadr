package com.yoursway.sadr.python.core.typeinferencing.services;

import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public interface AssignmentsRequestor {
    
    void assignment(AssignmentInfo info, FileScope fileScope);
    
}
