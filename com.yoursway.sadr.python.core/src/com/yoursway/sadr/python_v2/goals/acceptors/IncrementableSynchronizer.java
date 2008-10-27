package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.Collection;
import java.util.LinkedList;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class IncrementableSynchronizer<AcceptorType extends IAcceptor> extends Synchronizer {
    
    private final Collection<AcceptorType> acceptors = new LinkedList<AcceptorType>();
    private final AcceptorType totalResultAcceptor;
    
    public IncrementableSynchronizer(AcceptorType totalResultAcceptor) {
        this.totalResultAcceptor = totalResultAcceptor;
    }
    
    public void addAcceptor(AcceptorType delegate) {
        this.acceptors.add(delegate);
        increment();
    }
    
    //TODO replace increment-decrement mechanism with something more safe.
    public void increment() {
        if (counter < 0)
            throw new IllegalArgumentException();
        ++counter;
    }
    
    public void decrement() {
        if (counter <= 0)
            throw new IllegalArgumentException();
        --counter;
        checkCompleted();
    }
    
    AcceptorType getTotalResultAcceptor() {
        return totalResultAcceptor;
    }
    
    public void checkCompleted() {
        if (counter == 0) {
            completed(Grade.DONE);
            counter = -1;
        }
    }
}
