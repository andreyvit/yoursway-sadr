package com.yoursway.sadr.python.core.typeinferencing.types;

import java.util.Collection;

import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;

public interface Type {
    
    String describe();
    
    void findMethodsByPrefix(String prefix, Collection<PythonMethod> methods);
    
    void findMethod(String name, MethodRequestor requestor);
    
}
