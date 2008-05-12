package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodCallC;
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
    private final PythonConstructImpl<?> var;
    private final String name;
    
    public ResolveNameToObjectGoal(VariableReferenceC var, PythonValueSetAcceptor acceptor, Context context) {
        super(context);
        this.var = var;
        this.name = var.node().getName();
        this.acceptor = acceptor;
    }
    
    public ResolveNameToObjectGoal(CallC var, PythonValueSetAcceptor acceptor, Context context) {
        super(context);
        this.var = var;
        if (var instanceof MethodCallC) {
            this.name = var.node().getMethodName();
        } else if (var instanceof ProcedureCallC) {
            this.name = var.node().getProcedureName();
        } else {
            throw new IllegalStateException("should never reach this place");
        }
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        PythonConstruct result = null;
        Scope scope = this.var.parentScope();
        //FIXME change getEnclosedConstructs to iterator allowing parallel execution
        result = findInScope(scope);
        scope = scope.parentScope();
        if (null == result) {
            if (getContext() != null && getContext().contains(this.name)) {
                acceptor.addResult(getContext().getActualArgument(this.name), getContext());
                updateGrade(acceptor, Grade.DONE);
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
            if (this.var == construct) {
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
        if (result instanceof AssignmentC) {
            AssignmentC assignmentC = (AssignmentC) result;
            PythonConstruct subexpr = assignmentC.rhs();
            schedule(new ExpressionValueGoal(subexpr, getContext(), acceptor));
        } else if (result instanceof MethodDeclarationC) {
            MethodDeclarationC methodDeclarationC = (MethodDeclarationC) result;
            FunctionObject obj = new FunctionObject(methodDeclarationC);
            acceptor.addResult(obj, getContext());
            updateGrade(acceptor, Grade.DONE);
        } else if(result == null){
          //TODO if result is null return IMPOSSIBLE object
        } else {
            throw new IllegalStateException("should never reach this place");
        }
    }
    
    @Override
    public String describe() {
        String basic = super.describe();
        String scope = (var.parentScope()).toString();
        return basic + "\nfor name " + this.name + " in " + scope;
    }
}
