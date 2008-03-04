package com.yoursway.sadr.python.core.runtime.requestors.methods;

import com.yoursway.sadr.python.core.runtime.PythonMethod;

public class AnyMethodRequestor implements MethodRequestor {
    
    private boolean any = false;
    
    public void accept(PythonMethod method) {
        any = true;
    }
    
    public boolean anythingFound() {
        return any;
    }
    
}
