package com.yoursway.sadr.ruby.core.rewriting;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;

import com.yoursway.sadr.ruby.core.rewriting.edits.CompoundEdit;
import com.yoursway.sadr.ruby.core.rewriting.edits.ReplaceEdit;

public class WCall extends WNode {
    
    private String name;
    
    private final int nameOffset;
    
    private final int nameLength;
    
    private boolean nameChanged;
    
    WCall(RewritingSession session, CallExpression node) {
        SimpleReference nameNode = node.getCallName();
        this.name = nameNode.getName();
        
        ASTNode receiver = node.getReceiver();
        if (receiver == null) {
            nameOffset = session.skipWhitespace(node.sourceStart());
        } else {
            int offset = session.skipWhitespace(node.getReceiver().sourceEnd());
            if (session.charAt(offset) == '.') {
                offset = session.skipWhitespace(offset + 1);
            }
            nameOffset = offset;
        }
        int end = session.skipIdentifier(nameOffset);
        this.nameLength = end - nameOffset;
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
