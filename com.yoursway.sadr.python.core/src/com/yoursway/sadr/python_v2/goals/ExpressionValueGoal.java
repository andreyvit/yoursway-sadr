package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;

public abstract class ExpressionValueGoal extends ContextSensitiveGoal {
    
    protected final PythonValueSetAcceptor acceptor;
    
    public ExpressionValueGoal(Krocodile context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.acceptor = acceptor;
    }
    
    //    PythonValueSetAcceptor createAcceptor(final PythonValueSetAcceptor resultAcceptor) {
    //        return new PythonValueSetAcceptor() {
    //            public <T> void checkpoint(IGrade<T> grade) {
    //                resultAcceptor.setResults(this);
    //                updateGrade(resultAcceptor, grade);
    //            }
    //        };
    //    }
}
