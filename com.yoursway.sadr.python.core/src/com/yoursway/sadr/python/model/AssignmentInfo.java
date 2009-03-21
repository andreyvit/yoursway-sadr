package com.yoursway.sadr.python.model;

import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.index.unodes.Unode;

public class AssignmentInfo {
    
    private final PythonConstruct rhs;
    private final Unode receiver;
    
    public AssignmentInfo(Unode receiver, PythonConstruct rhs) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        this.receiver = receiver;
        this.rhs = rhs;
    }
    
    public Unode getReceiver() {
        return receiver;
    }
    
    public PythonConstruct getRhs() {
        return rhs;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
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
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        if (rhs == null) {
            if (other.rhs != null)
                return false;
        } else if (!rhs.equals(other.rhs))
            return false;
        return true;
    }
    
}
