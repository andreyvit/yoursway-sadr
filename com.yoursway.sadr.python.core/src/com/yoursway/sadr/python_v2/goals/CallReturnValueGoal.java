package com.yoursway.sadr.python_v2.goals;

import java.util.LinkedList;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ReturnC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
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
            //FIXME: returning only first return result for now.
            break;
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
