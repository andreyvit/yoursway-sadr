package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public abstract class RubyClassDefinition {
    
    private final RubyClass klass;
    
    public RubyClassDefinition(RubyClass klass) {
        this.klass = klass;
        klass.addClassDefinition(this);
    }
    
    public RubyClass klass() {
        return klass;
    }
    
    public abstract RubyClass superclass();
    
    public abstract ASTNode nodeForSelection();
    
}
