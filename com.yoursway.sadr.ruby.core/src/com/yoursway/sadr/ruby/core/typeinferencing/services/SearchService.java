package com.yoursway.sadr.ruby.core.typeinferencing.services;

import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;

public interface SearchService {
    
    FileScope[] searchForEverything();
    
    void findProcedureCalls(String name, CallsRequestor requestor);
    
    void findMethodCalls(String name, CallsRequestor requestor);
    
    void findAssignments(String name, AssignmentsRequestor requestor);
    
}
