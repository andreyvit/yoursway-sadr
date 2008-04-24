package com.yoursway.sadr.succeeder;

import java.util.Collection;

/**
 * Scheduler for the subgoals. Any subgoal scheduled may be later canceled.
 * Canceling is asynchronous, so subgoal may still calculate some results before
 * being stopped.
 */
public interface IScheduler {

	void schedule(IGoal goal, IGoal subgoal);

	void schedule(IGoal goal, IGoal subgoal, ISchedulingStrategy strategy);

	void schedule(IGoal goal, Collection<IGoal> subgoals, ISchedulingStrategy strategy);

	<T extends IGrade<T>> CheckpointToken checkpoint(IAcceptor acceptor, IGrade<T> grade);

}
