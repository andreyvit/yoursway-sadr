package com.yoursway.sadr.succeeder;

/**
 * The hidden agenda of introducing this class is to make SADR users call
 * {@link IScheduler#updateGrade(IAcceptor, IGrade)} method on
 * {@link IGoal#flush()}.
 */

public final class CheckpointToken {
	private CheckpointToken() {
	}
	private static CheckpointToken token = new CheckpointToken();
	static CheckpointToken instance() {
		return token;
	}
}
