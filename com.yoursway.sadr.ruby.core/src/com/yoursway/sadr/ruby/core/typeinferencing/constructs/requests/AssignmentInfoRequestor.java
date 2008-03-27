package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;

public interface AssignmentInfoRequestor {
    
    void accept(AssignmentInfo info);
    
}
