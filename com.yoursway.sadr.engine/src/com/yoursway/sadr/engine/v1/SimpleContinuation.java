package com.yoursway.sadr.engine.v1;

public interface SimpleContinuation {
    ContinuationRequestorCalledToken run(ContinuationScheduler requestor);
}
