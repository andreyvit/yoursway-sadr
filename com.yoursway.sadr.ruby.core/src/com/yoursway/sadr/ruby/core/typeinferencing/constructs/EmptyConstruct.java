package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;

public class EmptyConstruct extends AbstractConstruct {
    
    public EmptyConstruct(StaticContext sc) {
        super(sc);
    }
    
    @Override
    public IConstruct staticallyEnclosingConstruct() {
        throw new NotImplementedException();
        //return null;
    }
    
    @Override
    public IConstruct subconstructFor(ASTNode node) {
        throw new NotImplementedException();
        //return null;
    }
    
    @Override
    protected IConstruct wrap(StaticContext sc, ASTNode node) {
        throw new NotImplementedException();
        //return null;
    }
    
    public void calculateEffectiveControlFlowGraph(ContinuationRequestor requestor,
            ControlFlowGraphRequestor continuation) {
        List<IConstruct> emptyList = new ArrayList<IConstruct>();
        continuation.process(new ControlFlowGraph(emptyList), requestor);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        throw new NotImplementedException();
    }
    
    public ASTNode node() {
        return null;
    }
    
}
