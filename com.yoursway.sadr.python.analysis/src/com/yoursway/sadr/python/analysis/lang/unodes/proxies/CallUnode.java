package com.yoursway.sadr.python.analysis.lang.unodes.proxies;

import java.util.Collection;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.CallC;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

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
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        call.findRenames(punode, sc, dc, aliases);
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Punode punode, boolean reading) {
    }
    
}
