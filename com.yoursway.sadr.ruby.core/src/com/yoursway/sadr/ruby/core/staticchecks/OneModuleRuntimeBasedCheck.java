package com.yoursway.sadr.ruby.core.staticchecks;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;

public abstract class OneModuleRuntimeBasedCheck implements IStaticCheck {
    
    protected WholeProjectRuntime runtime;
    
    protected ModuleDeclaration currentModuleDeclaration;
    
    protected FileScope currentFileScope;
    
    protected void init(ISourceModule module) {
        currentModuleDeclaration = runtime.getASTFor(module);
        currentFileScope = runtime.getScopeFor(module);
    }
    
    public void init(WholeProjectRuntime runtime) {
        this.runtime = runtime;
    }
    
}
