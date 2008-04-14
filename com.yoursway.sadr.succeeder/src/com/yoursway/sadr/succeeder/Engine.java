package com.yoursway.sadr.succeeder;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class Engine implements IScheduler {

	private Map<IAcceptor, IGrade<?>> acceptors = new HashMap<IAcceptor, IGrade<?>>();
	private PriorityQueue<PrioritizedGoal> queue = new PriorityQueue<PrioritizedGoal>();
	private final ISchedulingStrategy defaultStrategy;

	public Engine(ISchedulingStrategy strategy) {
		this.defaultStrategy = strategy;
	}

	@SuppressWarnings("unchecked")
	public <T extends IGrade<T>> DumbReturnValue checkpoint(IAcceptor acceptor, IGrade<T> grade) {
		T previousGrade = (T) acceptors.get(acceptor);
		assert previousGrade == null || previousGrade.compareTo((T) grade) < 0;
		acceptors.put(acceptor, grade);
		return new DumbReturnValue();
	}

	public void schedule(IGoal goal) {
		schedule(goal, defaultStrategy);
	}

	public void schedule(IGoal goal, ISchedulingStrategy strategy) {
		int priority = strategy.getPriority(goal);
		queue.add(new PrioritizedGoal(goal, priority));
	}

	public void schedule(Collection<IGoal> goals, ISchedulingStrategy strategy) {
		for (IGoal goal : goals) {
			schedule(goal, strategy);
		}
	}

	public void run() {
		if (queue.isEmpty())
			return;
		while (!queue.isEmpty()) {
			passGoals();
			passCheckpoints();
		}
	}

	/**
	 * FIXME: change queue into HashMap
	 */
	private void passGoals() {
		if (queue.isEmpty())
			return;
		LinkedList<PrioritizedGoal> generation = new LinkedList<PrioritizedGoal>();
		int currentPriority = queue.peek().priority;
		while (!queue.isEmpty()) {
			PrioritizedGoal peek = queue.peek();
			if (peek.priority != currentPriority) break;
			generation.add(queue.poll());
		}
		for (PrioritizedGoal goal : generation) {
			goal.goal.preRun();
		}
	}

	private void passCheckpoints() {
		Map<IAcceptor, IGrade<?>> generation = acceptors;
		acceptors = new HashMap<IAcceptor, IGrade<?>>();
		for (IAcceptor acceptor : generation.keySet()) {
			IGrade<?> grade = generation.get(acceptor);
			acceptor.checkpoint(grade);
		}
	}

	private class PrioritizedGoal implements Comparable<PrioritizedGoal> {
		IGoal goal;
		Integer priority;

		public PrioritizedGoal(IGoal goal, Integer priority) {
			this.goal = goal;
			this.priority = priority;
		}

		public int compareTo(PrioritizedGoal o) {
			return priority.compareTo(o.priority);
		}
	}

}
