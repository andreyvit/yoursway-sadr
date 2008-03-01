package com.yoursway.sadr.ruby.core.ast.visitor;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.ruby.core.typeinferencing.services.NodeLookup;

public final class RubyExtendedAstTraverser extends RubyAstTraverser {
    
    private final NodeLookup lookup;
    
    public RubyExtendedAstTraverser(NodeLookup lookup) {
        this.lookup = lookup;
    }
    
    @Override
    protected void leave(ASTNode node) {
        processExtentions(node);
        super.leave(node);
    }
    
    private void processExtentions(ASTNode node) {
        for (ModuleDeclaration ext : lookup.extentionsOf(node)) {
            try {
                ext.traverse(adapter);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
