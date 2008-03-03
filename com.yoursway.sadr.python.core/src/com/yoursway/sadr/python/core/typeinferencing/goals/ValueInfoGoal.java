package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.ContextSensitiveThing;

public interface ValueInfoGoal extends Goal {
    
    ValueInfo result(ContextSensitiveThing victim);
    
    ValueInfo weakResult();
    
}
