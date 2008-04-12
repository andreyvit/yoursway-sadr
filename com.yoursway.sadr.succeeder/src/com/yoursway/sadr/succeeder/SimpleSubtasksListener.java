package com.yoursway.sadr.succeeder;

import java.util.LinkedList;
import java.util.List;

import com.yoursway.sadr.succeeder.utils.ISubgoalsResultsAcceptor;

public class SimpleSubtasksListener<ResultT> implements IGoalStateListener<ResultT> {
	
	private int goalsCount = 0;
	private List<ResultT> results = new LinkedList<ResultT>();
	private ISubgoalsResultsAcceptor<ResultT> acceptor;
	
	public SimpleSubtasksListener(ISubgoalsResultsAcceptor<ResultT> acceptor) {
		this.acceptor = acceptor;
	}

	public void canceled(IGoal<ResultT> goal) {
		throw new RuntimeException("Goals with attached SimpleSubtasksListener must not be canceled. This one was: " + goal.toString());
	}

	public void started(IGoal<ResultT> goal) {
		assert goalsCount > 0 : "New goals must not be started after returning results";
	}

	public void resultProduced(IGoal<ResultT> goal, ResultT result) {
		results.add(result);
	}

	public void finished(IGoal<ResultT> goal) {
		if(--goalsCount == 0)
			acceptor.subtasksFinished(results);
	}
	
	public void addToScheduler(IGoalScheduler scheduler, IGoal<ResultT> goal) {
		goalsCount++;
		scheduler.schedule(goal, this);
	}
}
