package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructVisitor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ReturnC;
import com.yoursway.sadr.python_v2.model.Context;

public class CallReturnValueGoal extends ContextSensitiveGoal {
    
    private final MethodDeclarationC methodDecl;
    private final PythonValueSetAcceptor acceptor;
    
    public CallReturnValueGoal(MethodDeclarationC methodDecl, Context context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.methodDecl = methodDecl;
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        if (getContext() == null) {
            //TODO schedule search and new goals for each result. 
        }
        evaluateInContext(getContext());
    }
    
    private void evaluateInContext(Context context) {
        methodDecl.traverse(new PythonConstructVisitor() {
            @Override
            public boolean visit(ReturnC construct) {
                ReturnC ret = construct;
                schedule(new ExpressionValueGoal(ret.getReturnedConstruct(), getContext(), acceptor));
                return false;
            }
        });
    }
}
