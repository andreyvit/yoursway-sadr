package com.yoursway.sadr.ruby.core.runtime.contributions;

import org.eclipse.dltk.ast.ASTNode;

public interface NodeBoundItem extends ContributableItem {
    
    ASTNode node();
    
}
