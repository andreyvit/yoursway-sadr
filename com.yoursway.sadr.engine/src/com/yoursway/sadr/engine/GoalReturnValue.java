package com.yoursway.sadr.engine;

/**
 * The only way to get an instance of this class is calling API methods.
 */

public final class GoalReturnValue {
    private GoalReturnValue() {
    }
    
    private static final GoalReturnValue instance = new GoalReturnValue();
    
    static GoalReturnValue instance() {
        return instance;
    }
}
