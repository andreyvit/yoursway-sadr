package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.AbstractConstruct;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;

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
    
    public void calculateEffectiveControlFlowGraph(
            ContinuationRequestor requestor,
            ControlFlowGraphRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        List<RubyConstruct> emptyList = new ArrayList<RubyConstruct>();
        continuation
                .process(new ControlFlowGraph<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode>(
                        emptyList), requestor);
    }
    
    public void evaluateValue(RubyDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        throw new NotImplementedException();
    }
    
    public ASTNode node() {
        return null;
    }
    
    public RubyStaticContext rubyStaticContext() {
        throw new NotImplementedException();
        // return null;
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
    
}
