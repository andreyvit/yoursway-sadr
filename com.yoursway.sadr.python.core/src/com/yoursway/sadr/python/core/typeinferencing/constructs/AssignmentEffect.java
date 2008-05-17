package com.yoursway.sadr.python.core.typeinferencing.constructs;

public class AssignmentEffect extends Effect {
    
    private final Frog lhs;
    private final Frog rhs;
    
    public AssignmentEffect(Frog lhs, Frog rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    @Override
    public Frog apply(Frog frog) {
        return frog.replace(lhs, rhs);
    }
    
}
