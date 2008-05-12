package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.AbstractGoal;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Result;

public abstract class AbstractValueInfoGoal extends AbstractGoal implements ValueInfoGoal,
        ValueInfoContinuation {
    
    private ValueInfo result = null;
    
    public void causesRecursion() {
        setResult(emptyValueInfo());
        done();
    }
    
    @Override
    public void done() {
        if (result == null)
            throw new IllegalStateException("setResult of " + toString() + " has never been called");
    }
    
    public ValueInfo result(ContextSensitiveThing thing) {
        if (thing != null)
            expandTo(thing);
        return roughResult();
    }
    
    public ValueInfo roughResult() {
        if (result == null)
            throw new IllegalStateException(getClass().getSimpleName() + ".result() before done()");
        return result;
    }
    
    public void copyAnswerFrom(Goal goal) {
        copyAnswerFrom(((ValueInfoGoal) goal).result(thing()));
    }
    
    public void copyAnswerFrom(Result result) {
        this.result = (ValueInfo) result;
        done();
    }
    
    protected final void setResult(ValueInfo result) {
        if (result == null)
            throw new NullPointerException("result");
        if (this.result != null)
            throw new IllegalStateException("Result can only be set once, dude!");
        this.result = result;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getClass().getSimpleName()).append(" <").append(describeParameters()).append(">");
        if (result != null)
            s.append(" = ").append(result);
        return s.toString();
    }
    
    protected abstract String describeParameters();
    
    public ContinuationRequestorCalledToken consume(IValueInfo result, ContinuationScheduler requestor) {
        setResult(ValueInfo.from(result));
        return requestor.done();
    }
    
}
