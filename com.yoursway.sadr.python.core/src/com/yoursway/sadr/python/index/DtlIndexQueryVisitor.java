package com.yoursway.sadr.python.index;

public interface DtlIndexQueryVisitor {
    
    void acceptAssignmentsQuery(AssignmentsIndexQuery assignmentsIndexQuery, AssignmentsRequestor requestor);
    
}
