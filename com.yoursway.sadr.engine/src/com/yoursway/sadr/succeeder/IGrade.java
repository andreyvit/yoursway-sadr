package com.yoursway.sadr.succeeder;

/**
 * Result readiness grade marker.
 */
public interface IGrade<T> extends Comparable<T> {
    
    boolean isDone();
}
