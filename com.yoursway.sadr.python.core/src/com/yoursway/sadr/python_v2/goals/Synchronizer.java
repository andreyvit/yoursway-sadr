package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.succeeder.IGrade;

abstract class Synchronizer {
    private int counter;
    
    public Synchronizer(int i) {
        assert i > 0;
        counter = i;
    }
    
    public <T> void subgoalDone(IGrade<T> grade) {
        --counter;
        if (counter <= 0) {
            allDone(grade);
        }
    }
    
    public abstract <T> void allDone(IGrade<T> grade);
}
