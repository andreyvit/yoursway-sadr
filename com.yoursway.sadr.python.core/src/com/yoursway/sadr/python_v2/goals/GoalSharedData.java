package com.yoursway.sadr.python_v2.goals;

public class GoalSharedData {
    private int value;
    
    public GoalSharedData(int value) {
        this.value = value;
        
    }
    
    synchronized public <T> int decrement() {
        return --value;
    }
    
}
