package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class PythonFileC extends PythonScopeImpl<ModuleDeclaration> {
    
    private final String moduleName;
    
    public PythonFileC(Scope sc, ModuleDeclaration node, String name) {
        super(sc, node);
        this.moduleName = name;
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        setChildConstructs(PythonConstructFactory.wrap(this.node.getStatements(), this));
    }
    
    @Override
    protected PythonConstruct wrap(ASTNode node) {
        return PythonConstructFactory.wrap(node, this);
    }
    
    public String displayName() {
        return "Module " + this.moduleName;
    }
    
}
