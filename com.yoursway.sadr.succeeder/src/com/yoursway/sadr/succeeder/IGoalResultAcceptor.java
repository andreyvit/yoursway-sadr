package com.yoursway.sadr.succeeder;

/**
 * Goal can produce multiple results. Those results are passed to the parent
 * goal through this interface.
 */
public interface IGoalResultAcceptor<ResultT> {
	void resultProduced(ResultT result);
}
