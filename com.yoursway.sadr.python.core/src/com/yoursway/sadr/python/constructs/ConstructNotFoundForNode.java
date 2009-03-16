package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.utils.Failure;

public class ConstructNotFoundForNode extends Failure {
    
    private static final long serialVersionUID = 1L;
    
    public ConstructNotFoundForNode(ASTNode node) {
        add("node_class", node.getClass().getName());
    }
    
}
