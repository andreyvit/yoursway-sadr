package com.yoursway.sadr.python.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;

public interface Type {
    
    String describe();
    
    void findMethodsByPrefix(String prefix, Collection<RubyMethod> methods);
    
    void findMethod(String name, MethodRequestor requestor);
    
}
