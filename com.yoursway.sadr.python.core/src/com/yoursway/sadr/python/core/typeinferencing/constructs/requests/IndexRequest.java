package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.python.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonStaticContext;
import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;

public class IndexRequest implements
        Request<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>, AssignmentInfoRequestor {
    
    private final AbstractMultiMap<String, CallC> procedureCalls;
    
    private final AbstractMultiMap<String, CallC> methodsCalls;
    
    private final AbstractMultiMap<String, AssignmentInfo> assignments;
    
    public IndexRequest(AbstractMultiMap<String, CallC> methodsCalls,
            AbstractMultiMap<String, CallC> procedureCalls,
            AbstractMultiMap<String, AssignmentInfo> assignments) {
        this.methodsCalls = methodsCalls;
        this.procedureCalls = procedureCalls;
        this.assignments = assignments;
    }
    
    public void accept(PythonConstruct construct) {
        if (construct instanceof IndexAffector)
            ((IndexAffector) construct).actOnIndex(this);
    }
    
    public void addMethodCall(String name, CallC construct) {
        Assert.isNotNull(name);
        methodsCalls.put(name, construct);
    }
    
    public void addProcedureCall(String name, CallC construct) {
        Assert.isNotNull(name);
        procedureCalls.put(name, construct);
    }
    
    public ContinuationRequestorCalledToken enter(PythonConstruct construct, ContinuationScheduler requestor,
            VisitorRequestor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> continuation) {
        accept(construct);
        return continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
    public void accept(AssignmentInfo info) {
        assignments.put(info.variableName(), info);
    }
    
}
