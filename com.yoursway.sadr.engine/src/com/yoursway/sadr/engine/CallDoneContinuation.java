/**
 * 
 */
package com.yoursway.sadr.engine;

public final class CallDoneContinuation implements SimpleContinuation {
    
    public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
        return requestor.done();
    }
    
}