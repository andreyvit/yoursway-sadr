package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.core.propagation.PropagationTrackerImpl;
import com.yoursway.sadr.python.core.runtime.PythonAnalysisSchema;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.OuteriorNodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public class RootScope extends AbstractScope {
    
    private final PythonRuntimeModel model;
    private final OuteriorNodeLookup outeriorNodeLookup;
    private final SearchService searchService;
    
    private final PropagationTracker<PythonConstruct, Scope, PythonDynamicContext, ASTNode> propagationTracker;
    
    public RootScope(PythonRuntimeModel model, OuteriorNodeLookup outeriorNodeLookup,
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
    
    public PropagationTracker<PythonConstruct, Scope, PythonDynamicContext, ASTNode> propagationTracker() {
        return propagationTracker;
    }
    
    public PythonConstruct createConstruct() {
        throw new UnsupportedOperationException();
    }
    
    public PythonClass currentClass() {
        throw new UnsupportedOperationException();
    }
    
    public PythonConstruct parentConstruct() {
        return null;
    }
    
    public RuntimeModel runtimeModel() {
        return model;
    }
    
    public PythonAnalysisSchema schema() {
        return model.schema();
    }
    
}
