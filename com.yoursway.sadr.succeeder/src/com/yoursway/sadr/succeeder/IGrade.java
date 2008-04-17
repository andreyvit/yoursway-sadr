package com.yoursway.sadr.succeeder;

/**
 * Result readiness grade marker.
 * */
public interface IGrade<T extends IGrade<?>> extends Comparable<T>{
	
	boolean isDone();
	
}
