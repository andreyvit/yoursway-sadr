package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.ruby.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.PropagationTracker;
import com.yoursway.sadr.ruby.core.typeinferencing.services.SearchService;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ServicesMegapack;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public interface Scope extends ServicesMegapack, RubyStaticContext, RubyDynamicContext {
    
    ClassLookup classLookup();
    
    NodeLookup nodeLookup();
    
    VariableLookup variableLookup();
    
    ProcedureLookup procedureLookup();
    
    ValueInfo selfType();
    
    SearchService searchService();
    
    InstanceRegistrar instanceRegistrar();
    
    FileScope fileScope();
    
    PropagationTracker propagationTracker();
    
     RubyConstruct createConstruct();
}
