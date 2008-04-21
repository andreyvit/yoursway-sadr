package com.yoursway.sadr.succeeder;

/**
 * Result readiness grade marker
 */
public interface IGrade<T extends IGrade<?>> extends Comparable<T> {

	/**
	 * Is this grade signifies the final state of goal execution? When goal is
	 * checkpointed with grade.isDone == true, this goal will be scheduled no
	 * more.
	 */
	boolean isDone();
}
