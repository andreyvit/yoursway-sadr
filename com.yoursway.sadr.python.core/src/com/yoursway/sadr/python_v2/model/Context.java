package com.yoursway.sadr.python_v2.model;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

//FIXME make real context or settle crocodiles here. 
/**
 * Encapsulates actual arguments.
 */
public interface Context {
    PythonObject getActualArgument(String name);
    
    public void getMatchingArguments(Frog name, PythonVariableAcceptor va);
    
    static final Context EMPTY = new Context() {
        
        public PythonObject getActualArgument(String name) {
            return null;
        }
        
        public void getMatchingArguments(Frog name, PythonVariableAcceptor va) {
        }
        
        public Set<String> keys() {
            return new HashSet<String>();
        }
        
        public void put(String name, PythonObject value) {
            
        }
        
        @Override
        public String toString() {
            return "Empty context";
        }
    };
    
    Set<String> keys();
    
    void put(String name, PythonObject value);
}