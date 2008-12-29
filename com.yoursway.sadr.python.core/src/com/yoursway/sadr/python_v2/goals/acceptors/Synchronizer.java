package com.yoursway.sadr.python_v2.goals.acceptors;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.succeeder.IGrade;

abstract public class Synchronizer {
    private int counter;
    
    protected Synchronizer() {
        counter = 0;
    }
    
    public Synchronizer(int i) {
        if (i <= 0)
            throw new IllegalArgumentException();
        counter = i;
    }
    
    protected boolean isDone() {
        return counter <= 0;
    }
    
    protected <T> void addSubgoal() {
        if (counter < 0) {
            throw new IllegalStateException("addSubgoal after checkpoint(Grade.DONE) called");
        }
        counter++;
    }
    
    public <T> void subgoalDone(IGrade<T> grade) {
        if (counter < 0) {
            throw new IllegalStateException("subgoalDone after checkpoint(Grade.DONE) called");
        }
        if (grade == Grade.DONE) {
            --counter;
        }
        checkCompleted(grade);
    }
    
    protected <T> void checkCompleted(IGrade<T> grade) {
        if (counter < 0) {
            throw new IllegalStateException("subgoalDone after checkpoint(Grade.DONE) called");
        }
        if (counter == 0) {
            checkpoint(grade);
            if (grade == Grade.DONE) {
                counter = -1;
            }
        }
    }
    
    protected abstract <T> void checkpoint(IGrade<T> grade);
    
    @Override
    public String toString() {
        return String.valueOf(counter);
    }
}
