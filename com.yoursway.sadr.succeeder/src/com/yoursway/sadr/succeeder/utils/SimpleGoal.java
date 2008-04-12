/**
 * 
 */
package com.yoursway.sadr.succeeder.utils;

import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGoalResultAcceptor;
import com.yoursway.sadr.succeeder.IGoalScheduler;

/**
 * Simple implementation of IGoal:
 * <ul>
 * <li>all subgoals return values of the same type</li>
 * <li>goal awaits finishing of all subgoals</li>
 * </ul>
 * <p>
 * To use this implementation, just inherit this class and implement <code>preRun</code> and <code>subtasksFinished</code> methods.
 * <p>
 * <code>resultAcceptor</code> and <code>scheduler</code> variables are available and may be used to return values and schedule tasks.
 */
public abstract class SimpleGoal<ResultT, SubgoalsResultT> implements
		IGoal<ResultT>, ISubgoalsResultsAcceptor<SubgoalsResultT> {

	protected class SimpleScheduler {
		public void schedule(IGoal<SubgoalsResultT> goal) {
			subtasksListener.addToScheduler(rawScheduler, goal);
		}
	}

	private IGoalScheduler rawScheduler;
	protected IGoalResultAcceptor<ResultT> resultAcceptor;
	protected SimpleScheduler scheduler;
	private SimpleSubtasksListener<SubgoalsResultT> subtasksListener = new SimpleSubtasksListener<SubgoalsResultT>(
			this);

	public final void isCanceled() {
	}

	public final void postRun() {
	}

	public final void setGoalContext(IGoalScheduler scheduler,
			IGoalResultAcceptor<ResultT> resultAcceptor) {
		this.rawScheduler = scheduler;
		this.resultAcceptor = resultAcceptor;
		this.scheduler = new SimpleScheduler();
	}
}
