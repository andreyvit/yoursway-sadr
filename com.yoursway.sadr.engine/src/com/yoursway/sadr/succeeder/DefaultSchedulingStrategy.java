package com.yoursway.sadr.succeeder;
/**
 * Zero priority, no pruning.
 */
public class DefaultSchedulingStrategy implements ISchedulingStrategy {

	public int getPriority(IGoal goal) {
		return 0;
	}

	public boolean prune(IGoal goal) {
		return false;
	}

}
