package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.engine.AbstractGoal;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.engine.Sinner;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;

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
    
    public ValueInfo result(Sinner victim) {
        if (victim != null)
            punish(victim);
        return resultWithoutKarma();
    }
    
    public ValueInfo resultWithoutKarma() {
        if (result == null)
            throw new IllegalStateException(getClass().getSimpleName() + ".result() before done()");
        return result;
    }
    
    public void copyAnswerFrom(Goal goal) {
        copyAnswerFrom(((ValueInfoGoal) goal).result(thou()));
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
    
    public void consume(ValueInfo result, ContinuationRequestor requestor) {
        setResult(result);
        requestor.done();
    }
    
}
