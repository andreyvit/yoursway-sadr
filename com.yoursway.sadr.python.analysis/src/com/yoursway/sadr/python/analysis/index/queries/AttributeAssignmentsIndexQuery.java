package com.yoursway.sadr.python.analysis.index.queries;

import static java.lang.String.format;

import com.yoursway.sadr.engine.incremental.SourceUnit;

public class AttributeAssignmentsIndexQuery implements DtlIndexQuery<AttributeAssignmentsRequestor> {
    
    private final String attributeName;
    
    public AttributeAssignmentsIndexQuery(String attributeName) {
        if (attributeName == null)
            throw new NullPointerException("attributeName is null");
        this.attributeName = attributeName;
    }
    
    public void accept(DtlIndexQueryVisitor visitor, AttributeAssignmentsRequestor requestor) {
        visitor.acceptAttributeAssignmentsQuery(this, requestor);
    }
    
    public String getAttributeName() {
        return attributeName;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
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
        AttributeAssignmentsIndexQuery other = (AttributeAssignmentsIndexQuery) obj;
        if (attributeName == null) {
            if (other.attributeName != null)
                return false;
        } else if (!attributeName.equals(other.attributeName))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return format("AttrAssignments(%s)", attributeName);
    }
    
    public SourceUnit localSourceUnit() {
        return null;
    }
    
}
