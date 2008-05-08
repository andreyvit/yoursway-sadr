package com.yoursway.sadr.succeeder.tests.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.Engine;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;
import com.yoursway.sadr.succeeder.TimeLimitBasedSchedulingStrategy;
import com.yoursway.sadr.succeeder.tests.internal.EngineTests.Grade;

public class DefaultSchedulingStrategiesTests {
	
	private static class AssertingAcceptor implements IAcceptor {
		private int actualResult;
		
		AssertingAcceptor(int actualResult) {
			this.actualResult = actualResult;
		}
		
		public void setResult(int actualResult) {
			this.actualResult = actualResult;
		}

		public <T> void checkpoint(IGrade<T> grade) {
			assertEquals(1, actualResult);
		}
	}
	
	private static class SleepingGoal extends Goal{
		
		AssertingAcceptor acceptor = new AssertingAcceptor(33);

		public CheckpointToken flush() {
			return checkpoint(acceptor, Grade.DONE);
		}

		public void preRun() {
				acceptor.setResult(1);
				checkpoint(acceptor, Grade.INTERMEDIATE);
				try { Thread.sleep(2000); } 
				catch (InterruptedException e) { e.printStackTrace(); }
				schedule(new Goal(){
					public CheckpointToken flush() {
						return checkpoint(acceptor, Grade.INTERMEDIATE);
					}
					public void preRun() {
							acceptor.setResult(0);
							checkpoint(acceptor, Grade.DONE);
					}
				});
		}
		
	}
	
	@Test
	public void testTimeLimitPruning() {
		Engine engine = new Engine(new TimeLimitBasedSchedulingStrategy(500));
		engine.run(new SleepingGoal());
	}
}
