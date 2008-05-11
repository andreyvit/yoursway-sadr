package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.python_v2.model.Context;

public class PythonSourceField extends PythonField implements ContributableItem {
    
    private final VariableReference node;
    
    public PythonSourceField(Context context, PythonBasicClass klass, String name, VariableReference node) {
        super(klass, name);
        this.node = node;
        context.add(this);
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        return new ASTNode[] { node };
    }
}
