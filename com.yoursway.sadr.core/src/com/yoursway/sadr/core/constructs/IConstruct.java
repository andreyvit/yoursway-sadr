package com.yoursway.sadr.core.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;

public interface IConstruct<C extends IConstruct<?, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    SC staticContext();
    
    N node();
    
    C staticallyEnclosingConstruct();
    
    C subconstructFor(ASTNode node);
    
    void evaluateValue(DC dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation);
    
    void calculateEffectiveControlFlowGraph(ContinuationRequestor requestor,
            ControlFlowGraphRequestor<C, SC, DC, N> continuation);
    
}
