package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Sinner;

public interface ValueInfoGoal extends Goal {
    
    ValueInfo result(Sinner victim);
    
    ValueInfo resultWithoutKarma();
    
}