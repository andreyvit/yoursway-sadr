package com.yoursway.sadr.engine;

/**
 * @author buriy
 * 
 * Real objects to schedule
 * 
 * Parent is required for Backward Propagation
 */
public abstract class Query {
    
    public abstract void evaluate();
    
    public abstract Goal goal();
    
    public abstract void recursive();
    
    public abstract Query parent();
    
}