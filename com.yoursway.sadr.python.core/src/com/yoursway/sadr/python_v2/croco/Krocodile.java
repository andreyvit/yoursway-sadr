package com.yoursway.sadr.python_v2.croco;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.model.ContextImpl;

public class Krocodile {
    
    private final Krocodile parent;
    
    private final PythonConstruct construct;
    
    private final ContextImpl context;
    
    private Krocodile() {
        this.parent = null;
        this.construct = null;
        this.context = null;
    }
    
    public Krocodile(Krocodile parent, PythonConstruct construct, ContextImpl context) {
        this.parent = parent;
        this.construct = construct;
        this.context = context;
    }
    
    public Krocodile(Krocodile parent, PythonConstruct construct) {
        this.parent = parent;
        this.construct = construct;
        this.context = null;
    }
    
    public PythonConstruct construct() {
        return construct;
    }
    
    public RuntimeObject getActualArgument(String name) {
        if (context == null && parent != null)
            return parent.getActualArgument(name);
        return context.getActualArgument(name);
    }
    
    public void getMatchingArguments(Frog name, PythonVariableAcceptor acceptor) {
        if (context == null && parent != null) {
            parent.getMatchingArguments(name, acceptor);
        } else {
            context.findMatchingArguments(name, acceptor);
        }
    }
    
    public static final Krocodile EMPTY = new Krocodile() {
        
        @Override
        public RuntimeObject getActualArgument(String name) {
            return null;
        }
        
        @Override
        public void getMatchingArguments(Frog name, PythonVariableAcceptor va) {
        }
        
        @Override
        public Set<String> keys() {
            return new HashSet<String>();
        }
        
        @Override
        public void put(String name, RuntimeObject value) {
            
        }
        
        @Override
        public String toString() {
            return "Empty context";
        }
    };
    
    public Set<String> keys() {
        if (context == null && parent != null)
            return parent.keys();
        return context.keys();
    }
    
    public void put(String name, RuntimeObject value) {
        if (context == null && parent != null) {
            parent.put(name, value);
        } else {
            context.put(name, value);
        }
    }
}
