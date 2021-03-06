package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyFile;
import com.yoursway.sadr.ruby.core.runtime.RubyLocalVariable;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.services.NodeLookup;

public class FileScope extends LocalScope implements NodeLookup {
    
    private final ISourceModule file;
    
    private final AbstractMultiMap<ASTNode, ModuleDeclaration> extentions = new ArrayListHashMultiMap<ASTNode, ModuleDeclaration>();
    
    private final RubyFile module;
    
    public FileScope(RootScope parent, RubyFile module, ISourceModule file, ModuleDeclaration node) {
        super(parent, node);
        this.module = module;
        this.file = file;
    }
    
    @Override
    public ModuleDeclaration node() {
        return (ModuleDeclaration) super.node();
    }
    
    public RubyFile module() {
        return module;
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
    
    @Override
    public RubyVariable lookupVariable(String name) {
        RubyVariable variable = findVariable(name);
        if (variable == null)
            variable = new RubyLocalVariable(module, null, this, name);
        return variable;
    }
    
    public NodeBoundItem lookup(ASTNode node) {
        return ((RootScope) parent).outeriorNodeLookup().lookup(file, node);
    }
    
    @Override
    public RubyVariable findOwnVariable(String name) {
        RubyVariable var = module.findLocalVariable(name);
        if (var != null)
            return var;
        return null;
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
    public RubyClass currentClass() {
        return null;
    }
    
}