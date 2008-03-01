package com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards;

public class StarWildcard extends AbstractWildcard {
    
    public static final Wildcard INSTANCE = new StarWildcard();
    
    private StarWildcard() {
    }
    
    @Override
    public String toString() {
        return "*";
    }
    
}
