package com.yoursway.sadr.succeeder;

import java.util.Collection;

/**
 * Scheduler for the subgoals. Any subgoal scheduled may be later canceled.
 * Canceling is asynchronous, so subgoal may still calculate some results before
 * being stopped.
 * 
 * Scheduler is also responsible for making <b>checkpoints</b>. Checkpoint is
 * the notification that goal has got some partial result, and this result need
 * to be passed to parent goal through the acceptor.
 * 
 * Acceptor passed to the checkpoint will be called later by the engine, and is
 * responsible for passing the result to the parent goal when called.
 */
public interface IScheduler {

	void schedule(IGoal goal);

	void schedule(IGoal goal, ISchedulingStrategy strategy);

	void schedule(Collection<IGoal> goals, ISchedulingStrategy strategy);

	<T extends IGrade<T>> CheckpointToken checkpoint(IAcceptor acceptor,
			IGrade<T> grade);

}
