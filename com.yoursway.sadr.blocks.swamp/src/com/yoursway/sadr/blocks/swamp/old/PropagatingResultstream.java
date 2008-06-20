package com.yoursway.sadr.blocks.swamp.old;

import com.yoursway.sadr.blocks.swamp.Swamp;
import com.yoursway.sadr.blocks.swamp.effects.Effect;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;
import com.yoursway.sadr.blocks.swamp.requests.Request;

public class PropagatingResultstream implements Resultstream {

    private final Swamp swamp;
    private final EffectCursor cursor;
    private final Formula formula;
    private final Request request;

    public PropagatingResultstream(Swamp swamp, Formula formula, Request request, EffectCursor cursor) {
        if (swamp == null)
            throw new NullPointerException("swamp is null");
        if (formula == null)
            throw new NullPointerException("formula is null");
        if (request == null)
            throw new NullPointerException("request is null");
        if (cursor == null)
            throw new NullPointerException("cursor is null");
        this.swamp = swamp;
        this.formula = formula;
        this.request = request;
        this.cursor = cursor;
    }
    
    public void run() {
        Effect effect = cursor.current();
        Formula formula = this.formula;
        formula = request.apply(formula, effect);
        Resultstream next = cursor.next();
        new PropagatingResultstream(swamp, formula, request, null);
    }
    
}
