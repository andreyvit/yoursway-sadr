package com.yoursway.sadr.succeeder;

/**
 * Scheduler for the subgoals. All the subgoals scheduled will be run and their
 * state changes will be reported to the IGoalStateListener.
 * 
 * Any subgoal scheduled may be later canceled. Canceling is asynchronous, so
 * subgoal may still calculate some results before being stopped.
 */
public interface IGoalScheduler {
	<T> void schedule(IGoal<T> goal, IGoalStateListener<T> listener);

	<T> void cancel(IGoal<T> goal);
}
