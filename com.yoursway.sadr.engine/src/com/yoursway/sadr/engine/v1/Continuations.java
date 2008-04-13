package com.yoursway.sadr.engine.v1;

import java.util.Iterator;

public class Continuations {
    
    public static <T> ContinuationRequestorCalledToken iterate(Iterable<T> iterable,
            IterationContinuation<T> visitor, ContinuationScheduler requestor, SimpleContinuation continuation) {
        return iterate(iterable.iterator(), visitor, requestor, continuation);
    }
    
    public static <T> ContinuationRequestorCalledToken iterate(Iterator<T> iterator,
            final IterationContinuation<T> visitor, ContinuationScheduler requestor,
            final SimpleContinuation continuation) {
        if (iterator.hasNext()) {
            T value = iterator.next();
            final Iterator<T> iterator2 = iterator;
            SimpleContinuation cont = new SimpleContinuation() {
                
                public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                    return iterate(iterator2, visitor, requestor, continuation);
                }
                
            };
            return visitor.iteration(value, requestor, cont);
        } else {
            return continuation.run(requestor);
        }
        
    }
    
}
