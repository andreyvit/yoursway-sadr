package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.python.core.runtime.PythonAnalysisSchema;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public class PythonDelegatingStaticContext implements Scope {
    
    private final Scope delegateTo;
    
    public PythonDelegatingStaticContext(Scope delegateTo) {
        this.delegateTo = delegateTo;
    }
    
    public StandardTypes builtins() {
        return delegateTo.builtins();
    }
    
    public ClassLookup classLookup() {
        return delegateTo.classLookup();
    }
    
    public PythonClass currentClass() {
        return delegateTo.currentClass();
    }
    
    public Collection<ModuleDeclaration> extentionsOf(ASTNode node) {
        return delegateTo.extentionsOf(node);
    }
    
    public InstanceRegistrar instanceRegistrar() {
        return delegateTo.instanceRegistrar();
    }
    
    public PythonConstruct parentConstruct() {
        return delegateTo.parentConstruct();
    }
    
    public ProcedureLookup procedureLookup() {
        return delegateTo.procedureLookup();
    }
    
    public PropagationTracker<PythonConstruct, Scope, PythonDynamicContext, ASTNode> propagationTracker() {
        return delegateTo.propagationTracker();
    }
    
    public SearchService searchService() {
        return delegateTo.searchService();
    }
    
    public ValueInfo selfType() {
        return delegateTo.selfType();
    }
    
    public VariableLookup variableLookup() {
        return delegateTo.variableLookup();
    }
    
    public PythonConstruct createConstruct() {
        return delegateTo.createConstruct();
    }
    
    public FileScope fileScope() {
        return delegateTo.fileScope();
    }
    
    public NodeLookup nodeLookup() {
        return delegateTo.nodeLookup();
    }
    
    public Scope nearestScope() {
        return delegateTo.nearestScope();
    }
    
    public RuntimeModel runtimeModel() {
        return delegateTo.runtimeModel();
    }
    
    public PythonAnalysisSchema schema() {
        return delegateTo.schema();
    }
    
}
