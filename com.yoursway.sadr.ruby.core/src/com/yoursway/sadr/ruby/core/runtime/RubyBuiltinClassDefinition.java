package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public class RubyBuiltinClassDefinition extends RubyClassDefinition {
    
    private final RubyClass superclass;
    
    public RubyBuiltinClassDefinition(RubyClass klass, RubyClass superclass) {
        super(klass);
        this.superclass = superclass;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return null;
    }
    
    @Override
    public RubyClass superclass() {
        return superclass;
    }
    
}
