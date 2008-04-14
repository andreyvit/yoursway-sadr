package com.yoursway.sadr.succeeder;

/**
 * The bigger is priority the earlier it will run. 
 */
public interface ISchedulingStrategy {

	int getPriority(IGoal goal);

}
