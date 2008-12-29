package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.goals.acceptors.Synchronizer;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class Acceptor extends Synchronizer implements IAcceptor {
    
    private final Acceptor parent;
    
    public Acceptor() {
        super(1);
        this.parent = null;
    }
    
    protected Acceptor(Acceptor parent) {
        super(1);
        parent.addSubgoal();
        this.parent = parent;
    }
    
    @Override
    public <T> void checkpoint(IGrade<T> grade) {
        if (!this.isDone()) {
            throw new IllegalStateException("Checkpoint called when not all subgoals completed");
        }
        if (parent != null) {
            parent.subgoalDone(grade);
        }
    }
    
    @Override
    public String toString() {
        if (parent != null) {
            return super.toString() + "<-" + parent.toString();
        } else {
            return super.toString();
        }
    }
}
