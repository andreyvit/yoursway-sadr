package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;

public class UserMethodF extends Frog {
    
    private final MethodDeclarationC construct;
    
    public UserMethodF(MethodDeclarationC construct) {
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
        final UserMethodF other = (UserMethodF) obj;
        if (construct == null) {
            if (other.construct != null)
                return false;
        } else if (!construct.equals(other.construct))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + construct + ")";
    }
    
    public MethodDeclarationC getConstruct() {
        return construct;
    }
}
