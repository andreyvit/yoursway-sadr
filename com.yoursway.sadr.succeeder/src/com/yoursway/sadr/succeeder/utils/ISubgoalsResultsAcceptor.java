/**
 * 
 */
package com.yoursway.sadr.succeeder.utils;

import java.util.List;

/**
 *
 */
public interface ISubgoalsResultsAcceptor<ResultT> {
	public void subtasksFinished(List<ResultT> results);
}
