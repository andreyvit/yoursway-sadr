package com.yoursway.sadr.python.index;

import static java.lang.String.format;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.constructs.MethodDeclarationC;

public class ReturnsIndexQuery implements DtlIndexQuery<ReturnsRequestor> {
    
    private final MethodDeclarationC methodC;
    
    public ReturnsIndexQuery(MethodDeclarationC methodC) {
        if (methodC == null)
            throw new NullPointerException("methodC is null");
        this.methodC = methodC;
    }
    
    public void accept(DtlIndexQueryVisitor visitor, ReturnsRequestor requestor) {
        visitor.acceptReturnsQuery(this, requestor);
    }
    
    public MethodDeclarationC getMethodC() {
        return methodC;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((methodC == null) ? 0 : methodC.hashCode());
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
        ReturnsIndexQuery other = (ReturnsIndexQuery) obj;
        if (methodC == null) {
            if (other.methodC != null)
                return false;
        } else if (!methodC.equals(other.methodC))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return format("Returns(%s)", methodC.name());
    }
    
    public SourceUnit localSourceUnit() {
        return methodC.fileC().sourceUnit();
    }
    
}
