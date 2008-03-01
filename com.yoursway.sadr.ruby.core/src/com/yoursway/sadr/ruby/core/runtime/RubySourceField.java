package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.StringLiteral;

import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.runtime.contributions.ContributableItem;

public class RubySourceField extends RubyField implements ContributableItem {
    
    private final StringLiteral node;
    
    public RubySourceField(Context context, RubyBasicClass klass, String name, RubyClassDefinition definition,
            StringLiteral node) {
        super(klass, name);
        this.node = node;
        context.add(this);
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        return new ASTNode[] { node };
    }
}
