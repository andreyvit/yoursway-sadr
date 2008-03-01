package com.yoursway.sadr.python.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;

public class UnknownType implements Type {
    
    public static final UnknownType INSTANCE = new UnknownType();
    
    private UnknownType() {
    }
    
    public String describe() {
        return "?";
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
    }
    
    public void findMethodsByPrefix(String prefix, Collection<RubyMethod> methods) {
    }
    
}
