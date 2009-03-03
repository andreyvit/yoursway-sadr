package com.yoursway.sadr.python_v2.goals;

import java.util.LinkedList;
import java.util.List;

import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.ReturnC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.succeeder.Goal;

public class CallReturnValueGoal extends Goal<PythonValueSet> {
    
    private final MethodDeclarationC methodDecl;
    private PythonValueSet acceptor;
    private final Krocodile crocodile;
    
    public CallReturnValueGoal(MethodDeclarationC methodDecl, final Krocodile arguments) {
        this.methodDecl = methodDecl;
        this.crocodile = arguments;
    }
    
    public PythonValueSet evaluate() {
        acceptor = new PythonValueSet();
        List<PythonConstruct> enclosedconstructs = methodDecl.getEnclosedConstructs();
        List<ReturnC> returns = new LinkedList<ReturnC>();
        for (PythonConstruct construct : enclosedconstructs) {
            if (construct instanceof ReturnC) {
                ReturnC returnC = (ReturnC) construct;
                returns.add(returnC);
            }
        }
        for (ReturnC item : returns) {
            acceptor.addResults(item.getReturnedConstruct().evaluate(crocodile));
        }
        return acceptor;
    }
    
    @Override
    public String describe() {
        return super.describe() + "\nfor " + methodDecl.displayName();
    }
}
