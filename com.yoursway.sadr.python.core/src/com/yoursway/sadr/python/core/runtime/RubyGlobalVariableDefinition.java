package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ruby.ast.RubyAssignment;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;

public class RubyGlobalVariableDefinition implements ContributableItem {
    
    private final VariableReference node1;
    private final RubyAssignment node2;
    
    public RubyGlobalVariableDefinition(RubyGlobalVariable variable, Context context,
            VariableReference node1, RubyAssignment node2) {
        this.node1 = node1;
        this.node2 = node2;
        variable.addDefinition(this);
    }
    
    public VariableReference getDeclarationNode() {
        return node1;
    }
    
    public RubyAssignment getAssignmentNode() {
        return node2;
    }
    
}
