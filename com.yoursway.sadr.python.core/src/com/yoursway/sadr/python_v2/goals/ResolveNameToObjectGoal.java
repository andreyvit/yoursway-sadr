package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.AssignmentC;
import com.yoursway.sadr.python_v2.constructs.CallC;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodCallC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.ProcedureCallC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CreateInstanceGoal;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;

public class ResolveNameToObjectGoal extends ContextSensitiveGoal {
    
    private PythonValueSetAcceptor acceptor;
    private final PythonConstruct var;
    private final String name;
    
    protected ResolveNameToObjectGoal(PythonConstruct start, String name, PythonValueSetAcceptor acceptor,
            Context context) {
        super(context);
        this.name = name;
        this.setAcceptor(acceptor);
        this.var = start;
    }
    
    public ResolveNameToObjectGoal(VariableReferenceC var, PythonValueSetAcceptor acceptor, Context context) {
        super(context);
        this.var = var;
        this.name = var.node().getName();
        this.setAcceptor(acceptor);
    }
    
    public ResolveNameToObjectGoal(CallC var, PythonValueSetAcceptor acceptor, Context context) {
        super(context);
        this.var = var;
        if (var instanceof MethodCallC) {
            this.name = var.node().getName();
        } else if (var instanceof ProcedureCallC) {
            this.name = var.node().getName();
        } else {
            throw new IllegalStateException("should never reach this place");
        }
        this.setAcceptor(acceptor);
    }
    
    public void preRun() {
        PythonConstruct result = null;
        Scope scope = this.var.parentScope();
        //FIXME change getEnclosedConstructs to iterator allowing parallel execution
        result = findInScope(scope);
        scope = scope.parentScope();
        if (null == result) {
            if (getContext() != null && getContext().contains(this.name)) {
                acceptor().addResult(getContext().getActualArgument(this.name), getContext());
                updateGrade(acceptor(), Grade.DONE);
                return;
            }
            if (name.equals("self") && this.var.parentScope() instanceof MethodDeclarationC && scope != null
                    && scope instanceof ClassDeclarationC) {
                ClassDeclarationC classC = (ClassDeclarationC) scope;
                //FIXME: HACK for self handling
                schedule(new CreateInstanceGoal(classC, new PythonArguments(), null, acceptor));
                return;
            }
        }
        while (result == null && scope != null) {
            result = findInScope(scope);
            scope = scope.parentScope();
        }
        processResultConstruct(result);
    }
    
    protected PythonConstruct findInScope(Scope scope) {
        List<PythonConstruct> children = scope.getEnclosedConstructs2();
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
            } else if (construct instanceof MethodDeclarationC) {
                //FIXME merge with previous if-statement.
                MethodDeclarationC declarationC = (MethodDeclarationC) construct;
                if (declarationC.node().getName().equals(this.name)) {
                    result = declarationC;
                }
            } else if (construct instanceof ClassDeclarationC) {
                //FIXME merge with previous if-statement.
                ClassDeclarationC declarationC = (ClassDeclarationC) construct;
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
            schedule(subexpr.evaluate(getContext(), acceptor()));
        } else if (result instanceof MethodDeclarationC) {
            MethodDeclarationC methodDeclarationC = (MethodDeclarationC) result;
            FunctionObject obj = new FunctionObject(methodDeclarationC);
            
            acceptor().addResult(obj, getContext());
            updateGrade(acceptor(), Grade.DONE);
        } else if (result instanceof ClassDeclarationC) {
            ClassDeclarationC classDeclarationC = (ClassDeclarationC) result;
            FunctionObject obj = new FunctionObject(classDeclarationC);
            acceptor().addResult(obj, getContext());
            updateGrade(acceptor(), Grade.DONE);
        } else if (result == null) {
            RuntimeObject object = Builtins.instance().getAttribute(name);
            if (object != null) {
                acceptor().addResult(object, getContext());
            }
            //TODO if result is null return IMPOSSIBLE object
            updateGrade(acceptor(), Grade.DONE);
        } else {
            throw new IllegalStateException("should never reach this place");
        }
    }
    
    @Override
    public String describe() {
        String scope = (var.parentScope()).toString();
        return super.describe() + "\nfor name " + this.name + " in " + scope;
    }
    
    public void setAcceptor(PythonValueSetAcceptor acceptor) {
        this.acceptor = acceptor;
    }
    
    public PythonValueSetAcceptor acceptor() {
        return acceptor;
    }
}
