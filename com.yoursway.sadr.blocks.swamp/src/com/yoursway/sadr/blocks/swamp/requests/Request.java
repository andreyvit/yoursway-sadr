package com.yoursway.sadr.blocks.swamp.requests;

import com.yoursway.sadr.blocks.swamp.effects.Effect;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;

public interface Request {
    
    Formula apply(Formula frog, Effect effect);
    
    Request clone(Effect effect);
    
}
