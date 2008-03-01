package com.yoursway.sadr.ruby.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;

public class StubType implements Type {
    
    public static final Type WILDCARD = new StubType("WILDCARD");
    
    private final String name;
    
    public StubType(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public String describe() {
        return name;
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        throw new UnsupportedOperationException();
    }
    
    public void findMethodsByPrefix(String prefix, Collection<RubyMethod> methods) {
        throw new UnsupportedOperationException();
    }
    
}
