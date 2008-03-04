package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.google.common.collect.Lists;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonStaticContext;

public class ReturnsRequest implements
        Request<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    private final Collection<PythonConstruct> returns = Lists.newArrayList();
    
    public ReturnsRequest() {
    }
    
    public void accept(PythonConstruct construct) {
        if (construct instanceof ReturnsAffector)
            ((ReturnsAffector) construct).actOnReturns(this);
    }
    
    public void add(PythonConstruct construct) {
        returns.add(construct);
    }
    
    public PythonConstruct[] returns() {
        return returns.toArray(new PythonConstruct[returns.size()]);
    }
    
    public void enter(PythonConstruct construct, ContinuationRequestor requestor,
            VisitorRequestor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> continuation) {
        accept(construct);
        continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
}
