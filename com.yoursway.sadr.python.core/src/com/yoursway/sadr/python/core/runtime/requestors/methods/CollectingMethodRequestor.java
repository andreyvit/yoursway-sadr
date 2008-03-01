package com.yoursway.sadr.python.core.runtime.requestors.methods;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.python.core.runtime.RubyMethod;

public class CollectingMethodRequestor implements MethodRequestor {
    
    private final Collection<RubyMethod> results = new ArrayList<RubyMethod>();
    
    public void accept(RubyMethod method) {
        results.add(method);
    }
    
    public Collection<RubyMethod> asCollection() {
        return results;
    }
    
    public RubyMethod[] asArray() {
        return results.toArray(new RubyMethod[results.size()]);
    }
    
    public boolean anythingFound() {
        return !results.isEmpty();
    }
    
}
