package com.yoursway.sadr.python_v2.goals;

import java.util.LinkedList;
import java.util.List;

import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.ReturnC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.succeeder.Goal;

public class CallReturnValueGoal extends Goal<PythonValueSet> {
    
    private final MethodDeclarationC methodDecl;
    private PythonValueSet acceptor;
    private final Krocodile crocodile;
    
    public CallReturnValueGoal(MethodDeclarationC methodDecl, final Krocodile arguments) {
        this.methodDecl = methodDecl;
        this.crocodile = arguments;
    }
    
    void collectReturns(List<ReturnC> returns, List<PythonConstruct> constructs) {
        for (PythonConstruct construct : constructs) {
            if (construct instanceof ReturnC) {
                returns.add((ReturnC) construct);
            } else if (construct instanceof IfC) {
                IfC ifC = (IfC) construct;
                PythonValueSet choices = ifC.evaluate(crocodile);
                for (PythonObject choice : choices) {
                    List<PythonConstruct> branch = ifC.getBranch(choice);
                    if (branch == null)
                        continue;
                    collectReturns(returns, branch);
                }
            } else {
                collectReturns(returns, construct.getPostChildren());
            }
        }
    }
    
    public PythonValueSet evaluate() {
        List<ReturnC> returns = new LinkedList<ReturnC>();
        collectReturns(returns, methodDecl.getPostChildren());
        acceptor = new PythonValueSet();
        for (ReturnC item : returns) {
            acceptor.addResults(item.evaluate(crocodile));
        }
        return acceptor;
    }
    
    @Override
    public String describe() {
        return super.describe() + "\nfor " + methodDecl.displayName();
    }
}
