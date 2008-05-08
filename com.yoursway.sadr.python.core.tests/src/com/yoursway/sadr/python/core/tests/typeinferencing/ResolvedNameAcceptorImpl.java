/**
 * 
 */
package com.yoursway.sadr.python.core.tests.typeinferencing;

import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentC;
import com.yoursway.sadr.python_v2.goals.ResolvedNameAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public class ResolvedNameAcceptorImpl implements ResolvedNameAcceptor {
    private AssignmentC resultAssignmentC;
    
    public void addResult(AssignmentC assignment) {
        resultAssignmentC = assignment;
    }
    
    public AssignmentC getResultAssignmentC() {
        return resultAssignmentC;
    }
    
    public <T> void checkpoint(IGrade<T> grade) {
        
    }
}