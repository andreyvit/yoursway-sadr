package com.yoursway.sadr.python.index;

public interface DtlIndexQueryVisitor {
    
    void acceptAssignmentsQuery(AssignmentsIndexQuery assignmentsIndexQuery, AssignmentsRequestor requestor);
    
    void acceptReturnsQuery(ReturnsIndexQuery query, ReturnsRequestor requestor);
    
    void acceptAttributeAssignmentsQuery(AttributeAssignmentsIndexQuery query,
            AttributeAssignmentsRequestor requestor);
    
}
