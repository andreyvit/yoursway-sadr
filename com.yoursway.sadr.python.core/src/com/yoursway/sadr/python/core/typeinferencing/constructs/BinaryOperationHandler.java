package com.yoursway.sadr.python.core.typeinferencing.constructs;


import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;

/**
 * Handler for ordinary binary operations
 */
public interface BinaryOperationHandler extends Continuation {
    
    void setContext(PythonConstruct context, ValueInfoGoal leftGoal,
            ValueInfoGoal rightGoal);
    
    ValueInfo result();
    
    public abstract void done(ContinuationScheduler requestor);
    
    public abstract Goal[] provideSubgoals();
}
