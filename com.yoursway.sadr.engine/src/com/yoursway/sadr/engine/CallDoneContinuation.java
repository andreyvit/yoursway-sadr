/**
 * 
 */
package com.yoursway.sadr.engine;

public final class CallDoneContinuation<R extends Result> implements SimpleContinuation {
    
    private final R result;
    
    public CallDoneContinuation(R result) {
        this.result = result;
    }
    
    public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
        return requestor.done(result);
    }
    
}