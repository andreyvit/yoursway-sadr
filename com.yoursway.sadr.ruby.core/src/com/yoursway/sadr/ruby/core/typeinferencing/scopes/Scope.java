package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.services.NodeLookup;

public interface Scope extends RubyStaticContext, RubyDynamicContext {
    
    NodeLookup nodeLookup();
    
    FileScope fileScope();
    
    RubyConstruct createConstruct();
}
