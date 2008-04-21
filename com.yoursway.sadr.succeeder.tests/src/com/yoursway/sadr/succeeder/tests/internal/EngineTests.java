package com.yoursway.sadr.succeeder.tests.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.Engine;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;
import com.yoursway.sadr.succeeder.ISchedulingStrategy;


public class EngineTests {

	static enum Grade implements IGrade<Grade> {

		INTERMEDIATE {

			public boolean isDone() {
				return false;
			}

		},

		DONE {

			public boolean isDone() {
				return true;
			}

		};

	}

	private static class DefaultStrategy implements ISchedulingStrategy {
		public int getPriority(IGoal goal) {
			return 0;
		}

		public boolean prune(IGoal goal) {
			return false;
		}
	}
	
	private static class AssertingAcceptor implements IAcceptor {
		private int actualResult;
		
		AssertingAcceptor(int actualResult) {
			this.actualResult = actualResult;
		}

		public void checkpoint(IGrade<?> grade) {
			assertEquals(1, actualResult);
		}
		
	}
	
	@Test
	public void singleGoal() {
		Engine scheduler = new Engine(new DefaultStrategy());
		scheduler.schedule(new Goal(){
			private int result = 0;
			private IAcceptor acceptor;

			public void preRun() {
				result = 1;
				acceptor = new AssertingAcceptor(result);
				checkpoint(acceptor, Grade.DONE);
			}

			public CheckpointToken flush() {
				return checkpoint(acceptor, Grade.DONE);
			}
			
		});
		scheduler.run();
	}
	
	private static class SequentialGoal extends Goal{
		public static final int MAX_DEPTH = 3; 
		private int result = 0;
		private final int depth;
		private IAcceptor acceptor;
		public SequentialGoal(int depth) {
			this.depth = depth; 
		}
		
		public void preRun() {
			result = depth;
			checkpoint(new IAcceptor(){
				
				public void checkpoint(IGrade<?> grade) {
					if (result < SequentialGoal.MAX_DEPTH) {
						schedule(new SequentialGoal(depth+1));
					}
					else
						assertEquals(MAX_DEPTH, depth);
				}
	
			}, Grade.INTERMEDIATE);
		}
	
		public CheckpointToken flush() {
			return checkpoint(acceptor, Grade.DONE);
		}
		
	}
	
	@Test
	public void sequentialGoals() {
		Engine scheduler = new Engine(new DefaultStrategy());
		scheduler.schedule(new SequentialGoal(0));
		scheduler.run();
	}
	
}
