package com.yoursway.sadr.succeeder.tests.internal;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.yoursway.sadr.succeeder.Engine;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;
import com.yoursway.sadr.succeeder.ISchedulingStrategy;

public class OrderedGoalTest extends TestCase {

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

	class Acceptor implements IAcceptor {
		int number = InstanceNumerator.generateNumber(this.getClass());
		private List<Integer> results = newArrayList();

		@Override
		public String toString() {
			return "I am acceptor number " + number;
		}

		public Acceptor() {

		}

		public <T> void checkpoint(IGrade<T> grade) {
			assertEquals(results.size(), 2);
			assertEquals(42, results.get(0).intValue());
			assertEquals(42, results.get(1).intValue());
		}

		public void resultProduced(int ultimateAnswer) {
			results.add(ultimateAnswer);
		}

	}

	private final class GoalImpl extends Goal {

		private Acceptor resultAcceptor;

		public GoalImpl(Acceptor acceptor) {
			resultAcceptor = acceptor;
		}

		public void preRun() {
			resultAcceptor.resultProduced(42);
		}

        @Override
		public String describe() {
            return "";
        }

	}

	@Test
	public void testOrderedGoal() {
		Engine engine = new Engine(new DefaultStrategy());
		final Acceptor acceptor = new Acceptor();
		engine.run(new Goal(){
			public void preRun() {
				schedule(new GoalImpl(acceptor));
				schedule(new GoalImpl(acceptor));
			}

            @Override
			public String describe() {
                return "";
            }
		});
	}
}
