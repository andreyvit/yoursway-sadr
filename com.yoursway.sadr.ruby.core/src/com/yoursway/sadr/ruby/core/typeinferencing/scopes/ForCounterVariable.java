package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ruby.ast.RubyAssignment;

import com.yoursway.sadr.ruby.core.runtime.RubyVariable;

public class ForCounterVariable extends RubyVariable {
    
    private final ForScope scope;
    
    public ForCounterVariable(ForScope scope) {
        this.scope = scope;
    }
    
    @Override
    public String name() {
        if (scope.node().getTarget() instanceof RubyAssignment) {
            RubyAssignment ass = (RubyAssignment) scope.node().getTarget();
            SimpleReference ref = (SimpleReference) ass.getLeft();
            return ref.getName();
        } else
            return "<херня-какая-то>";
    }
    
    public ForScope scope() {
        return scope;
    }
    
    @Override
    public String toString() {
        return name();
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        return new ASTNode[] { scope.node() };
    }
    
}
