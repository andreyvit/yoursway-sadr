package com.yoursway.sadr.blocks.foundation.wildcards;

import com.yoursway.sadr.blocks.foundation.types.Type;

public class StarWildcard extends AbstractWildcard {
    
    public static final Wildcard INSTANCE = new StarWildcard();
    
    private StarWildcard() {
    }
    
    @Override
    public String toString() {
        return "*";
    }
    
    public Type replaceWildcard(Type replacement) {
        return replacement;
    }
    
}
