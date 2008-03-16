package com.yoursway.sadr.python.core.typeinferencing.keys.wildcards;

public class FieldWildcard extends AbstractWildcard {
    
    private final String name;
    private final Wildcard receiver;
    
    public FieldWildcard(String name, Wildcard receiver) {
        this.name = name;
        this.receiver = receiver;
    }
    
    @Override
    public String toString() {
        return receiver + " ." + name;
    }
    
    public String name() {
        return name;
    }
    
    public Wildcard receiver() {
        return receiver;
    }
    
}
