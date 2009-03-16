package com.yoursway.sadr.python.model;

import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.index.unodes.Unode;

public class AssignmentInfo {
    
    private final Unode lhs;
    private final PythonConstruct rhs;
    
    public AssignmentInfo(Unode lhs, PythonConstruct rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
        result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
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
        AssignmentInfo other = (AssignmentInfo) obj;
        if (lhs == null) {
            if (other.lhs != null)
                return false;
        } else if (!lhs.equals(other.lhs))
            return false;
        if (rhs == null) {
            if (other.rhs != null)
                return false;
        } else if (!rhs.equals(other.rhs))
            return false;
        return true;
    }
    
}
