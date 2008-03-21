package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class AssignmentInfo {
    
    private final Wildcard wildcard;
    private final RubyConstruct construct;
    
    public AssignmentInfo(Wildcard wildcard, RubyConstruct construct) {
        this.wildcard = wildcard;
        this.construct = construct;
    }
    
    public Wildcard wildcard() {
        return wildcard;
    }
    
    public RubyConstruct construct() {
        return construct;
    }
    
}
