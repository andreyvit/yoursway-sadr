package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.constructs.FieldAccessC;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveReference extends Goal {
    
    private final PythonValueSetAcceptor acceptor;
    
    public ResolveReference(FieldAccessC fieldAccessC, PythonValueSetAcceptor acceptor) {
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        // TODO Auto-generated method stub
        acceptor.checkpoint(Grade.DONE);
    }
    
    @Override
    protected String describe() {
        return this.getClass().getSimpleName();
    }
}
