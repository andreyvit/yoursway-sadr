package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;

public class AssignmentInfo {
    
    private final RubyConstruct rhs;
    private final AccessInfo access;
    
    public AssignmentInfo(AccessInfo access, RubyConstruct rhs) {
        this.access = access; //? lvalue
        this.rhs = rhs;
    }
    
    public AccessInfo accessInfo() {
        return access;
    }
    
    public RubyConstruct rhs() {
        return rhs;
    }
    
    public String variableName() {
        return access.variableName();
    }
    
    public Wildcard wildcard() {
        return access.wildcard();
    }
    
    public RubyConstruct receiver() {
        return access.receiver();
    }
    
}