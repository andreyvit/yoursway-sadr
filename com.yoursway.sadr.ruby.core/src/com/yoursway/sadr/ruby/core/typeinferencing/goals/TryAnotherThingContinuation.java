/**
 * 
 */
package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SimpleContinuation;

public final class TryAnotherThingContinuation implements ValueInfoContinuation {
    
    private final SimpleContinuation alternative;
    private final ValueInfoContinuation continuation;
    
    public TryAnotherThingContinuation(SimpleContinuation alternative, ValueInfoContinuation continuation) {
        this.alternative = alternative;
        this.continuation = continuation;
    }
    
    public ContinuationRequestorCalledToken consume(IValueInfo result, ContinuationScheduler requestor) {
        if (result.isEmpty())
            return alternative.run(requestor);
        else
            return continuation.consume(result, requestor);
    }
    
}