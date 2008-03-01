package com.yoursway.sadr.python.core.typeinferencing.services;

import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public interface SearchService {
    
    FileScope[] searchForEverything();
    
    void findProcedureCalls(String name, CallsRequestor requestor);
    
    void findMethodCalls(String name, CallsRequestor requestor);
    
    void findAssignments(String name, AssignmentsRequestor requestor);
    
}
