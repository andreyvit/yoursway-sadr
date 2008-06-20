package com.yoursway.sadr.blocks.swamp.effects;

import com.yoursway.utils.DebugOutputHelper;

public class AbstractEffect implements Effect {
    
    @Override
    public String toString() {
        return DebugOutputHelper.reflectionBasedToString(this);
    }
   
}
