package com.yoursway.sadr.succeeder;

/**
 * This strategy prunes if given time limit is exceeded.
 * */
public class TimeLimitBasedSchedulingStrategy implements ISchedulingStrategy {
	
	private final long timeLimit;
	private final long startTime;

	/**
	 * @param timeLimit - time limit in milliseconds.
	 */
	public TimeLimitBasedSchedulingStrategy(long timeLimit) {
		if (timeLimit < 0)
			throw new IllegalArgumentException("Time limit van not be negative.");
		this.timeLimit = timeLimit;
		this.startTime = System.currentTimeMillis();
	}

	public int getPriority(IGoal goal) {
		return 0;
	}

	public boolean prune(IGoal goal) {
		return System.currentTimeMillis() - startTime > timeLimit;
	}

}
