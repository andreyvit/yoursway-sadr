/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;

public final class TryAnotherThingContinuation implements ValueInfoContinuation {
    
    private final SimpleContinuation alternative;
    private final ValueInfoContinuation continuation;
    
    public TryAnotherThingContinuation(SimpleContinuation alternative, ValueInfoContinuation continuation) {
        this.alternative = alternative;
        this.continuation = continuation;
    }
    
    public void consume(IValueInfo result, ContinuationRequestor requestor) {
        if (result.isEmpty())
            alternative.run(requestor);
        else
            continuation.consume(result, requestor);
    }
    
}