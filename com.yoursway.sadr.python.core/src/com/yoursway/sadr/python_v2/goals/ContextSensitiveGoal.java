package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.Goal;

public abstract class ContextSensitiveGoal extends Goal {
    private final Context context;
    
    public ContextSensitiveGoal(Context context) {
        this.context = context;
    }
    
    public Context getContext() {
        return context;
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
        if (this.getContext() != null) {
            return simpleName + " with context";
        } else {
            return simpleName;
        }
    }
}
