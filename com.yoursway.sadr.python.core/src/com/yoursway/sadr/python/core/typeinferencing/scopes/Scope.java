package com.yoursway.sadr.python.core.typeinferencing.scopes;

import com.yoursway.sadr.python.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.PropagationTracker;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;
import com.yoursway.sadr.python.core.typeinferencing.services.ServicesMegapack;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public interface Scope extends ServicesMegapack, StaticContext, DynamicContext {
    
    ClassLookup classLookup();
    
    NodeLookup nodeLookup();
    
    VariableLookup variableLookup();
    
    ProcedureLookup procedureLookup();
    
    ValueInfo selfType();
    
    SearchService searchService();
    
    InstanceRegistrar instanceRegistrar();
    
    FileScope fileScope();
    
    PropagationTracker propagationTracker();
    
     IConstruct createConstruct();
}
