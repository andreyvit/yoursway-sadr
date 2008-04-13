/**
 * 
 */
package com.yoursway.sadr.engine.v1;

public final class CallDoneContinuation implements SimpleContinuation {
    
    public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
        return requestor.done();
    }
    
}