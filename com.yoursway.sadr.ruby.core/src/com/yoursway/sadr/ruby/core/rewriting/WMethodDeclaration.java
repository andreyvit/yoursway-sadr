package com.yoursway.sadr.ruby.core.rewriting;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.ruby.core.rewriting.edits.CompoundEdit;
import com.yoursway.sadr.ruby.core.rewriting.edits.ReplaceEdit;

public class WMethodDeclaration extends WNode {
    
    private String name;
    
    private final int nameOffset;
    
    private final int nameLength;
    
    private boolean nameChanged;
    
    WMethodDeclaration(RewritingSession session, MethodDeclaration node) {
        name = node.getName();
        nameOffset = node.getNameStart();
        nameLength = name.length();
    }
    
    public void setName(String name) {
        this.name = name;
        nameChanged = true;
    }
    
    @Override
    public void createEdits(CompoundEdit compound) {
        if (nameChanged) {
            compound.add(new ReplaceEdit(nameOffset, nameLength, name));
        }
    }
    
}
