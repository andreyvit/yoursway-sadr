package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class AssignmentInfo {
    
    private final Wildcard wildcard;
    private final Construct<Scope, ASTNode> construct;
    
    public AssignmentInfo(Wildcard wildcard, Construct<Scope, ASTNode> construct) {
        this.wildcard = wildcard;
        this.construct = construct;
    }
    
    public Wildcard wildcard() {
        return wildcard;
    }
    
    public Construct<Scope, ASTNode> construct() {
        return construct;
    }
    
}
