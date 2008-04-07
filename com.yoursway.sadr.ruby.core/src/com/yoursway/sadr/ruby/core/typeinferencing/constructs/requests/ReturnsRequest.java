package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.google.common.collect.Lists;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;

public class ReturnsRequest implements Request<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    private final Collection<RubyConstruct> returns = Lists.newArrayList();
    
    public ReturnsRequest() {
    }
    
    public void accept(RubyConstruct construct) {
        if (construct instanceof ReturnsAffector)
            ((ReturnsAffector) construct).actOnReturns(this);
    }
    
    public void add(RubyConstruct construct) {
        returns.add(construct);
    }
    
    public RubyConstruct[] returns() {
        return returns.toArray(new RubyConstruct[returns.size()]);
    }
    
    public ContinuationRequestorCalledToken enter(RubyConstruct construct, ContinuationScheduler requestor,
            VisitorRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        accept(construct);
        return continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
}
