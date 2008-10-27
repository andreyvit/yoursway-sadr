package com.yoursway.sadr.python.core.runtime.requestors.methods;

import java.util.ArrayList;
import java.util.Collection;

public class MethodNamesRequestor implements MethodRequestor {
    
    private final Collection<String> results = new ArrayList<String>();
    
    public void accept(PythonMethod method) {
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
