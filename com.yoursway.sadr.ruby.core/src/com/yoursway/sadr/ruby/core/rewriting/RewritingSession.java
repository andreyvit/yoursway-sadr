package com.yoursway.sadr.ruby.core.rewriting;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ruby.core.utils.RubySyntaxUtils;

import com.yoursway.sadr.ruby.core.rewriting.edits.CompoundEdit;

public class RewritingSession {
    
    private final Map<ASTNode, WNode> nodes = new HashMap<ASTNode, WNode>();
    private final CharSequence source;
    
    public RewritingSession(CharSequence source) {
        this.source = source;
    }
    
    public WNode map(ASTNode node) {
        WNode wnode = nodes.get(node);
        if (wnode == null) {
            wnode = create(node);
            nodes.put(node, wnode);
        }
        return wnode;
    }
    
    private WNode create(ASTNode node) {
        if (node instanceof MethodDeclaration)
            return new WMethodDeclaration(this, (MethodDeclaration) node);
        if (node instanceof CallExpression)
            return new WCall(this, (CallExpression) node);
        return new WPassThru(this, node);
    }
    
    public String commit(String content) {
        StringBuilder builder = new StringBuilder(content);
        CompoundEdit compound = new CompoundEdit();
        for (WNode wnode : nodes.values())
            wnode.createEdits(compound);
        compound.apply(builder);
        return builder.toString();
    }
    
    int skipWhitespace(int start) {
        int end = source.length();
        for (int cur = start; cur < end; cur++)
            if (!RubySyntaxUtils.isWhitespace(source.charAt(cur)))
                return cur;
        return end;
    }
    
    int skipIdentifier(int start) {
        int end = source.length();
        for (int cur = start; cur < end; cur++)
            if (!RubySyntaxUtils.isLessStrictIdentifierCharacter(source.charAt(cur)))
                return cur;
        return end;
    }
    
    char charAt(int offset) {
        return source.charAt(offset);
    }
    
    String substring(int start, int end) {
        return source.subSequence(start, end).toString();
    }
    
}
