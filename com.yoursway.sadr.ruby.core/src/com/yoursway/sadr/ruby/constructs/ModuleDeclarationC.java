package com.yoursway.sadr.ruby.constructs;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.AbstractConstruct;
import com.yoursway.sadr.core.Scope;

public class ModuleDeclarationC extends AbstractConstruct {
    
    public ModuleDeclarationC(Scope scope, ModuleDeclaration node) {
        super(scope, node);
    }
    
    @Override
    public ModuleDeclaration node() {
        return (ModuleDeclaration) super.node();
    }

}
