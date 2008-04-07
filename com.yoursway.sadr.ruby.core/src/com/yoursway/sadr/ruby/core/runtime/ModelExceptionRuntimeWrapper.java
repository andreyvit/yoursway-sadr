package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.core.ModelException;

public class ModelExceptionRuntimeWrapper extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public ModelExceptionRuntimeWrapper(ModelException cause) {
        super(cause);
    }
    
    public ModelException unwrap() {
        return (ModelException) getCause();
    }
    
}
