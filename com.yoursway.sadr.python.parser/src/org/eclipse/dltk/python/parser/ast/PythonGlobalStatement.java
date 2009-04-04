package org.eclipse.dltk.python.parser.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;

public class PythonGlobalStatement extends Statement {
    
    private final List<VariableReference> declaredVariables = new ArrayList<VariableReference>();
    
    public PythonGlobalStatement(DLTKToken token) {
        super(token);
    }
    
    public void add(DLTKToken token) {
        add(new VariableReference(token));
    }
    
    public void add(VariableReference node) {
        if (node == null)
            throw new NullPointerException("node is null");
        declaredVariables.add(node);
    }
    
    public List<VariableReference> getDeclaredVariables() {
        return declaredVariables;
    }
    
    @Override
    public void traverse(ASTVisitor visitor) throws Exception {
        if (visitor.visit(this)) {
            for (VariableReference var : declaredVariables)
                var.traverse(visitor);
            visitor.endvisit(this);
        }
    }
    
    @Override
    public int getKind() {
        return -42;
    }
    
}
