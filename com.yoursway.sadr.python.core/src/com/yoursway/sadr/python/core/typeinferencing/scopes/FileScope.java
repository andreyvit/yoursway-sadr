package com.yoursway.sadr.python.core.typeinferencing.scopes;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;

public class FileScope extends LocalScope implements NodeLookup {
    
    private final ISourceModule file;
    
    private final AbstractMultiMap<ASTNode, ModuleDeclaration> extentions = new ArrayListHashMultiMap<ASTNode, ModuleDeclaration>();
    
    public FileScope(RootScope parent, ISourceModule file, ModuleDeclaration node) {
        super(parent, node);
        this.file = file;
    }
    
    @Override
    public ModuleDeclaration node() {
        return (ModuleDeclaration) super.node();
    }
    
    @Override
    public NodeLookup nodeLookup() {
        return this;
    }
    
    public ISourceModule file() {
        return file;
    }
    
    @Override
    protected String leafToString() {
        return file.getElementName();
    }
    
    public NodeBoundItem lookup(ASTNode node) {
        return ((RootScope) parent).outeriorNodeLookup().lookup(file, node);
    }
    
    @Override
    public PythonVariable findOwnVariable(String name) {
        return null; // none for now
    }
    
    public ValueInfo selfType() {
        return null;
    }
    
    @Override
    public Collection<ModuleDeclaration> extentionsOf(ASTNode node) {
        Collection<ModuleDeclaration> result = extentions.get(node);
        if (result == null)
            return Collections.emptyList();
        else
            return result;
    }
    
    @Override
    public FileScope fileScope() {
        return this;
    }
    
    @Override
    public PythonClass currentClass() {
        return null;
    }
    
}
