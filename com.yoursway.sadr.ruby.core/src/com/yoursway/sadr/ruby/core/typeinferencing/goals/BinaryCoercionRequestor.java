package com.yoursway.sadr.ruby.core.typeinferencing.goals;

public interface BinaryCoercionRequestor {
    
    void intType();
    
    void stringType();
    
    void ints(long a, long b);
    
    void strings(String a, String b);
    
    void unknowns();
    
}
