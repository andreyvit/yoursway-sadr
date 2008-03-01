package com.yoursway.sadr.python.core.runtime.requestors.methods;

import com.yoursway.sadr.python.core.runtime.RubyMethod;

public interface MethodRequestor {
    
    void accept(RubyMethod method);
    
}
