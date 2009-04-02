package com.yoursway.sadr.python.index.unodes;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public final class ComplexExpressionUnode extends Unode {
    
    private final PythonConstruct construct;
    
    public ComplexExpressionUnode(PythonConstruct construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        this.construct = construct;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext staticContext, PythonDynamicContext dc,
            List<PythonScope> currentScopes) {
        return Analysis.evaluate(new ExpressionValueGoal(construct, dc));
    }
    
    @Override
    protected int computeHashCode() {
        return construct.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        return this.construct == ((ComplexExpressionUnode) obj).construct;
    }
    
    @Override
    public Punode punodize() {
        return null;
    }
    
    @Override
    public String toString() {
        return "Expr(" + construct + ")";
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return null;
    }
    
}
