package com.yoursway.sadr.succeeder;

/**
 * A goal that can schedule subgoals.
 * <p>
 * Goals must not communicate directly - goal and subgoal may be run in
 * different threads, and many goals may be run concurrently. When subgoal need
 * to pass data to goal, it should call checkpoint() on IScheduler passed to it,
 * passing grade (completeness level) and acceptor - object that keeps the
 * partial results and passes it to the parent when called by engine.
 * <p>
 * Usually acceptors will be passed to subgoals by parents at their creation,
 * but it is not required. The only limitation for the acceptor is that it must
 * not be reused for reporting results to multiple parents, as reporting results
 * may happen in different threads simultaneously.
 */
public interface IGoal {
	/**
	 * Called once before preRun().
	 * 
	 * Cannot produce results. Cannot schedule subtasks.
	 * 
	 * @param scheduler
	 *            Scheduler to be used to schedule subgoals.
	 * @param resultAcceptor
	 *            Acceptor, accepting result(s) of goal. Can schedule subtasks
	 *            when called.
	 */
	void setScheduler(IScheduler scheduler);

	/**
	 * Called first when task is run. All the further processing of goal will be
	 * done through acceptors passed to subgoals.
	 */
	void preRun();

	/**
	 * Called when engine needs to get partial result from the goal (e.g. when
	 * goal is going to be canceled).
	 * 
	 * @return
	 */
	CheckpointToken flush();
}
