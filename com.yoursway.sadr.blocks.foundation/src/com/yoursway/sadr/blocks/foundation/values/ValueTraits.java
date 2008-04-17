package com.yoursway.sadr.blocks.foundation.values;

/**
 * @author buriy
 * 
 * This was quick and dirty hack. To be removed and replaced with real type
 * determination things.
 * 
 */
public interface ValueTraits {
    
    boolean isInteger();
    
    long integerValue();
    
    boolean cohersibleToString();
    
    String coherseToString();
    
    boolean cohersibleToBoolean();
    
    boolean coherseToBoolean();
    
    public String toString();
}
