package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.python_v2.goals.sideeffects.UserMethodF;
import com.yoursway.sadr.python_v2.goals.sideeffects.VariableReadF;

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
