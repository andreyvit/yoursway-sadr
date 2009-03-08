/**
 * 
 */
package com.yoursway.sadr.python_v2.croco;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.python_v2.constructs.Scope;
import com.yoursway.sadr.python_v2.model.ContextImpl;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

final class EmptyKrocodile extends Krocodile {
    @Override
    public PythonObject getActualArgument(String name) {
        return null;
    }
    
    @Override
    public Set<String> keys() {
        return new HashSet<String>();
    }
    
    @Override
    public void put(String name, PythonObject value) {
        
    }
    
    @Override
    public ContextImpl getContext(Scope scope) {
        return null;
    }
    
    @Override
    public String toString() {
        return "Empty";
    }
    
    @Override
    public int size() {
        return 0;
    }
}