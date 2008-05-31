package com.yoursway.sadr.python_v2.model;

import java.util.HashSet;
import java.util.Set;

//FIXME make real context or settle crocodiles here. 
/**
 * Encapsulates actual arguments.
 */
public interface Context {
    RuntimeObject getActualArgument(String name);
    
    boolean contains(String name);
    
    static final Context EMPTY_CONTEXT = new Context() {
        
        public boolean contains(String name) {
            return false;
        }
        
        public RuntimeObject getActualArgument(String name) {
            return null;
        }
        
        public Set<String> keys() {
            return new HashSet<String>();
        }
        
        public void put(String name, RuntimeObject value) {
            
        }
        
        @Override
        public String toString() {
            return "Empty context";
        }
    };
    
    Set<String> keys();
    
    void put(String name, RuntimeObject value);
}