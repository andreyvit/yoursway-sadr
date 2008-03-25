package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;

import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.ClassDeclarationC;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.ClassScope;

public class RubySourceClassDefinition extends RubyClassDefinition implements ContributableItem,
        LocalVariableContainer {
    
    private final RubyClass superclass;
    private final RubyClassDeclaration node;
    private final ClassScope scope;
    
    private final Collection<RubyLocalVariable> localVariables = new ArrayList<RubyLocalVariable>();
    
    private final Map<String, RubyLocalVariable> namesToLocalVariables = new HashMap<String, RubyLocalVariable>();
    
    public RubySourceClassDefinition(ClassScope scope, Context context, ClassDeclarationC construct,
            RubyClass superclass) {
        super(scope.klass());
        this.scope = scope;
        this.node = construct.node();
        this.superclass = superclass;
        context.add(this);
    }
    
    @Override
    public RubyClass superclass() {
        return superclass;
    }
    
    public ClassScope scope() {
        return scope;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return node.getClassName();
    }
    
    public void addLocalVariable(RubyLocalVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name(), localVariable);
    }
    
    public RubyVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name);
    }
    
    public ASTNode node() {
        return node;
    }
    
}
