package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonFileC;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public abstract class LocalScope extends ChildScope implements VariableLookup {
    
    private final ASTNode node;
    
    public LocalScope(Scope parent, ASTNode node) {
        super(parent);
        this.node = node;
    }
    
    public ASTNode node() {
        return node;
    }
    
    public IConstruct createConstruct() {
        FileScope fileScope = fileScope();
        PythonFileC fileC = new PythonFileC(fileScope, fileScope.node());
        return fileC.subconstructFor(node);
    }
    
    public VariableLookup variableLookup() {
        return this;
    }
    
    public RubyVariable findVariable(String name) {
        RubyVariable result = findOwnVariable(name);
        if (result != null)
            return result;
        return parent.variableLookup().findVariable(name);
    }
    
    public abstract RubyVariable findOwnVariable(String name);
    
}
