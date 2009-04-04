package com.yoursway.sadr.python.analysis.index.queries;

import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;

public interface PassedArgumentsRequestor {
    
    void call(PassedArgumentInfo info, PythonFileC fileC);
    
}
