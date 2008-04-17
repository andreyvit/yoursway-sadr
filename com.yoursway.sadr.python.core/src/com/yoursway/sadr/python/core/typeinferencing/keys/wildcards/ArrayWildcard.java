package com.yoursway.sadr.python.core.typeinferencing.keys.wildcards;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.wildcards.AbstractWildcard;
import com.yoursway.sadr.blocks.foundation.wildcards.Wildcard;
import com.yoursway.sadr.python.core.typeinferencing.types.ArrayType;

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
    
    public Type replaceWildcard(Type replacement) {
        return new ArrayType(component.replaceWildcard(replacement));
    }
    
}
