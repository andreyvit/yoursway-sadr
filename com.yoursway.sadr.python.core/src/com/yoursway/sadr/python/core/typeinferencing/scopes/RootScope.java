package com.yoursway.sadr.python.core.typeinferencing.scopes;

import com.yoursway.sadr.python.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.OuteriorNodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.PropagationTracker;
import com.yoursway.sadr.python.core.typeinferencing.services.PropagationTrackerImpl;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public class RootScope extends AbstractScope {
    
    private final RubyRuntimeModel model;
    private final OuteriorNodeLookup outeriorNodeLookup;
    private final SearchService searchService;
    
    private final PropagationTracker propagationTracker;
    
    public RootScope(RubyRuntimeModel model, OuteriorNodeLookup outeriorNodeLookup, SearchService searchService) {
        this.model = model;
        this.outeriorNodeLookup = outeriorNodeLookup;
        this.searchService = searchService;
        propagationTracker = new PropagationTrackerImpl();
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
    
    public PropagationTracker propagationTracker() {
        return propagationTracker;
    }
    
     public IConstruct createConstruct() {
        throw new UnsupportedOperationException();
    }
}
