package com.yoursway.sadr.python_v2.goals;

import java.util.LinkedList;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.ReturnC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
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
        this.acceptor = new PythonValueSetAcceptor(arguments) {
            
            @Override
            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                if (result == null) {
                    result = Builtins.getNone();
                }
                parentAcceptor.addResult(result, parentContext);
                updateGrade(parentAcceptor, grade);
            }
        };
    }
    
    public void preRun() {
        List<PythonConstruct> enclosedconstructs = methodDecl.getEnclosedConstructs();
        List<ReturnC> returns = new LinkedList<ReturnC>();
        for (PythonConstruct construct : enclosedconstructs) {
            if (construct instanceof ReturnC) {
                ReturnC returnC = (ReturnC) construct;
                returns.add(returnC);
            }
        }
        //        ResultsCollector collector = new ResultsCollector(returns.size()) {
        //            @Override
        //            public <T> void completed(IGrade<T> grade) {
        //                
        //            }
        //        };
        for (ReturnC item : returns) {
            schedule(item.getReturnedConstruct().evaluate(getContext(), acceptor));
        }
        //        if (returns.isEmpty()) {
        //            updateGrade(acceptor, Grade.DONE);
        //        }
    }
    
    @Override
    public String describe() {
        return super.describe() + "\nfor " + methodDecl.displayName();
    }
}
