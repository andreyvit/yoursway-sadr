package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;

public interface AssignmentInfoProvider {
    
    AssignmentInfo[] assigned();
    
}