/**
 * 
 */
package com.yoursway.sadr.python_v2.croco;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.model.ContextImpl;

final class EmptyKrocodile extends Krocodile {
    @Override
    public RuntimeObject getActualArgument(String name) {
        return null;
    }
    
    @Override
    public Set<String> keys() {
        return new HashSet<String>();
    }
    
    @Override
    public void put(String name, RuntimeObject value) {
        
    }
    
    @Override
    public ContextImpl getContext(Scope scope) {
        return null;
    }
    
    @Override
    public String toString() {
        return "Empty";
    }
}