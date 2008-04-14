/**
 * 
 */
package com.yoursway.sadr.succeeder;

/**
 * Simple implementation of IGoal:
 * <ul>
 * <li>all subgoals return values of the same type</li>
 * <li>goal awaits finishing of all subgoals</li>
 * </ul>
 * <p>
 * To use this implementation, just inherit this class and implement
 * <code>preRun</code> and <code>subtasksFinished</code> methods.
 * <p>
 * <code>resultAcceptor</code> and <code>scheduler</code> variables are
 * available and may be used to return values and schedule tasks.
 */
public abstract class Goal implements IGoal {

	private IScheduler scheduler;

	protected IScheduler getScheduler() {
		return scheduler;
	}

	public final void isCanceled() {
	}

	public final void setScheduler(IScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public DumbReturnValue flush() {
		return null;
	}
}
