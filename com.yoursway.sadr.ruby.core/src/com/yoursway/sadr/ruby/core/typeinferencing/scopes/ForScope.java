package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import org.eclipse.dltk.ruby.ast.RubyForStatement2;

import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public class ForScope extends LocalScope {
    
    private final ForCounterVariable counter;
    
    public ForScope(Scope parent, RubyForStatement2 node) {
        super(parent, node);
        counter = new ForCounterVariable(this);
    }
    
    @Override
    public RubyForStatement2 node() {
        return (RubyForStatement2) super.node();
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
