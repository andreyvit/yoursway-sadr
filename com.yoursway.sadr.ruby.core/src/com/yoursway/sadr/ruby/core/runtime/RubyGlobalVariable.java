package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

public class RubyGlobalVariable extends RubyVariable {
    
    private final List<RubyGlobalVariableDefinition> definitions = new ArrayList<RubyGlobalVariableDefinition>();
    private final String name;
    
    public RubyGlobalVariable(RubyRuntimeModel model, String name) {
        this.name = name;
        model.addGlobalVariable(this);
    }
    
    @Override
    public String name() {
        return name;
    }
    
    public void addDefinition(RubyGlobalVariableDefinition definition) {
        definitions.add(definition);
    }
    
    @Override
    public String toString() {
        return "$" + name;
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        ASTNode[] result = new ASTNode[definitions.size()];
        int i = 0;
        for (RubyGlobalVariableDefinition def : definitions) {
            result[i++] = def.getAssignmentNode().getLeft();
        }
        return result;
    }
    
}
