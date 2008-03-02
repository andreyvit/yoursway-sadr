package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ClassScope;

public class RubySourceClassDefinition extends RubyClassDefinition implements ContributableItem,
        LocalVariableContainer {
    
    private final RubyClass superclass;
    private final PythonClassDeclaration node;
    
    private final Collection<RubyLocalVariable> localVariables = new ArrayList<RubyLocalVariable>();
    
    private final Map<String, RubyLocalVariable> namesToLocalVariables = new HashMap<String, RubyLocalVariable>();
    private final ClassScope scope;
    
    public RubySourceClassDefinition(ClassScope scope, Context context, ClassDeclarationC construct,
            RubyClass superclass) {
        super(scope.klass());
        this.scope = scope;
        this.node = construct.node();
        this.superclass = superclass;
        context.add(this);
    }
    
    public ClassScope scope() {
        return scope;
    }
    
    @Override
    public RubyClass superclass() {
        return superclass;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return node; // FIXME
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
