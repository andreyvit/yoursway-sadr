package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.Goal;

public abstract class ContextSensitiveGoal extends Goal {
    private final Krocodile crocodile;
    
    public ContextSensitiveGoal(Krocodile context) {
        this.crocodile = context;
        if (context == null) {
            throw new NullPointerException("Context is null");
        }
    }
    
    public Krocodile getKrocodile() {
        return crocodile;
    }
    
    @Override
    public CheckpointToken flush() {
        // TODO Auto-generated method stub
        return null;
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
