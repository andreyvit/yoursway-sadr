package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class Acceptor implements IAcceptor {
    
    public <T> void checkpoint(IGrade<T> grade) {
        
    }
}
