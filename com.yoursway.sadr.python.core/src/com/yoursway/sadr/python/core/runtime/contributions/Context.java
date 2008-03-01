package com.yoursway.sadr.python.core.runtime.contributions;

import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.python.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public interface Context {
    
    RubyRuntimeModel model();
    
    ISourceModule file();
    
    FileScope fileScope();
    
    void add(ContributableItem item);
    
}