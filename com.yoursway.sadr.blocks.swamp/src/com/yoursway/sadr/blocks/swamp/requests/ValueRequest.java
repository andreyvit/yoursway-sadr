package com.yoursway.sadr.blocks.swamp.requests;

import com.yoursway.sadr.blocks.swamp.effects.Effect;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;

public class ValueRequest implements Request {
    
    public Formula apply(Formula formula, Effect effect) {
        Formula result = effect.apply(formula);
        if (!result.equals(formula))
            System.out.println("FROG:  " + formula + " => " + result);
        else
            System.out.println("FROG:  " + formula);
        RuntimeObject value = result.compactValue();
        if (value == null)
            return result;
        else {
            System.out.println("Result found: " + value);
//            acceptor.addResult(value, EMPTY_CONTEXT);
            return null;
        }
    }
    
    public Request clone(Effect effect) {
        return this;
    }
    
}
