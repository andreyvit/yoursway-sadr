package com.yoursway.sadr.succeeder;

import java.util.Collection;

/**
 * Scheduler for the subgoals. All the subgoals scheduled will be run and their
 * state changes will be reported to the IGoalStateListener.
 * 
 * Any subgoal scheduled may be later canceled. Canceling is asynchronous, so
 * subgoal may still calculate some results before being stopped.
 */
public interface IScheduler {
	
	void schedule(IGoal goal);

	void schedule(IGoal goal, ISchedulingStrategy strategy);

	void schedule(Collection<IGoal> goals, ISchedulingStrategy strategy);

	<T extends IGrade<T>> DumbReturnValue checkpoint(IAcceptor acceptor, IGrade<T> grade);
	
}