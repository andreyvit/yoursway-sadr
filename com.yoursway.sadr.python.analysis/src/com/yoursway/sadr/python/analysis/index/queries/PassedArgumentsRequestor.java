package com.yoursway.sadr.python.analysis.index.queries;

import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;

public interface PassedArgumentsRequestor {
    
    void call(PassedArgumentInfo info);
    
}
