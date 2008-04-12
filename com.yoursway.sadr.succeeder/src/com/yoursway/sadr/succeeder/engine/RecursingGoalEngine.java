package com.yoursway.sadr.succeeder.engine;

import java.util.LinkedList;
import java.util.List;

import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGoalResultAcceptor;
import com.yoursway.sadr.succeeder.IGoalScheduler;
import com.yoursway.sadr.succeeder.IGoalStateListener;

/**
 * Proof-of-concept goal engine, which uses recursion and does not implement
 * meaningful task canceling (while being conformant goal engine though).
 * 
 * Key point of the type-safe implementation of GoalEngine is paring of IGoal
 * and IGoalStateListener - IGoal<T>, IGoalStateListener<T> and handler should
 * be created at the same time, and handler should expose generic interface to
 * the world, while still being correctly typed inside.
 */
public class RecursingGoalEngine<ResultT> {

	public static final IGoalStateListener<String> NULL_ACCEPTOR = new IGoalStateListener<String>() {
		public void canceled(IGoal<String> goal) {
		}

		public void finished(IGoal<String> goal) {
		}

		public void resultProduced(IGoal<String> goal, String t) {
		}

		public void started(IGoal<String> goal) {
		}
	};

	private IGoal<ResultT> goal;
	private IGoalStateListener<ResultT> resultListener;

	public RecursingGoalEngine(IGoal<ResultT> goal,
			IGoalStateListener<ResultT> resultListener) {
		this.goal = goal;
		this.resultListener = resultListener;
	}

	public void run() {
		final List<RecursingGoalEngine<?>> engines = new LinkedList<RecursingGoalEngine<?>>();

		IGoalScheduler scheduler = new IGoalScheduler() {
			public <T> void cancel(IGoal<T> goal) {
			}

			public <T> void schedule(IGoal<T> goal,
					IGoalStateListener<T> listener) {
				engines.add(new RecursingGoalEngine<T>(goal, listener));
			}
		};

		IGoalResultAcceptor<ResultT> acceptor = new IGoalResultAcceptor<ResultT>() {
			public void resultProduced(ResultT result) {
				resultListener.resultProduced(goal, result);
			}
		};

		goal.setGoalContext(scheduler, acceptor);
		resultListener.started(goal);
		goal.preRun();

		for (RecursingGoalEngine<?> i : engines)
			i.run();

		goal.postRun();
		resultListener.finished(goal);
	}
}
