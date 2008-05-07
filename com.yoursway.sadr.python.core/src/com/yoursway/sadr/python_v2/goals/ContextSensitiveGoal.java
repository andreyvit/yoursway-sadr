package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.succeeder.Goal;

public abstract class ContextSensitiveGoal extends Goal {
    private final Context context;
    
    public ContextSensitiveGoal(Context context) {
        this.context = context;
    }
    
    public Context getContext() {
        return context;
    }
}
