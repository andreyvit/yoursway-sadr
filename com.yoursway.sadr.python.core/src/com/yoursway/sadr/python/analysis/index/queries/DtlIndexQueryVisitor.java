package com.yoursway.sadr.python.analysis.index.queries;

public interface DtlIndexQueryVisitor {
    
    void acceptAssignmentsQuery(AssignmentsIndexQuery assignmentsIndexQuery, AssignmentsRequestor requestor);
    
    void acceptReturnsQuery(ReturnsIndexQuery query, ReturnsRequestor requestor);
    
    void acceptAttributeAssignmentsQuery(AttributeAssignmentsIndexQuery query,
            AttributeAssignmentsRequestor requestor);
    
    void acceptPassedArgumentsQuery(PassedArgumentsIndexQuery query, PassedArgumentsRequestor requestor);
    
}
