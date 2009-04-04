package com.yoursway.sadr.python.analysis.index.queries;

import static java.lang.String.format;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.MethodDeclarationC;

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
    
    public MethodArea getArea() {
        return (MethodArea) methodC.getInnerLC().getArea();
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
