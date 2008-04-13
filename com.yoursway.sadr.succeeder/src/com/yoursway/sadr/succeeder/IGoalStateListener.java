package com.yoursway.sadr.succeeder;

/**
 * Goals subscribe to the status of subgoal by passing implementation of
 * <code>IGoalStateListener&lt;ResultT&gt;</code> to
 * <code>IGoalScheduler#schedule</code>.
 * 
 * There are several adapters of
 * <code>IGoalStateListener&lt;ResultT&gt;</code> providing simpler interface.
 */
public interface IGoalStateListener<ResultT> {
	void started(IGoal<ResultT> goal);
	
	void resultProduced(IGoal<ResultT> goal, ResultT result);

	void finished(IGoal<ResultT> goal);

	void canceled(IGoal<ResultT> goal);
}
