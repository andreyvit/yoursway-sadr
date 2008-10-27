package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;

public abstract class ExpressionValueGoal extends ContextSensitiveGoal {
    
    protected final PythonValueSetAcceptor acceptor;
    
    public ExpressionValueGoal(Context context, PythonValueSetAcceptor acceptor) {
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
