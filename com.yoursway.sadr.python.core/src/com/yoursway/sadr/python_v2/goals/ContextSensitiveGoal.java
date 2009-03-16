package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.succeeder.Goal;

public abstract class ContextSensitiveGoal extends Goal<PythonValueSet> {
    protected PythonValueSet acceptor;
    private final Krocodile crocodile;
    
    public ContextSensitiveGoal(Krocodile crocodile) {
        this.crocodile = crocodile;
    }
    
    public Krocodile getKrocodile() {
        return crocodile;
    }
    
    public PythonValueSet getAcceptor() {
        return acceptor;
    }
    
    public PythonValueSet evaluate() {
        this.acceptor = new PythonValueSet();
        preRun();
        return acceptor;
    }
    
    public void preRun() {
        
    }
    
    @Override
    public String describe() {
        String simpleName = this.getClass().getSimpleName();
        if (simpleName.equals("")) {
            simpleName = this.getClass().getName();
            simpleName = simpleName.substring(simpleName.lastIndexOf('.') + 1);
        }
        if (this.getKrocodile() != null) {
            return simpleName + " with context";
        } else {
            return simpleName;
        }
    }
}
