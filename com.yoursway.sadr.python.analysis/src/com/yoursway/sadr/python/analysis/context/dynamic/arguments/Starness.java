package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

public enum Starness {
    
    REGULAR,

    STAR,

    DOUBLE_STAR;
    
    public static Starness fromCount(int stars) {
        return Starness.values()[stars];
    }
    
}
