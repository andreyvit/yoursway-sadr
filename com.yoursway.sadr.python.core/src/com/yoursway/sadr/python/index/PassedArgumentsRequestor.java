package com.yoursway.sadr.python.index;

import com.yoursway.sadr.python.constructs.PythonFileC;
import com.yoursway.sadr.python.model.PassedArgumentInfo;

public interface PassedArgumentsRequestor {
    
    void call(PassedArgumentInfo info, PythonFileC fileC);
    
}
