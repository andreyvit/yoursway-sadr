package com.yoursway.sadr.engine;

import java.util.Iterator;

public class Continuations {
    
    public static <T> void iterate(Iterable<T> iterable, IterationContinuation<T> visitor,
            ContinuationRequestor requestor, SimpleContinuation continuation) {
        iterate(iterable.iterator(), visitor, requestor, continuation);
    }
    
    public static <T> void iterate(final Iterator<T> iterator, final IterationContinuation<T> visitor,
            ContinuationRequestor requestor, final SimpleContinuation continuation) {
        if (iterator.hasNext()) {
            T value = iterator.next();
            SimpleContinuation cont = new SimpleContinuation() {
                
                public void evaluate(ContinuationRequestor requestor) {
                    iterate(iterator, visitor, requestor, continuation);
                }
                
            };
            visitor.iteration(value, requestor, cont);
        } else {
            continuation.evaluate(requestor);
        }
        
    }
    
}
