package com.yoursway.sadr.engine;

public interface SimpleContinuation {
    ContinuationRequestorCalledToken run(ContinuationScheduler requestor);
}
