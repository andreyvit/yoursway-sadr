package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.FieldAccessC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveReferenceGoal extends Goal {
    
    private final PythonValueSetAcceptor acceptor;
    
    public ResolveReferenceGoal(FieldAccessC fieldAccessC, PythonValueSetAcceptor acceptor) {
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        // TODO Auto-generated method stub
        acceptor.checkpoint(Grade.DONE);
    }
    
    @Override
    public String describe() {
        return this.getClass().getSimpleName();
    }
}
