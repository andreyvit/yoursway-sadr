package com.yoursway.sadr.ruby.core.runtime.requestors.methods;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;

public class MethodNamesRequestor implements MethodRequestor {
    
    private final Collection<String> results = new ArrayList<String>();
    
    public void accept(RubyMethod method) {
        results.add(method.name());
    }
    
    public Collection<String> asCollection() {
        return results;
    }
    
    public String[] asArray() {
        return results.toArray(new String[results.size()]);
    }
    
    public boolean anythingFound() {
        return !results.isEmpty();
    }
    
}
