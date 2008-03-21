package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.DtlFileC;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public abstract class LocalScope extends ChildScope implements VariableLookup {
    
    private final ASTNode node;
    
    public LocalScope(Scope parent, ASTNode node) {
        super(parent);
        this.node = node;
    }
    
    public ASTNode node() {
        return node;
    }
    
    public RubyConstruct createConstruct() {
        FileScope fileScope = fileScope();
        DtlFileC fileC = new DtlFileC(fileScope, fileScope.node());
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
