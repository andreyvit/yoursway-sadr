package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python_v2.goals.internal.PythonUserClassType;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;

public class UserClassF extends Frog {
    
    private final ClassDeclarationC construct;
    
    public UserClassF(ClassDeclarationC construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        this.construct = construct;
    }
    
    @Override
    public Frog replace(Frog lhs, Frog rhs) {
        if (lhs.equals(this))
            return rhs;
        return this;
    }
    
    public PythonClassType createType() {
        return new PythonUserClassType(construct);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + construct + ")";
    }
}
