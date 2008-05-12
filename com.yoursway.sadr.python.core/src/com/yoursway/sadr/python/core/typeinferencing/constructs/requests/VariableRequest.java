package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;

public class VariableRequest implements
        Request<PythonConstruct, Scope, PythonDynamicContext, ASTNode>,
        AssignmentInfoRequestor, AssignmentInfoProvider {
    
    private final PythonVariable variable;
    
    private final Collection<AssignmentInfo> assigned = new ArrayList<AssignmentInfo>();
    
    public VariableRequest(PythonVariable variable, InfoKind infoKind) {
        this.variable = variable;
    }
    
    public PythonVariable variable() {
        return variable;
    }
    
    public void accept(PythonConstruct construct) {
        if (construct instanceof VariableAffector)
            ((VariableAffector) construct).actOnVariable(this);
    }
    
    public AssignmentInfo[] assigned() {
        return assigned.toArray(new AssignmentInfo[assigned.size()]);
    }
    
    public ContinuationRequestorCalledToken enter(PythonConstruct construct, ContinuationScheduler requestor,
            VisitorRequestor<PythonConstruct, Scope, PythonDynamicContext, ASTNode> continuation) {
        accept(construct);
        return continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
    public void accept(AssignmentInfo info) {
        if (matches(info.threesome()))
            add(info);
    }
    
    private boolean matches(MumblaWumblaThreesome threesome) {
        return variable.name().equals(threesome.variableName());
    }
    
    private void add(AssignmentInfo info) {
        assigned.add(info);
    }
    
}
