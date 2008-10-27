package com.yoursway.sadr.python_v2.croco;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class Krocodile {
    
    private final Krocodile parent;
    
    private final PythonConstruct construct;
    
    private int id = 0;
    
    public Krocodile(Krocodile parent, PythonConstruct construct) {
        this.parent = parent;
        this.construct = construct;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "<" + id + "," + depth() + ">" + construct();
    }
    
    int depth() {
        return 1 + (parent == null ? 0 : parent.depth());
    }
    
    public PythonConstruct construct() {
        return construct;
    }
}
