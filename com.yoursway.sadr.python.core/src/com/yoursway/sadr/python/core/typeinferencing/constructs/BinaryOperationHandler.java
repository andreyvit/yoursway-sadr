package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;

/**
 * Handler for ordinary binary operations
 */
public interface BinaryOperationHandler extends Continuation {
    
    void setContext(PythonConstructImpl<BinaryExpression> context, ValueInfoGoal leftGoal,
            ValueInfoGoal rightGoal);
    
    ValueInfo result();
    
    public abstract void done(ContinuationScheduler requestor);
    
    public abstract void provideSubgoals(SubgoalRequestor requestor);
}
