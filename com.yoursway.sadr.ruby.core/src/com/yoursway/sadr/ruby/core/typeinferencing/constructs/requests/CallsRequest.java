package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.CallInfo;

public class CallsRequest implements Request<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    private final RubyVariable variable;
    
    private final Collection<CallInfo> calls = new ArrayList<CallInfo>();
    
    public CallsRequest(RubyVariable variable, InfoKind infoKind) {
        this.variable = variable;
    }
    
    public RubyVariable variable() {
        return variable;
    }
    
    public void accept(RubyConstruct construct) {
        if (construct instanceof CallsAffector)
            ((CallsAffector) construct).actOnCalls(this);
    }
    
    public void add(CallInfo info) {
        calls.add(info);
    }
    
    public CallInfo[] calls() {
        return calls.toArray(new CallInfo[calls.size()]);
    }
    
    public void enter(RubyConstruct construct, ContinuationRequestor requestor,
            VisitorRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        accept(construct);
        continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
}
