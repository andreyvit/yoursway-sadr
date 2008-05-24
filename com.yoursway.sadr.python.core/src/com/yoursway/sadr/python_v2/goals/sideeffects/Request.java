package com.yoursway.sadr.python_v2.goals.sideeffects;

import com.yoursway.sadr.python_v2.constructs.Effect;
import com.yoursway.sadr.python_v2.constructs.Frog;

public interface Request {
    
    Frog apply(Frog frog, Effect effect);
    
    Request clone(Effect effect);
    
}
