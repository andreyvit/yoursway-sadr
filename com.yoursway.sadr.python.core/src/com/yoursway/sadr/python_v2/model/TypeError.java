package com.yoursway.sadr.python_v2.model;

import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;

public class TypeError extends PythonException {
    public TypeError(String message) {
        super(message);
    }
    
    private static final long serialVersionUID = 6462911252446656994L;
    
}
