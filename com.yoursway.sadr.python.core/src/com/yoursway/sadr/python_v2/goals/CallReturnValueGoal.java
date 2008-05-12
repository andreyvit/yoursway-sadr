package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructVisitor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ReturnC;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGrade;

public class CallReturnValueGoal extends ContextSensitiveGoal {
    
    private final MethodDeclarationC methodDecl;
    private final PythonValueSetAcceptor acceptor;
    
    public CallReturnValueGoal(MethodDeclarationC methodDecl, Context context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.methodDecl = methodDecl;
        this.acceptor = acceptor;
    }
    
    public CallReturnValueGoal(MethodDeclarationC decl, final Context arguments, final Context parentContext,
            final PythonValueSetAcceptor parentAcceptor) {
        super(arguments);
        this.methodDecl = decl;
        this.acceptor = new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                RuntimeObject result = getResultByContext(arguments);
                parentAcceptor.addResult(result, parentContext);
                updateGrade(parentAcceptor, grade);
            }
        };
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
                schedule(ret.getReturnedConstruct().evaluate(getContext(), acceptor));
                return false;
            }
        });
    }
    
    @Override
    public String describe() {
        String basic = super.describe();
        return basic + "\nfor " + methodDecl.displayName();
    }
}
