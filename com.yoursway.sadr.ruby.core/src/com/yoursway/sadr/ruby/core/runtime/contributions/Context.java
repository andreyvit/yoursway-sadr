package com.yoursway.sadr.ruby.core.runtime.contributions;

import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.ruby.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;

public interface Context {
    
    RubyRuntimeModel model();
    
    ISourceModule file();
    
    FileScope fileScope();
    
    void add(ContributableItem item);
    
}