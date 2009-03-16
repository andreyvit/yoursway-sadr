/**
 * 
 */
package com.yoursway.sadr.python_v2.croco;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.objects.ContextImpl;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

final class EmptyKrocodile extends PythonDynamicContext {
    @Override
    public PythonValue getActualArgument(String name) {
        return null;
    }
    
    @Override
    public Set<String> keys() {
        return new HashSet<String>();
    }
    
    @Override
    public void put(String name, PythonValue value) {
        
    }
    
    @Override
    public ContextImpl getContext(PythonStaticContext staticContext) {
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