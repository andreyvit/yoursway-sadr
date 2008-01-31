package com.yoursway.sadr.ruby.constructs;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.core.AbstractConstruct;
import com.yoursway.sadr.core.Scope;

public class MethodDeclarationC extends AbstractConstruct {
    
    public MethodDeclarationC(Scope scope, MethodDeclaration node) {
        super(scope, node);
    }
    
}
