package com.yoursway.sadr.ruby.core.typeinferencing.values;

public interface ValueTraits {
    
    boolean isInteger();
    
    long integerValue();
    
    boolean cohersibleToString();
    
    String coherseToString();
    
    boolean cohersibleToBoolean();
    
    boolean coherseToBoolean();
    
}
