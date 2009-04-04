package com.yoursway.sadr.python.analysis.context.lexical.scopes;

import static com.google.common.collect.Lists.newArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class PythonScope {
    
    private final Set<String> globalVariables = new HashSet<String>();
    
    private final Set<String> localVariables = new HashSet<String>();
    
    public abstract PythonScope parentScope();
    
    //    
    //    public abstract MethodDeclarationC getParentMethodDeclarationC();
    
    public abstract ModuleScope getFileScope();
    
    public final List<PythonScope> currentScopesIncludingSelf() {
        final List<PythonScope> scopes = newArrayList();
        for (PythonScope scope = this; scope != null; scope = scope.parentScope())
            scopes.add(scope);
        return scopes;
    }
    
    public final void addGlobalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        globalVariables.add(name);
    }
    
    public final boolean isGlobalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        return isGlobalScope() || globalVariables.contains(name);
    }
    
    public abstract boolean isGlobalScope();
    
    public final void addLocalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        localVariables.add(name);
    }
    
    public final boolean isLocalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        return localVariables.contains(name);
    }
    
    public final PythonScope findDefiningScope(String name) {
        if (isGlobalVariable(name))
            return getFileScope();
        for (PythonScope scope = this; scope != null; scope = scope.parentScope())
            if (scope.isLocalVariable(name))
                return scope;
        return getFileScope();
    }
    
}
