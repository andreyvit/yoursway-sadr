package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python_v2.goals.UserClassF;
import com.yoursway.sadr.python_v2.goals.VariableReadF;

public class ClassEffect extends Effect {
    
    private final ClassDeclarationC construct;
    
    public ClassEffect(ClassDeclarationC construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        this.construct = construct;
    }
    
    @Override
    public Frog apply(Frog frog) {
        return frog.replace(new VariableReadF(construct.name()), new UserClassF(construct));
    }
}
