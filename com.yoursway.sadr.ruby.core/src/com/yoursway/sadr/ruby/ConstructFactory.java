package com.yoursway.sadr.ruby;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.statements.Block;

import com.yoursway.sadr.core.Construct;
import com.yoursway.sadr.core.Scope;
import com.yoursway.sadr.ruby.constructs.CallC;
import com.yoursway.sadr.ruby.constructs.MethodDeclarationC;
import com.yoursway.sadr.ruby.constructs.ModuleDeclarationC;
import com.yoursway.sadr.ruby.constructs.StatementListC;

public class ConstructFactory {
    
    public static final ConstructFactory I = new ConstructFactory(); 
    
    public Construct create(Scope scope, ASTNode node) {
        if (node instanceof ModuleDeclaration)
            return new ModuleDeclarationC(scope, (ModuleDeclaration) node);
        if (node instanceof MethodDeclaration)
            return new MethodDeclarationC(scope, (MethodDeclaration) node);
        if (node instanceof CallExpression)
            return new CallC(scope, (CallExpression) node);
        if (node instanceof Block)
            return new StatementListC(scope, node);
        throw new RuntimeException("No construct found for node " + node.getClass());
    }
    
}
