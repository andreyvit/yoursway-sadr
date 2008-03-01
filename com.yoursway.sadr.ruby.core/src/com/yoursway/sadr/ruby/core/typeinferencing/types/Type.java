package com.yoursway.sadr.ruby.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;

public interface Type {
    
    String describe();
    
    void findMethodsByPrefix(String prefix, Collection<RubyMethod> methods);
    
    void findMethod(String name, MethodRequestor requestor);
    
}
