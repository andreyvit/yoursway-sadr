package com.yoursway.sadr.ruby.core.runtime.std;

import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubySimpleType;

public interface StandardTypes {
    
    RubyClass objectClass();
    
    RubySimpleType intType();
    
    RubySimpleType stringType();
    
    RubySimpleType nilType();
    
}