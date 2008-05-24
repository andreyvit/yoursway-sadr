package com.yoursway.sadr.python_v2.goals.acceptors;

import com.yoursway.sadr.succeeder.IGrade;

abstract public class Synchronizer {
    protected int counter;
    
    protected Synchronizer() {
        counter = 0;
    }
    
    public Synchronizer(int i) {
        assert i > 0;
        counter = i;
    }
    
    protected <T> void subgoalDone(IGrade<T> grade) {
        --counter;
        if (counter <= 0) {
            completed(grade);
        }
    }
    
    public abstract <T> void completed(IGrade<T> grade);
}
