package com.yoursway.sadr.succeeder.tests.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
		
		AssertingAcceptor(){
			actualResult = -1;
		}
		
		void setResult(int actualResult) {
			this.actualResult = actualResult;
		}

		public void checkpoint(Grade grade) {
			assertEquals(1, actualResult);
		}

		public <T> void checkpoint(IGrade<T> grade) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Test
	public void singleGoal() {
		Engine scheduler = new Engine(new DefaultStrategy());
		Goal goal = new Goal(){
			private int result = 0;
			private AssertingAcceptor acceptor;

			public void preRun() {
				result = 1;
				acceptor = new AssertingAcceptor();
				acceptor.setResult(result);
				updateGrade(acceptor, Grade.DONE);
			}

            @Override
            protected String describe() {
                return "";
            }
			
		};
		scheduler.run(goal);
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
			updateGrade(new IAcceptor(){
				
				public <T> void checkpoint(IGrade<T> grade) {
					if (result < SequentialGoal.MAX_DEPTH) {
						schedule(new SequentialGoal(depth+1));
					}
					else
						assertEquals(MAX_DEPTH, depth);
				}
	
			}, Grade.INTERMEDIATE);
		}

        @Override
        protected String describe() {
            return "";
        }
	}
	
	@Test
	public void sequentialGoals() {
		Engine scheduler = new Engine(new DefaultStrategy());
		scheduler.run(new SequentialGoal(0));
	}
	
}
