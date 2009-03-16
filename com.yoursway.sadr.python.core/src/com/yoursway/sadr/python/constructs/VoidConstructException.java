package com.yoursway.sadr.python.constructs;

import com.yoursway.utils.Failure;

public class VoidConstructException extends Failure {
    
    private static final long serialVersionUID = 1L;
    
    public VoidConstructException(PythonConstruct construct) {
        add("construct_class", construct.getClass().getName());
        add("construct", construct.toString());
    }
    
}
