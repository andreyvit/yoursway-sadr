package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.AbstractConstruct;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AccessInfo;

public class EmptyConstruct extends
        AbstractConstruct<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> implements
        RubyConstruct {
    
    public EmptyConstruct(RubyStaticContext sc) {
        super(sc);
    }
    
    @Override
    protected RubyConstruct wrap(RubyStaticContext sc, ASTNode node) {
        throw new NotImplementedException();
        //return null;
    }
    
    public ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(
            ContinuationScheduler requestor,
            ControlFlowGraphRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        List<RubyConstruct> emptyList = new ArrayList<RubyConstruct>();
        return continuation
                .process(new ControlFlowGraph<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode>(
                        emptyList), requestor);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        throw new NotImplementedException();
    }
    
    public ASTNode node() {
        return null;
    }
    
    public RubyConstruct staticallyEnclosingConstruct() {
        throw new NotImplementedException();
        // return null;
    }
    
    public RubyConstruct subconstructFor(ASTNode node) {
        throw new NotImplementedException();
        // return null;
    }
    
    public List<RubyConstruct> enclosedConstructs() {
        throw new NotImplementedException();
        // return null;
    }
    
    public RubyConstruct parent() {
        throw new NotImplementedException();
        // return null;
    }
    
    public Collection<AccessInfo> accessInfos() {
        throw new NotImplementedException();
        // return null;
    }
    
}
