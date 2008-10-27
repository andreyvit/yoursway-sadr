package com.yoursway.sadr.python.core.runtime.requestors.methods;

import java.util.ArrayList;
import java.util.Collection;

public class CollectingMethodRequestor implements MethodRequestor {
    
    private final Collection<PythonMethod> results = new ArrayList<PythonMethod>();
    
    public void accept(PythonMethod method) {
        results.add(method);
    }
    
    public Collection<PythonMethod> asCollection() {
        return results;
    }
    
    public PythonMethod[] asArray() {
        return results.toArray(new PythonMethod[results.size()]);
    }
    
    public boolean anythingFound() {
        return !results.isEmpty();
    }
    
}
