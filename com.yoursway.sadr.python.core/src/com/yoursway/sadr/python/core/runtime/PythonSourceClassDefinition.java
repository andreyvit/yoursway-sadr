package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import sun.reflect.generics.scope.ClassScope;

import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.model.Context;

public class PythonSourceClassDefinition extends PythonClassDefinition implements ContributableItem,
        LocalVariableContainer {
    
    private final PythonMetaType superclass;
    private final PythonClassDeclaration node;
    
    private final Collection<PythonScopedVariable> localVariables = new ArrayList<PythonScopedVariable>();
    
    private final Map<String, PythonScopedVariable> namesToLocalVariables = new HashMap<String, PythonScopedVariable>();
    private final ClassScope scope;
    
    public PythonSourceClassDefinition(ClassScope scope, Context context, ClassDeclarationC construct,
            PythonMetaType superclass) {
        super(scope.klass());
        this.scope = scope;
        this.node = construct.node();
        this.superclass = superclass;
        context.add(this);
    }
    
    public ClassScope parentScope() {
        return scope;
    }
    
    @Override
    public PythonMetaType superclass() {
        return superclass;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return node; // FIXME
    }
    
    public void addLocalVariable(PythonScopedVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name().toLowerCase(), localVariable);
    }
    
    public PythonVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name.toLowerCase());
    }
    
    public ASTNode node() {
        return node;
    }
    
}
