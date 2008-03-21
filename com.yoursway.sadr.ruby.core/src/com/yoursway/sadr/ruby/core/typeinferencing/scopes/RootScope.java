package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.core.propagation.PropagationTrackerImpl;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.ruby.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.OuteriorNodeLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.SearchService;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public class RootScope extends AbstractScope {
    
    private final RubyRuntimeModel model;
    private final OuteriorNodeLookup outeriorNodeLookup;
    private final SearchService searchService;
    
    private final PropagationTracker<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> propagationTracker;
    
    public RootScope(RubyRuntimeModel model, OuteriorNodeLookup outeriorNodeLookup,
            SearchService searchService) {
        this.model = model;
        this.outeriorNodeLookup = outeriorNodeLookup;
        this.searchService = searchService;
        propagationTracker = PropagationTrackerImpl.create();
    }
    
    public ClassLookup classLookup() {
        return model;
    }
    
    public OuteriorNodeLookup outeriorNodeLookup() {
        return outeriorNodeLookup;
    }
    
    @Override
    public String toString() {
        return "root";
    }
    
    public NodeLookup nodeLookup() {
        throw new UnsupportedOperationException("RootScope.nodeLookup is not supported");
    }
    
    public VariableLookup variableLookup() {
        return model;
    }
    
    public ProcedureLookup procedureLookup() {
        return model;
    }
    
    public ValueInfo selfType() {
        throw new UnsupportedOperationException();
    }
    
    public SearchService searchService() {
        return searchService;
    }
    
    public InstanceRegistrar instanceRegistrar() {
        return model.instanceRegistrar();
    }
    
    public FileScope fileScope() {
        throw new UnsupportedOperationException();
    }
    
    public PropagationTracker<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> propagationTracker() {
        return propagationTracker;
    }
    
    public RubyConstruct createConstruct() {
        throw new UnsupportedOperationException();
    }
    
    public RubyClass currentClass() {
        throw new UnsupportedOperationException();
    }
    
    public RubyConstruct parentConstruct() {
        return null;
    }
}
