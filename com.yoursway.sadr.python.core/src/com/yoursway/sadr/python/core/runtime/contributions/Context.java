package com.yoursway.sadr.python.core.runtime.contributions;

import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;

//FIXME make real context or settle crocodiles here. 
public interface Context {
    
    PythonRuntimeModel model();
    
    ISourceModule file();
    
    FileScope fileScope();
    
    void add(ContributableItem item);
    
}