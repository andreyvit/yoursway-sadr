package com.yoursway.sadr.python_v2.model;

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
    };
}