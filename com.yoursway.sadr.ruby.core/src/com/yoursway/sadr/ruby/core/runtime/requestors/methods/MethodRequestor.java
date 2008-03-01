package com.yoursway.sadr.ruby.core.runtime.requestors.methods;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;

public interface MethodRequestor {
    
    void accept(RubyMethod method);
    
}
