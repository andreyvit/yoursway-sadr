package com.yoursway.sadr.python.core.runtime.contributions;

import org.eclipse.dltk.ast.ASTNode;

public interface NodeBoundItem extends ContributableItem {
    
    ASTNode node();
    
}
