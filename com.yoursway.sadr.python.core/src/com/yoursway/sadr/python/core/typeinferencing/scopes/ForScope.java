package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.dltk.python.parser.ast.statements.ForEachStatement;

import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

public class ForScope extends LocalScope {
    
    private final ForCounterVariable counter;
    
    public ForScope(Scope parent, ForEachStatement node) {
        super(parent, node);
        counter = new ForCounterVariable(this);
    }
    
    @Override
    public ForEachStatement node() {
        return (ForEachStatement) super.node();
    }
    
    public ForCounterVariable counter() {
        return counter;
    }
    
    @Override
    protected String leafToString() {
        return "for " + counter.name();
    }
    
    @Override
    public RubyVariable findOwnVariable(String name) {
        if (name.equalsIgnoreCase(counter.name()))
            return counter;
        return null;
    }
    
    public ValueInfo selfType() {
        return parent().selfType();
    }
    
}
