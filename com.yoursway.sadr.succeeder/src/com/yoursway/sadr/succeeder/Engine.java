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
	private final Map<IGoal, ISchedulingStrategy> goalToStrategy = new HashMap<IGoal, ISchedulingStrategy>();

	public Engine(ISchedulingStrategy strategy) {
		this.defaultStrategy = strategy;
	}

	@SuppressWarnings("unchecked")
	public <T extends IGrade<T>> CheckpointToken checkpoint(IAcceptor acceptor, IGrade<T> grade) {
		T previousGrade = (T) acceptors.get(acceptor);
		if(previousGrade == null || previousGrade.compareTo((T) grade) < 0){
			throw new IllegalArgumentException("Grade should increase for "+acceptor.getClass().getSimpleName());
		}
		acceptors.put(acceptor, grade);
		return CheckpointToken.instance();
	}

	public void schedule(IGoal goal) {
		schedule(goal, defaultStrategy);
	}

	public void schedule(IGoal goal, ISchedulingStrategy strategy) {
		int priority = strategy.getPriority(goal);
		goalToStrategy.put(goal, strategy);
		queue.add(new PrioritizedGoal(goal, priority));
	}

	public void schedule(Collection<IGoal> goals, ISchedulingStrategy strategy) {
		for (IGoal goal : goals) {
			schedule(goal, strategy);
		}
	}

	public void run() {
		while (!queue.isEmpty()) {
			passGoals();
			passCheckpoints();
		}
	}

	/**
	 * Precondition: !queue.isEmpty()
	 * FIXME: change queue into HashMap
	 */
	private void passGoals() {
		LinkedList<PrioritizedGoal> generation = new LinkedList<PrioritizedGoal>();
		int currentPriority = queue.peek().priority;
		while (!queue.isEmpty()) {
			PrioritizedGoal peek = queue.peek();
			if (peek.priority != currentPriority) break;
			generation.add(queue.poll());
		}
		for (PrioritizedGoal pGoal : generation) {
			IGoal goal = pGoal.goal;
			goal.setScheduler(this);
			goal.preRun();
			ISchedulingStrategy strategy = goalToStrategy.remove(goal);
			if (strategy.prune(goal)) {
				goal.flush();
			}
			
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
