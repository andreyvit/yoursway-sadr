package com.yoursway.sadr.python.core.typeinferencing.keys.wildcards;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.wildcards.AbstractWildcard;
import com.yoursway.sadr.blocks.foundation.wildcards.Wildcard;

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
    
    public Type replaceWildcard(Type replacement) {
        // TODO handle the field (?)
        return receiver.replaceWildcard(replacement);
    }
    
}
