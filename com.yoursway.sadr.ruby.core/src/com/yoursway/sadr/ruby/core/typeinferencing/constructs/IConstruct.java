package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;

public interface IConstruct {
    
    StaticContext staticContext();
    
    ASTNode node();
    
    IConstruct staticallyEnclosingConstruct();
    
    IConstruct subconstructFor(ASTNode node);
    
    void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation);
    
    void calculateEffectiveControlFlowGraph(ContinuationRequestor requestor,
            ControlFlowGraphRequestor continuation);
    
}