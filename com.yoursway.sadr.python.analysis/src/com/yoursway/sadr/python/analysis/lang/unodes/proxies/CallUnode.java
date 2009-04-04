package com.yoursway.sadr.python.analysis.lang.unodes.proxies;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.analysis.Constants;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.CallSiteDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class CallUnode extends Unode {
    
    private final Bnode callable;
    private final Arguments arguments;
    
    public CallUnode(Bnode callable, Arguments arguments) {
        if (callable == null)
            throw new NullPointerException("call is null");
        if (arguments == null)
            throw new NullPointerException("arguments is null");
        this.callable = callable;
        this.arguments = arguments;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonLexicalContext sc, PythonDynamicContext dc) {
        int size = dc.callStackSize();
        if (size >= Constants.MAXIMUM_CALL_STACK_DEPTH)
            return PythonValueSet.EMPTY;
        PythonValueSet callableValueSet = Analysis.evaluate(new ExpressionValueGoal(callable, dc));
        return callableValueSet.call(createDynamicContext(dc));
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        int size = dc.callStackSize();
        if (size >= Constants.MAXIMUM_CALL_STACK_DEPTH)
            return;
        
        PythonValueSet callableValueSet = Analysis.evaluate(new ExpressionValueGoal(callable, dc));
        callableValueSet.findRenames(suffix, sc, createDynamicContext(dc), aliases);
    }
    
    public CallSiteDynamicContext createDynamicContext(PythonDynamicContext dc) {
        return new CallSiteDynamicContext(dc, arguments);
    }
    
    @Override
    protected int computeHashCode() {
        return callable.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        return this.callable == ((CallUnode) obj).callable;
    }
    
    @Override
    public String toString() {
        return "#{" + callable + "}";
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
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
    }
    
}
