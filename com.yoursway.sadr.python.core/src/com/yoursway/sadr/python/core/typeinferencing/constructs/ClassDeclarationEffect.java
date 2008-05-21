package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python_v2.goals.sideeffects.UserClassF;
import com.yoursway.sadr.python_v2.goals.sideeffects.VariableReadF;

public class ClassDeclarationEffect extends Effect {
    
    private final ClassDeclarationC construct;
    
    public ClassDeclarationEffect(ClassDeclarationC construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        this.construct = construct;
    }
    
    @Override
    public Frog apply(Frog frog) {
        return frog.replace(new VariableReadF(construct.name()), new UserClassF(construct));
    }
}
