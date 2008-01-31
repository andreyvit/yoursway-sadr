package com.yoursway.sadr.ruby;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ruby.ast.RubyCallArgumentsList;

import com.yoursway.sadr.core.Construct;
import com.yoursway.sadr.core.Scope;
import com.yoursway.sadr.ruby.constructs.CallC;
import com.yoursway.sadr.ruby.constructs.MethodDeclarationC;
import com.yoursway.sadr.ruby.constructs.ModuleDeclarationC;
import com.yoursway.sadr.ruby.constructs.StatementListC;
import com.yoursway.sadr.ruby.constructs.UnhandledC;

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
        if (node instanceof RubyCallArgumentsList || node instanceof CallArgumentsList)
            return new UnhandledC(scope, node);
        throw new RuntimeException("No construct found for node " + node.getClass());
    }
    
}
