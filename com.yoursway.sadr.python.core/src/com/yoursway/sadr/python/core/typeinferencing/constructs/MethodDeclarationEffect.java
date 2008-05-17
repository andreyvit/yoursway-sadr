package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python_v2.goals.UserMethodF;
import com.yoursway.sadr.python_v2.goals.VariableReadF;

public class MethodDeclarationEffect extends Effect {
    
    private final MethodDeclarationC construct;
    
    public MethodDeclarationEffect(MethodDeclarationC construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        this.construct = construct;
    }
    
    @Override
    public Frog apply(Frog frog) {
        return frog.replace(new VariableReadF(construct.node().getName()), new UserMethodF(construct));
    }
    
}
