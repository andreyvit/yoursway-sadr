package com.yoursway.sadr.blocks.swamp.old;

import com.yoursway.sadr.blocks.swamp.formulas.Formula;
import com.yoursway.sadr.blocks.swamp.requests.Request;
import com.yoursway.sadr.succeeder.Goal;

public class SquelchingGoal extends Goal {
    
    private final Formula formula;
    private final Request request;

    public SquelchingGoal(Session session, Formula formula, Request request) {
        if (formula == null)
            throw new NullPointerException("formula is null");
        if (request == null)
            throw new NullPointerException("request is null");
        this.formula = formula;
        this.request = request;
    }

    @Override
    public void preRun() {
        
    }
    
}
