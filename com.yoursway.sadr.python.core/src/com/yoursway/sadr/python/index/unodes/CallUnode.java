package com.yoursway.sadr.python.index.unodes;

import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.constructs.CallC;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public final class CallUnode extends Unode {
    
    private final CallC call;
    
    public CallUnode(CallC call) {
        if (call == null)
            throw new NullPointerException("call is null");
        this.call = call;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc) {
        return Analysis.evaluate(new ExpressionValueGoal(call, dc));
    }
    
    @Override
    protected int computeHashCode() {
        return call.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        return this.call == ((CallUnode) obj).call;
    }
    
    @Override
    public Punode punodize() {
        return null;
    }
    
    @Override
    public String toString() {
        return "#{" + call + "}";
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return null;
    }
    
    @Override
    public boolean isIndexable() {
        return false;
    }
    
    @Override
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Bnode> aliases) {
        call.findRenames(punode, sc, dc, aliases);
    }
    
}
