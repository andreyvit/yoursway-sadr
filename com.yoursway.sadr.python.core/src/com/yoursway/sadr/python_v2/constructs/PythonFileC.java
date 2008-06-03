package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.python.core.runtime.ProjectRuntime;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class PythonFileC extends PythonScopeImpl<ModuleDeclaration> {
    
    private final String moduleName;
    private final ProjectRuntime projectRuntime;
    
    public PythonFileC(Scope sc, ModuleDeclaration node, String name, ProjectRuntime projectRuntime) {
        super(sc, node);
        this.moduleName = name;
        this.projectRuntime = projectRuntime;
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        setPostChildren(PythonConstructFactory.wrap(this.node.getStatements(), this));
    }
    
    public ProjectRuntime getProjectRuntime() {
        return projectRuntime;
    }
    
    public String displayName() {
        return "Module " + this.moduleName;
    }
    
    @Override
    public PythonFileC getFileScope() {
        return this;
    }
    
    @Override
    public String name() {
        return this.moduleName;
    }
}
