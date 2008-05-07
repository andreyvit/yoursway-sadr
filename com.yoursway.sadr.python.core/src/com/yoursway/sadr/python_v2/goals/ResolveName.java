package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveName extends Goal {
    
    private final VariableReferenceC variableReference;
    private final ResolvedNameAcceptor acceptor;
    
    public ResolveName(VariableReferenceC variableReference, ResolvedNameAcceptor acceptor) {
        this.variableReference = variableReference;
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        AssignmentC resultAssignmentC = null;
        Scope scope = variableReference.parentScope();
        //FIXME change getEnclosedConstructs to iterator allowing parallel execution
        resultAssignmentC = findInScope(scope);
        scope = scope.parentScope();
        while (resultAssignmentC == null && scope != null) {
            resultAssignmentC = findInScope(scope);
            scope = scope.parentScope();
        }
        
        acceptor.addResult(resultAssignmentC);
        checkpoint(acceptor, Grade.DONE);
    }
    
    private AssignmentC findInScope(Scope scope) {
        List<PythonConstruct> children = scope.getEnclosedconstructs();
        AssignmentC resultAssignmentC = null;
        for (PythonConstruct construct : children) {
            if (variableReference == construct) {
                break;
            }
            if (construct instanceof AssignmentC) {
                AssignmentC assignmentC = (AssignmentC) construct;
                PythonConstruct lhs = assignmentC.lhs();
                if (lhs instanceof VariableReferenceC) {
                    VariableReferenceC reference = (VariableReferenceC) lhs;
                    if (reference.node().getName().equals(variableReference.node().getName())) {
                        resultAssignmentC = assignmentC;
                    }
                }
            }
        }
        return resultAssignmentC;
    }
    
    public CheckpointToken flush() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
