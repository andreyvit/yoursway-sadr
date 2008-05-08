package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ProcedureCallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveNameGoal extends Goal {
    
    private final ResolvedNameAcceptor acceptor;
    private final PythonConstructImpl variableReference;
    private final String name;
    
    public ResolveNameGoal(VariableReferenceC var, ResolvedNameAcceptor acceptor) {
        variableReference = var;
        this.name = var.node().getName();
        this.acceptor = acceptor;
    }
    
    public ResolveNameGoal(ProcedureCallC var, ResolvedNameAcceptor acceptor) {
        variableReference = var;
        this.name = var.node().getProcedureName();
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        PythonConstruct result = null;
        Scope scope = variableReference.parentScope();
        //FIXME change getEnclosedConstructs to iterator allowing parallel execution
        result = findInScope(scope);
        scope = scope.parentScope();
        while (result == null && scope != null) {
            result = findInScope(scope);
            scope = scope.parentScope();
        }
        if (result != null) {
            acceptor.addResult(result);
        }
        checkpoint(acceptor, Grade.DONE);
    }
    
    private PythonConstruct findInScope(Scope scope) {
        List<PythonConstruct> children = scope.getEnclosedconstructs();
        PythonConstruct result = null;
        for (PythonConstruct construct : children) {
            if (variableReference == construct) {
                break;
            }
            if (construct instanceof AssignmentC) {
                AssignmentC assignmentC = (AssignmentC) construct;
                PythonConstruct lhs = assignmentC.lhs();
                if (lhs instanceof VariableReferenceC) {
                    VariableReferenceC reference = (VariableReferenceC) lhs;
                    if (reference.node().getName().equals(this.name)) {
                        result = assignmentC;
                    }
                }
            }
            if (construct instanceof MethodDeclarationC) {
                MethodDeclarationC declarationC = (MethodDeclarationC) construct;
                if (declarationC.node().getName().equals(this.name)) {
                    result = declarationC;
                }
            }
        }
        return result;
    }
    
    @Override
    public CheckpointToken flush() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
