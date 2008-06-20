package com.yoursway.sadr.blocks.swamp.old;

import com.yoursway.sadr.blocks.swamp.formulas.Formula;
import com.yoursway.utils.DebugOutputHelper;

public class Branch {
    
    private final Formula target;

    public Branch(Formula target) {
        if (target == null)
            throw new NullPointerException("target is null");
        this.target = target;
    }
    
    @Override
    public String toString() {
        return DebugOutputHelper.reflectionBasedToString(this, target);
    }
    
}
