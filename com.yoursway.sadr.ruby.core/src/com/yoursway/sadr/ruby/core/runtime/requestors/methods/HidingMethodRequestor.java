package com.yoursway.sadr.ruby.core.runtime.requestors.methods;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;

public class HidingMethodRequestor implements MethodRequestor {
    
    private final Set<String> hiddenMethods = new HashSet<String>();
    private final MethodRequestor next;
    
    public HidingMethodRequestor(MethodRequestor next) {
        this.next = next;
    }
    
    public void accept(RubyMethod method) {
        String name = method.name();
        if (hiddenMethods.contains(name))
            return;
        hiddenMethods.add(name);
        next.accept(method);
    }
    
}
