package com.yoursway.sadr.python.analysis.index.queries;

import static java.lang.String.format;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

public class PassedArgumentsIndexQuery implements DtlIndexQuery<PassedArgumentsRequestor> {
    
    private final Unode unode;
    
    public PassedArgumentsIndexQuery(Unode unode) {
        if (unode == null)
            throw new NullPointerException("unode is null");
        this.unode = unode;
    }
    
    public void accept(DtlIndexQueryVisitor visitor, PassedArgumentsRequestor requestor) {
        visitor.acceptPassedArgumentsQuery(this, requestor);
    }
    
    public Unode getUnode() {
        return unode;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((unode == null) ? 0 : unode.hashCode());
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
        PassedArgumentsIndexQuery other = (PassedArgumentsIndexQuery) obj;
        if (unode == null) {
            if (other.unode != null)
                return false;
        } else if (!unode.equals(other.unode))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return format("PassedArguments(%s)", unode.toString());
    }
    
    public SourceUnit localSourceUnit() {
        return null;
    }
    
}
