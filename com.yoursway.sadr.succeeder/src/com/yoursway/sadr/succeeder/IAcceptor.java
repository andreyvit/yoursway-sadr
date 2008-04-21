package com.yoursway.sadr.succeeder;

/**
 * Keeps partial results from the subgoal and passes them to the parent goal
 * when <code>checkpoint</code> is called by the engine.
 * 
 * If subgoal checkpoints several times with the same acceptor, scheduler is
 * free to squeeze several checkpoints into one.
 */
public interface IAcceptor {

	/**
	 * Engine calls checkpoint on acceptor when it is safe to pass results to
	 * the parent.
	 * 
	 * @param grade
	 *            grade passed by the subgoal
	 */
	void checkpoint(IGrade<?> grade);
}
