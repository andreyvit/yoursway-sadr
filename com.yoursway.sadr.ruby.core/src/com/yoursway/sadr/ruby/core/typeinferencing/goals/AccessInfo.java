package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.ArrayWildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;

public class AccessInfo {
    
    private final Wildcard wildcard;
    private final RubyConstruct receiver;
    private final String variableName;
    
    public AccessInfo(RubyConstruct receiver, String variableName, Wildcard wildcard) {
        this.receiver = receiver;
        this.variableName = variableName;
        this.wildcard = wildcard;
    }
    
    public RubyConstruct receiver() {
        return receiver;
    }
    
    public Wildcard wildcard() {
        return wildcard;
    }
    
    public String variableName() {
        return variableName;
    }
    
    public AccessInfo wrapIntoArray() {
        return new AccessInfo(receiver, variableName, new ArrayWildcard(wildcard));
    }
    
}
