/**
 * 
 */
package com.yoursway.sadr.python.core.tests.typeinferencing;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.ResolvedNameAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public class ResolvedNameAcceptorImpl implements ResolvedNameAcceptor {
    private PythonConstruct result;
    
    public void addResult(PythonConstruct assignment) {
        result = assignment;
    }
    
    public PythonConstruct getResult() {
        return result;
    }
    
    public <T> void checkpoint(IGrade<T> grade) {
        
    }
}