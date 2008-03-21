package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.IConstruct;

public interface RubyConstruct extends
        IConstruct<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    //    RubyStaticContext rubyStaticContext();
    //    
    //    ASTNode node();
    //    
    //    RubyConstruct staticallyEnclosingConstruct();
    //    
    //    RubyConstruct subconstructFor(ASTNode node);
    //    
    //    void evaluateValue(RubyDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
    //            ValueInfoContinuation continuation);
    //    
    //    void calculateEffectiveControlFlowGraph(ContinuationRequestor requestor,
    //            ControlFlowGraphRequestor continuation);
    //    
}