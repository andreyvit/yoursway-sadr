package com.yoursway.sadr.python.core.runtime.requestors.methods;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.python.core.runtime.PythonMethod;

public class HidingMethodRequestor implements MethodRequestor {
    
    private final Set<String> hiddenMethods = new HashSet<String>();
    private final MethodRequestor next;
    
    public HidingMethodRequestor(MethodRequestor next) {
        this.next = next;
    }
    
    public void accept(PythonMethod method) {
        String name = method.name().toLowerCase();
        if (hiddenMethods.contains(name))
            return;
        hiddenMethods.add(name);
        next.accept(method);
    }
    
}
