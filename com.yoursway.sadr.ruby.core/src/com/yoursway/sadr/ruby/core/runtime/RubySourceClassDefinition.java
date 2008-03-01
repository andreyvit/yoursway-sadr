package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;

import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class RubySourceClassDefinition extends RubyClassDefinition implements ContributableItem,
        LocalVariableContainer {
    
    private final RubyClass superclass;
    private final RubyClassDeclaration node;
    private final Scope scope;
    
    private final Collection<RubyLocalVariable> localVariables = new ArrayList<RubyLocalVariable>();
    
    private final Map<String, RubyLocalVariable> namesToLocalVariables = new HashMap<String, RubyLocalVariable>();
    
    public RubySourceClassDefinition(RubyClass klass, Context context,
            Construct<Scope, RubyClassDeclaration> construct, RubyClass superclass) {
        super(klass);
        this.node = construct.node();
        this.superclass = superclass;
        context.add(this);
        scope = construct.scope();
    }
    
    @Override
    public RubyClass superclass() {
        return superclass;
    }
    
    public Scope scope() {
        return scope;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return node.getClassName();
    }
    
    public void addLocalVariable(RubyLocalVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name().toLowerCase(), localVariable);
    }
    
    public RubyVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name.toLowerCase());
    }
    
    public ASTNode node() {
        return node;
    }
    
}
