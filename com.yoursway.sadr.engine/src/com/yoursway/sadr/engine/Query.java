package com.yoursway.sadr.engine;

public abstract class Query {
    
    public abstract void evaluate();
    
    public abstract Goal goal();
    
    public abstract void recursive();
    
    public abstract Query parent();
    
}