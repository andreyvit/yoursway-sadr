package com.yoursway.sadr.python.core.typeinferencing.typesets;

import java.util.Collection;

import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.python.core.typeinferencing.types.Type;

public interface TypeSet {
    
    boolean isEmpty();
    
    Collection<Type> containedTypes();
    
    RubyMethod[] findMethodsByPrefix(String prefix);
    
    String[] describePossibleTypes();
    
    void findMethod(String name, MethodRequestor requestor);
    
}