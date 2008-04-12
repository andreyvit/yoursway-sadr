package com.yoursway.sadr.succeeder.tests;
import junit.framework.TestCase;

import org.junit.Test;

import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGoalResultAcceptor;
import com.yoursway.sadr.succeeder.IGoalScheduler;
import com.yoursway.sadr.succeeder.IGoalStateListener;
import com.yoursway.sadr.succeeder.engine.RecursingGoalEngine;

public class EmptyGoalTest extends TestCase {
	private boolean wasInPreRun = false;
	private boolean wasInPostRun = false;
	
	class EmptyGoal implements IGoal<String> {
		private IGoalResultAcceptor<String> resultAcceptor;

		public void isCancelled() {
			assertTrue("isCancelled must not be called", false);
		}

		public void postRun() {
			assertTrue("postRun must be called before preRun", wasInPreRun);
			assertFalse("postRun must not be called twice", wasInPostRun);
			wasInPostRun = true;
			
			resultAcceptor.resultProduced("result");
		}

		public void preRun() {
			assertFalse("postRun must not be called before preRun", wasInPostRun);
			assertFalse("preRun must not be called twice", wasInPreRun);
			wasInPreRun = true;
		}

		public void setGoalContext(IGoalScheduler scheduler,
				IGoalResultAcceptor<String> resultAcceptor) {
			assertNotNull(scheduler);
			assertNotNull(resultAcceptor);
			assertNull("setGoalContext must not be called twice", this.resultAcceptor);
			this.resultAcceptor = resultAcceptor;
		}
	}
	
	@Test
	public void testEmptyGoal() {
		final IGoal<String> myGoal = new EmptyGoal();

		final boolean[] startedIsCalled = { false };
		final boolean[] resultProducedIsCalled = { false }; 
		final boolean[] finishedIsCalled = { false };

		RecursingGoalEngine<String> rge = new RecursingGoalEngine<String>(myGoal, new IGoalStateListener<String>() {

			public void canceled(IGoal<String> goal) {
				assertTrue("canceled must not be called", false);
			}

			public void started(IGoal<String> goal) {
				assertFalse("started must not be called twice", startedIsCalled[0]);
				assertFalse("resultProduced must not be called after finished", resultProducedIsCalled[0]);
				assertFalse("started must not be called after finished", finishedIsCalled[0]);
				startedIsCalled[0] = true;
			}

			public void resultProduced(IGoal<String> goal, String result) {
				assertTrue("resultProduced must be called after started", startedIsCalled[0]);
				assertFalse("resultProduced must not be called twice", resultProducedIsCalled[0]);
				assertFalse("resultProduced must not be called after finished", finishedIsCalled[0]);
				resultProducedIsCalled[0] = true;
				
				assertEquals(goal, myGoal);
				assertEquals(result, "result");
			}

			public void finished(IGoal<String> goal) {
				assertTrue("finished must be called after started", startedIsCalled[0]);
				assertTrue("finished must be called after resultProduced", resultProducedIsCalled[0]);
				assertFalse("finished must not be called twice", finishedIsCalled[0]);
				finishedIsCalled[0] = true;
			}
		});
		
		rge.run();
	}
}
