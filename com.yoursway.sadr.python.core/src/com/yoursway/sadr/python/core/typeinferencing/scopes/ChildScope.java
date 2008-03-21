package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonStaticContext;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;

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
    
    public PropagationTracker<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> propagationTracker() {
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
    
    public PythonClass currentClass() {
        return parent.currentClass();
    }
    
    public PythonConstruct parentConstruct() {
        return parent.parentConstruct();
    }
    
}
