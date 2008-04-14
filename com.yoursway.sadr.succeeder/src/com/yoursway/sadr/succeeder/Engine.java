package com.yoursway.sadr.succeeder;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;


public class Engine implements IGoalScheduler {
	
	private Map<IGoalResultAcceptor, IGrade> acceptors;
	private final ISchedulingStrategy defaultStrategy;
	private PriorityQueue<PrioritizedGoal> queue;

	public Engine(ISchedulingStrategy strategy){
		this.defaultStrategy = strategy;
		this.queue = new PriorityQueue<PrioritizedGoal>();
	}
	
	@Override
	public DumbReturnValue checkpoint(IGoalResultAcceptor acceptor, IGrade grade) {
		IGrade previousGrade = acceptors.get(acceptor);
		assert previousGrade == null || previousGrade.compareTo(grade)<0;
		acceptors.put(acceptor, grade);
		return new DumbReturnValue();
	}

	@Override
	public void schedule(IGoal goal) {
		schedule(goal, defaultStrategy);
	}

	@Override
	public void schedule(IGoal goal, ISchedulingStrategy strategy) {
		int priority = strategy.getPriority(goal);
		queue.add(new PrioritizedGoal(goal, priority));
	}

	@Override
	public void schedule(Collection<IGoal> goals, ISchedulingStrategy strategy) {
		for (IGoal goal : goals) {
			schedule(goal, strategy);
		}
	}
	
	public void run(){
		if(queue.isEmpty())
			return;
		int currentPriority = queue.peek().priority;
		while(!queue.isEmpty()){
			PrioritizedGoal peek = queue.peek();
			if(peek.priority < currentPriority){
				passCheckpoints();
			}
			if(peek.priority != currentPriority){
				currentPriority = peek.priority;
			}
			PrioritizedGoal poll = queue.poll();
			poll.goal.preRun();
		}
	}
	
	private void passCheckpoints() {
		for (IGoalResultAcceptor acceptor : acceptors.keySet()) {
			IGrade grade = acceptors.get(acceptor);
			acceptor.checkpoint(grade);
		}
		
	}

	private class PrioritizedGoal implements Comparable<PrioritizedGoal>{
		IGoal goal;
		Integer priority;
		
		public PrioritizedGoal(IGoal goal, Integer priority) {
			this.goal = goal;
			this.priority = priority;
		}
		
		@Override
		public int compareTo(PrioritizedGoal o) {
			return priority.compareTo(o.priority);
		}
	}

}
