package com.yoursway.sadr.ruby.core.staticchecks;

import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;

public interface IStaticCheck {
    
    void check(ISourceModule module, IRubyProblemReporter reporter);
    
    void init(WholeProjectRuntime runtime);
    
}
