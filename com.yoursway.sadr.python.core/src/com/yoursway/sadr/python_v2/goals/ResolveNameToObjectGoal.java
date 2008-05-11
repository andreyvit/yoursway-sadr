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
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;

public class ResolveNameToObjectGoal extends ContextSensitiveGoal {
    
    private final PythonValueSetAcceptor acceptor;
    private final PythonConstructImpl variableReference;
    private final String name;
    
    public ResolveNameToObjectGoal(VariableReferenceC var, PythonValueSetAcceptor acceptor, Context context) {
        super(context);
        variableReference = var;
        this.name = var.node().getName();
        this.acceptor = acceptor;
    }
    
    public ResolveNameToObjectGoal(ProcedureCallC var, PythonValueSetAcceptor acceptor, Context context) {
        super(context);
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
        if (null == result && variableReference instanceof VariableReferenceC) {
            String name = ((VariableReferenceC) variableReference).node().getName();
            if (getContext() != null && getContext().contains(name)) {
                acceptor.addResult(getContext().getActualArguement(name), getContext());
                checkpoint(acceptor, Grade.DONE);
            }
        }
        while (result == null && scope != null) {
            result = findInScope(scope);
            scope = scope.parentScope();
        }
        processResultConstruct(result);
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
            if (construct instanceof MethodDeclarationC) {//FIXME merge with previous if-statement.
                MethodDeclarationC declarationC = (MethodDeclarationC) construct;
                if (declarationC.node().getName().equals(this.name)) {
                    result = declarationC;
                }
            }
        }
        return result;
    }
    
    public void processResultConstruct(PythonConstruct result) {
        //TODO if result is null return IMPOSSIBLE object
        if (result instanceof AssignmentC) {
            AssignmentC assignmentC = (AssignmentC) result;
            PythonConstruct subexpr = assignmentC.rhs();
            schedule(new ExpressionValueGoal(subexpr, getContext(), acceptor));
        } else if (result instanceof MethodDeclarationC) {
            MethodDeclarationC methodDeclarationC = (MethodDeclarationC) result;
            FunctionObject obj = new FunctionObject(methodDeclarationC);
            acceptor.addResult(obj, getContext());
            checkpoint(acceptor, Grade.DONE);
        }
    }
    
}
