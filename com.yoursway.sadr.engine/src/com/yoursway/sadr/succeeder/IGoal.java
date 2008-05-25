package com.yoursway.sadr.succeeder;

/**
 * A goal that yields a result of type ResultT and may schedule subgoals.
 * <p>
 * Goals communicate through IGoalResultListeners: when goal schedules subgoal,
 * goal must provide implementation of IGoalResultListener, which will receive
 * all results produced by the goal. Subgoal will receive IGoalResultListener
 * just before execution.
 * <p>
 * No provision is made that subgoal's Listener is the same listener that passed
 * by the parent goal - engine may delay results passing (while still
 * maintaining the axioms described below), and may use proxy listeners et al.
 * On practice, that means that subgoal MUST NOT try to coerce passed listener
 * to the another type - this will probably fail, or will start to fail when
 * implementation of scheduler changes.
 * <p>
 * Goals are invoked by the engine in a predictable way. Any scheduler will
 * maintain the following axioms:
 * <nl>
 * <li>No method is called from the different threads concurrently. Methods may
 * be called from different threads, though.</li>
 * 
 * <li><code>setGoalContenxt</code> is called before <code>preRun</code>.</li>
 * 
 * <li><code>goalStarted</code> is reported to the parent goal before calling
 * <code>preRun</code>.</li>
 * 
 * <li><code>resultProduced</code> is reported to the parent for every result
 * produced by the goal, in the order.</li>
 * 
 * <li>Nothing is guaranteed about the ordering of <code>resultProduced</code>
 * of different goals.</li>
 * 
 * <li><code>postRun</code> is called after <code>preRun</code>.</li>
 * 
 * <li><code>postRun</code> is called after all subgoals reported
 * <code>finished</code> or <code>canceled</code>.</li>
 * 
 * <li>After <code>postRun</code> is returned, <code>goalFinished</code> is
 * reported to the parent goal.</li>
 * </nl>
 * <p>
 * Simply saying, every subgoal is fully finished before <code>postRun</code>
 * of parent is called, but subgoals may run in any order and even concurrently.
 * 
 * Goal can produce multiple results sequentially. Each next result must have
 * higher grade than a previous.
 */
public interface IGoal {
    /**
     * Called once before preRun().
     * 
     * Cannot produce results. Cannot schedule subtasks.
     * 
     * @param scheduler
     *            Scheduler to be used to schedule subgoals.
     * @param resultAcceptor
     *            Acceptor, accepting result(s) of goal. Can schedule subtasks
     *            when called.
     */
    void setScheduler(IScheduler scheduler);
    
    /**
     * Called first when task is run.
     * 
     * Can produce results. Can schedule subtasks.
     */
    void preRun();
    
    /**
     * This method is called on the goal cancellation.
     * 
     * @return a {@link CheckpointToken} to assure that
     *         {@link IAcceptor#checkpoint(IGrade)} has been called.
     */
    CheckpointToken flush();
    
    String describe();
}