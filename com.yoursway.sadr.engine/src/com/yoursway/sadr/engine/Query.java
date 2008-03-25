package com.yoursway.sadr.engine;

/**
 * Queries are wrappers for {@link Goal} objects. It is not supposed to be
 * implemented by users.
 * 
 * @see Implementations are used in {@link AnalysisEngine} to realize the
 *      calling conventions of {@link Goal}s and {@link Continuation}s.
 */
public abstract class Query {
    
    public abstract void evaluate();
    
    public abstract Goal goal();
    
    public abstract void recursive();
    
    public abstract Query parent();
    
}