package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;

public interface IntegerTypesSupport {
    
    RuntimeModelWithIntegerTypes facelet(RuntimeModel model);

    ContinuationRequestorCalledToken evaluateIntegerLiteral(RuntimeModel runtimeModel, long intValue, ContinuationScheduler requestor, ValueInfoContinuation continuation);
    
}
