package com.yoursway.sadr.blocks.swamp.old;

import com.yoursway.sadr.blocks.swamp.effects.Effect;

public interface EffectCursor {
    
    Resultstream next();
    
    EffectCursor reverse();
    
    Effect current();
    
}
