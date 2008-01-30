package com.yoursway.sadr.ruby;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.Construct;
import com.yoursway.sadr.core.Scope;
import com.yoursway.sadr.ruby.constructs.CallC;
import com.yoursway.sadr.ruby.constructs.ModuleDeclarationC;

public class ConstructFactory {
    
    public static final ConstructFactory I = new ConstructFactory(); 
    
    public Construct create(Scope scope, ASTNode node) {
        if (node instanceof ModuleDeclaration)
            return new ModuleDeclarationC(scope, (ModuleDeclaration) node);
        else if (node instanceof CallExpression)
            return new CallC(scope, (CallExpression) node);
        return null;
    }
    
}
