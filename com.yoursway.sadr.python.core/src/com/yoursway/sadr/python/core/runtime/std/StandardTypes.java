package com.yoursway.sadr.python.core.runtime.std;

import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.RubySimpleType;

public interface StandardTypes {
    
    RubyClass objectClass();
    
    RubySimpleType intType();
    
    RubySimpleType stringType();
    
    RubySimpleType nilType();
    
}