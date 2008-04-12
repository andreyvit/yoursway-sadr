package com.yoursway.sadr.succeeder.tests;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGoalResultAcceptor;
import com.yoursway.sadr.succeeder.IGoalScheduler;
import com.yoursway.sadr.succeeder.engine.RecursingGoalEngine;
import com.yoursway.sadr.succeeder.utils.SimpleGoal;

public class SimpleGoalTest extends TestCase {
	
	private final class IGoalImplementation implements IGoal<Integer> {
		private IGoalResultAcceptor<Integer> resultAcceptor;

		public void isCancelled() {}

		public void postRun() { resultAcceptor.resultProduced(42); }

		public void preRun() {}

		public void setGoalContext(IGoalScheduler scheduler,
				IGoalResultAcceptor<Integer> resultAcceptor) {
			this.resultAcceptor = resultAcceptor;
		}
	}

	@Test
	public void testSimpleGoal() {
		
		IGoal<String> runnerGoal = new SimpleGoal<String, Integer>() {

			public void preRun() {
				scheduler.schedule(new IGoalImplementation());
				scheduler.schedule(new IGoalImplementation());
			}

			public void subtasksFinished(List<Integer> results) {
				assertEquals(results.size(), 2);
				assertEquals(42, results.get(0).intValue());
				assertEquals(42, results.get(1).intValue());
				
				resultAcceptor.resultProduced("something");
			}
		};
		
		RecursingGoalEngine<String> engine = new RecursingGoalEngine<String>(runnerGoal, RecursingGoalEngine.NULL_ACCEPTOR);
		engine.run();
	}
}
