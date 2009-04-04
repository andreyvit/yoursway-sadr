package com.yoursway.sadr.python.analysis.lang.constructs.special;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;

public class DummyAstNode extends ASTNode {
    
    @Override
    public void traverse(ASTVisitor visitor) throws Exception {
    }
    
}
