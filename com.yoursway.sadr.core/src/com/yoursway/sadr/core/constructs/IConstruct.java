package com.yoursway.sadr.core.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;

public interface IConstruct<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    SC staticContext();
    
    N node();
    
    C staticallyEnclosingConstruct();
    
    /**
     * WARNING: this method has O(N) complexity (where N is the number of
     * nodes), so use sparingly.
     */
    C subconstructFor(ASTNode node);
    
    C parent();
    
    List<C> enclosedConstructs();
    
    ContinuationRequestorCalledToken evaluateValue(DC dc, InfoKind infoKind, ContinuationScheduler requestor,
            ValueInfoContinuation continuation);
    
    ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(ContinuationScheduler requestor,
            ControlFlowGraphRequestor<C, SC, DC, N> continuation);
    
}
