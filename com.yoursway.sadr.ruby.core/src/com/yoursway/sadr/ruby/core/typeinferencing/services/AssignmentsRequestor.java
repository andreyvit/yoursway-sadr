package com.yoursway.sadr.ruby.core.typeinferencing.services;

import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;

public interface AssignmentsRequestor {
    
    void assignment(AssignmentInfo info, FileScope fileScope);
    
}
