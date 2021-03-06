package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.Goal;

public interface ValueInfoGoal extends Goal {
    
    ValueInfo result(ContextSensitiveThing thing);
    
    ValueInfo roughResult();
    
}
