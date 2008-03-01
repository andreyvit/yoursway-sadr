package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;

public class RubySourceField extends RubyField implements ContributableItem {
    
    private final VariableReference node;
    
    public RubySourceField(Context context, RubyBasicClass klass, String name, VariableReference node) {
        super(klass, name);
        this.node = node;
        context.add(this);
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        return new ASTNode[] { node };
    }
}
