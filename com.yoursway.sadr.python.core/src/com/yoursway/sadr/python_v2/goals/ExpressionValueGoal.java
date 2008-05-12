package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class ExpressionValueGoal extends ContextSensitiveGoal {
    
    protected final PythonValueSetAcceptor acceptor;
    
    public ExpressionValueGoal(Context context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.acceptor = acceptor;
    }
    
    PythonValueSetAcceptor createAcceptor(final PythonValueSetAcceptor resultAcceptor) {
        return new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                resultAcceptor.setResult(getResult());
                updateGrade(resultAcceptor, grade);
            }
        };
    }
}
