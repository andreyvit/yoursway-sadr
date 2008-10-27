package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.AssignmentC;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.acceptors.PvsaDelegate;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;

public class ResolveObjectGoal extends ContextSensitiveGoal {
    
    private final PythonConstruct currentConstruct;
    private final PythonValueSetAcceptor acceptor;
    
    public ResolveObjectGoal(Context context, PythonConstruct construct, PythonValueSetAcceptor acceptor) {
        super(context);
        this.currentConstruct = construct;
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        if (currentConstruct instanceof AssignmentC) {
            AssignmentC assignmentC = (AssignmentC) currentConstruct;
            onAssC(assignmentC);
        } else if (currentConstruct instanceof MethodDeclarationC) {
            //FIXME merge with previous if-statement.
            MethodDeclarationC declarationC = (MethodDeclarationC) currentConstruct;
            methodDeclC(declarationC);
        } else if (currentConstruct instanceof ClassDeclarationC) {
            //FIXME merge with previous if-statement.
            ClassDeclarationC declarationC = (ClassDeclarationC) currentConstruct;
            if (declarationC.node().getName().equals(this.name)) {
                FunctionObject obj = new FunctionObject(declarationC);
                PythonValueSetAcceptor resultAcceptor = new PvsaDelegate(incSync, getContext());
                resultAcceptor.addResult(obj, getContext());
                updateGrade(resultAcceptor, Grade.DONE);
                return true;
            }
        } else if (currentConstruct instanceof ImportDeclarationC) {
            ImportDeclarationC moduleImport = (ImportDeclarationC) currentConstruct;
            if (moduleImport.match(this.name)) {
                schedule(new ResolveModuleImportGoal(moduleImport, this.name, new PvsaDelegate(incSync,
                        getContext()), getContext()));
                return true;
            }
        } else if (currentConstruct instanceof IfC) {
            resolveIf();
            
        }
        return false;
    }
    
    private void onAssC(AssignmentC assignmentC) {
        PythonConstruct lhs = assignmentC.lhs();
        if (lhs instanceof VariableReferenceC) {
            VariableReferenceC reference = (VariableReferenceC) lhs;
            PythonConstruct subexpr = assignmentC.rhs();
            schedule(subexpr.evaluate(getContext(), acceptor));
        }
    }
    
    private void methodDeclC(MethodDeclarationC declarationC) {
        FunctionObject obj = new FunctionObject(declarationC);
        PythonValueSetAcceptor resultAcceptor = new PvsaDelegate(incSync, getContext());
        resultAcceptor.addResult(obj, getContext());
        updateGrade(resultAcceptor, Grade.DONE);
        return true;
    }
    
}
