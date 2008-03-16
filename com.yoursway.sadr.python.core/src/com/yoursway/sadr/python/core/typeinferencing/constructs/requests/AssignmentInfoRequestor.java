package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;

public interface AssignmentInfoRequestor {
    
    void accept(AssignmentInfo info);
    
}
