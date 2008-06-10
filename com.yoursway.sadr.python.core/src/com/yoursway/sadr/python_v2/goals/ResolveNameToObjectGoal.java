package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.AssignmentC;
import com.yoursway.sadr.python_v2.constructs.CallC;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodCallC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.ProcedureCallC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
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
        if (this.var == null) {
            throw new NullPointerException("Var is null");
        }
        
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
        PythonConstruct currentConstruct = this.var;
        Scope scope = currentConstruct.parentScope();
        PythonConstruct result = null;
        while (true) {
            PythonConstruct prevConstruct = currentConstruct;
            do {
                result = match(currentConstruct);
                currentConstruct = currentConstruct.getSyntacticallyPreviousConstruct();//TODO goal here
            } while (result == null
                    && !(currentConstruct instanceof Scope && currentConstruct == prevConstruct.parentScope()));
            if (null != result) {
                processResultConstruct(result);
                break;
            } else if (getContext() != null && getContext().contains(this.name)) {
                RuntimeObject argument = getContext().getActualArgument(this.name);
                acceptor().addResult(argument, getContext());
                updateGrade(acceptor(), Grade.DONE);
                break;
            }
            scope = scope.parentScope();
            if (scope == null) {
                RuntimeObject object = Builtins.instance().getAttribute(name);
                if (object != null) {
                    acceptor().addResult(object, getContext());
                }
                //TODO if result is null return IMPOSSIBLE object
                updateGrade(acceptor(), Grade.DONE);
                break;
            }
            currentConstruct = scope.getPostChildren().get(scope.getPostChildren().size() - 1);
        }
    }
    
    private PythonConstruct match(PythonConstruct currentConstruct) {
        PythonConstruct result = null;
        if (currentConstruct instanceof AssignmentC) {
            AssignmentC assignmentC = (AssignmentC) currentConstruct;
            PythonConstruct lhs = assignmentC.lhs();
            if (lhs instanceof VariableReferenceC) {
                VariableReferenceC reference = (VariableReferenceC) lhs;
                if (reference.node().getName().equals(this.name)) {
                    result = assignmentC;
                }
            }
        } else if (currentConstruct instanceof MethodDeclarationC) {
            //FIXME merge with previous if-statement.
            MethodDeclarationC declarationC = (MethodDeclarationC) currentConstruct;
            if (declarationC.node().getName().equals(this.name)) {
                result = declarationC;
            }
        } else if (currentConstruct instanceof ClassDeclarationC) {
            //FIXME merge with previous if-statement.
            ClassDeclarationC declarationC = (ClassDeclarationC) currentConstruct;
            if (declarationC.node().getName().equals(this.name)) {
                result = declarationC;
            }
        } else if (currentConstruct instanceof ImportDeclarationC) {
            ImportDeclarationC moduleImport = (ImportDeclarationC) currentConstruct;
            if (moduleImport.hasImport(this.name)) {
                result = moduleImport;
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
        } else if (result instanceof ImportDeclarationC) {
            ImportDeclarationC moduleImport = (ImportDeclarationC) result;
            schedule(new ResolveModuleImportGoal(moduleImport, this.name, acceptor, getContext()));
        } else if (result instanceof ClassDeclarationC) {
            ClassDeclarationC classDeclarationC = (ClassDeclarationC) result;
            FunctionObject obj = new FunctionObject(classDeclarationC);
            acceptor().addResult(obj, getContext());
            updateGrade(acceptor(), Grade.DONE);
        } else
            throw new IllegalStateException("Illegal construct occured.");
    }
    
    @Override
    public String describe() {
        String scope = (var.innerScope()).toString();
        return super.describe() + "\nfor name " + this.name + " in " + scope;
    }
    
    public void setAcceptor(PythonValueSetAcceptor acceptor) {
        this.acceptor = acceptor;
    }
    
    public PythonValueSetAcceptor acceptor() {
        return acceptor;
    }
}
