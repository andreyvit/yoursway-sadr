package com.yoursway.sadr.blocks.swamp.old;

import com.yoursway.sadr.blocks.swamp.Swamp;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;
import com.yoursway.sadr.blocks.swamp.requests.Request;

public class Session {
    
    private Swamp swamp = new Swamp();
    
    public Resultstream calculate(Formula formula, Request request, EffectCursor cursor) {
        return new PropagatingResultstream(swamp, formula, request, cursor);
    }
    
}
