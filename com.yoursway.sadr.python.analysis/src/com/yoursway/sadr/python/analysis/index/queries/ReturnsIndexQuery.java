package com.yoursway.sadr.python.analysis.index.queries;

import static java.lang.String.format;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;

public class ReturnsIndexQuery implements DtlIndexQuery<ReturnsRequestor> {
    
    private final MethodArea area;
    private final SourceUnit sourceUnit;
    
    public ReturnsIndexQuery(MethodArea area, SourceUnit sourceUnit) {
        if (area == null)
            throw new NullPointerException("area is null");
        if (sourceUnit == null)
            throw new NullPointerException("sourceUnit is null");
        this.area = area;
        this.sourceUnit = sourceUnit;
    }
    
    public void accept(DtlIndexQueryVisitor visitor, ReturnsRequestor requestor) {
        visitor.acceptReturnsQuery(this, requestor);
    }
    
    public MethodArea getArea() {
        return area;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((area == null) ? 0 : area.hashCode());
        result = prime * result + ((sourceUnit == null) ? 0 : sourceUnit.hashCode());
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
        if (area == null) {
            if (other.area != null)
                return false;
        } else if (!area.equals(other.area))
            return false;
        if (sourceUnit == null) {
            if (other.sourceUnit != null)
                return false;
        } else if (!sourceUnit.equals(other.sourceUnit))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return format("Returns(%s)", area);
    }
    
    public SourceUnit localSourceUnit() {
        return sourceUnit;
    }
    
}
