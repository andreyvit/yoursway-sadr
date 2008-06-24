package com.yoursway.sadr.python_v2.goals.sideeffects;

import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.Frog;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;

public class UserClassF extends Frog {
    
    private final ClassDeclarationC construct;
    
    public UserClassF(ClassDeclarationC construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        this.construct = construct;
    }
    
    public ClassDeclarationC getConstruct() {
        return construct;
    }
    
    @Override
    public Frog replace(Frog lhs, Frog rhs) {
        if (lhs.equals(this))
            return rhs;
        return this;
    }
    
    public PythonClassType createType() {
        return new PythonClassType(construct);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + construct + ")";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((construct == null) ? 0 : construct.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserClassF other = (UserClassF) obj;
        if (construct == null) {
            if (other.construct != null)
                return false;
        } else if (!construct.equals(other.construct))
            return false;
        return true;
    }
    
}
