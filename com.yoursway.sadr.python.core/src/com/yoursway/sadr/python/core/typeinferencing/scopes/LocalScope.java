package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonFileC;
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
    
    public PythonConstruct createConstruct() {
        FileScope fileScope = fileScope();
        PythonFileC fileC = new PythonFileC(fileScope, fileScope.node());
        return fileC.subconstructFor(node);
    }
    
    public VariableLookup variableLookup() {
        return this;
    }
    
    public PythonVariable findVariable(String name) {
        PythonVariable result = findOwnVariable(name);
        if (result != null)
            return result;
        return parent.variableLookup().findVariable(name);
    }
    
    public PythonVariable lookupVariable(String name) {
        return findVariable(name);
    }
    
    public abstract PythonVariable findOwnVariable(String name);
    
}
