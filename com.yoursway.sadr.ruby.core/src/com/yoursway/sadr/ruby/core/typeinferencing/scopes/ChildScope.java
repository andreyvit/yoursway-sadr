package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.ruby.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.SearchService;

public abstract class ChildScope extends AbstractScope {
    
    protected final Scope parent;
    
    public ChildScope(Scope parent) {
        Assert.isNotNull(parent);
        this.parent = parent;
    }
    
    public ClassLookup classLookup() {
        return parent.classLookup();
    }
    
    public NodeLookup nodeLookup() {
        return parent.nodeLookup();
    }
    
    public ProcedureLookup procedureLookup() {
        return parent.procedureLookup();
    }
    
    public SearchService searchService() {
        return parent.searchService();
    }
    
    public InstanceRegistrar instanceRegistrar() {
        return parent.instanceRegistrar();
    }
    
    public PropagationTracker<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> propagationTracker() {
        return parent.propagationTracker();
    }
    
    @Override
    public String toString() {
        return parent.toString() + "/" + leafToString();
    }
    
    public Scope parent() {
        return parent;
    }
    
    public FileScope getOuterFile() {
        if (this instanceof FileScope)
            return (FileScope) this;
        Scope t = parent;
        while (t != null) {
            if (!(t instanceof ChildScope))
                return null;
            if (t instanceof FileScope)
                return (FileScope) t;
            t = ((ChildScope) t).parent;
        }
        return null;
    }
    
    protected abstract String leafToString();
    
    public FileScope fileScope() {
        return parent.fileScope();
    }
    
    public RubyClass currentClass() {
        return parent.currentClass();
    }
    
    public RubyConstruct parentConstruct() {
        return parent.parentConstruct();
    }
    
}
