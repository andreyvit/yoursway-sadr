package com.yoursway.sadr.succeeder;

/**
 * Goals scheduling strategy. Defines the way {@link IScheduler}
 * executes goals.  
 * */
public interface ISchedulingStrategy {

	/**
	 * @return priority of the specified goal.
	 * */
	int getPriority(IGoal goal);

}
