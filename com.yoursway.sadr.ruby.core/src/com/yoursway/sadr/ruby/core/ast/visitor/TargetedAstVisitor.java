package com.yoursway.sadr.ruby.core.ast.visitor;

import org.eclipse.dltk.ast.ASTNode;

public abstract class TargetedAstVisitor<T extends ASTNode> extends RubyAstVisitor<T> {
    
    private final ASTNode target;
    
    public TargetedAstVisitor(ASTNode target) {
        super(null);
        this.target = target;
    }
    
    protected ASTNode target() {
        return target;
    }
    
    public TargetedAstVisitor(TargetedAstVisitor<?> parentVisitor) {
        super(parentVisitor);
        target = parentVisitor.target;
    }
    
    @Override
    protected boolean shouldEnter(ASTNode s) {
        //        int start = s.sourceStart();
        //        int end = s.sourceEnd();
        //        int targetStart = target.sourceStart();
        //        int targetEnd = target.sourceEnd();
        // TODO: fix this
        //        if (targetStart >= 0 && end >= 0 && targetStart > end)
        //            return false;
        //        if (targetEnd >= 0 && start >= 0 && targetEnd < start)
        //            return false;
        return true;
    }
    
}
