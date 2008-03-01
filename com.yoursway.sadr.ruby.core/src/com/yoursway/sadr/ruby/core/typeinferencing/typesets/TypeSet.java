package com.yoursway.sadr.ruby.core.typeinferencing.typesets;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;

public interface TypeSet {
    
    boolean isEmpty();
    
    Collection<Type> containedTypes();
    
    RubyMethod[] findMethodsByPrefix(String prefix);
    
    String[] describePossibleTypes();
    
    void findMethod(String name, MethodRequestor requestor);
    
}
