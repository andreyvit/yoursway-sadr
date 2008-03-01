package com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards;

public class ArrayWildcard extends AbstractWildcard {
    
    private final Wildcard component;
    
    public ArrayWildcard(Wildcard component) {
        this.component = component;
    }
    
    @Override
    public String toString() {
        return component.toString() + "[]";
    }
    
    public Wildcard component() {
        return component;
    }
    
}
