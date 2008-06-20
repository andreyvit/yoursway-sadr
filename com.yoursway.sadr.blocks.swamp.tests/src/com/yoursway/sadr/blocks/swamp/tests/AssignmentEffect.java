/**
 * 
 */
package com.yoursway.sadr.blocks.swamp.tests;

import com.yoursway.sadr.blocks.swamp.effects.AliasingEffect;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;

public class AssignmentEffect implements AliasingEffect {
    
    private final Formula lhs;
    private final Formula rhs;
    
    public AssignmentEffect(Formula lhs, Formula rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    public Formula lhs() {
        return lhs;
    }
    
    public Formula rhs() {
        return rhs;
    }
    
}