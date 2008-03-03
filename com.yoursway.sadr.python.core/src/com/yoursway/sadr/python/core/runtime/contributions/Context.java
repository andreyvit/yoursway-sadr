package com.yoursway.sadr.python.core.runtime.contributions;

import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public interface Context {
    
    PythonRuntimeModel model();
    
    ISourceModule file();
    
    FileScope fileScope();
    
    void add(ContributableItem item);
    
}