/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGrade;

abstract class ResolvedNameDelegatingAcceptor implements ResolvedNameAcceptor {
    /**
     * 
     */
    public ResolvedNameDelegatingAcceptor() {
    }
    
    public void addResult(PythonConstruct result) {
        if (result instanceof AssignmentC) {
            AssignmentC assignmentC = (AssignmentC) result;
            PythonConstruct subexpr = assignmentC.rhs();
            onAssignment(subexpr);
        } else if (result instanceof MethodDeclarationC) {
            MethodDeclarationC methodDeclarationC = (MethodDeclarationC) result;
            FunctionObject obj = new FunctionObject(methodDeclarationC);
            onMethodDeclaration(obj);
        }
    }
    
    abstract protected void onMethodDeclaration(FunctionObject obj);
    
    abstract protected void onAssignment(PythonConstruct subexpr);
    
    @SuppressWarnings("unchecked")
    public <T> void checkpoint(IGrade<T> grade) {
        //do nothing
    }
}