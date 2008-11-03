package com.yoursway.sadr.python_v2.model;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.croco.Frog;

//FIXME make real context or settle crocodiles here. 
/**
 * Encapsulates actual arguments.
 */
public interface Context {
    RuntimeObject getActualArgument(String name);
    
    public void getMatchingArguments(Frog name, PythonVariableAcceptor va);
    
    static final Context EMPTY = new Context() {
        
        public RuntimeObject getActualArgument(String name) {
            return null;
        }
        
        public void getMatchingArguments(Frog name, PythonVariableAcceptor va) {
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