package com.yoursway.sadr.python.core.typeinferencing.goals;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;

public class CallInfo {
    
    private final Wildcard wildcard;
    private final CallExpression node;
    
    public CallInfo(Wildcard wildcard, CallExpression node) {
        this.wildcard = wildcard;
        this.node = node;
    }
    
    public Wildcard getWildcard() {
        return wildcard;
    }
    
    public CallExpression getNode() {
        return node;
    }
    
}
