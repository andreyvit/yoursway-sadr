package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.propagation.ConstructBoundGoal;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonStaticContext;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DtlArgumentVariable;

public class ExpressionValueInfoGoal extends AbstractValueInfoGoal implements ValueInfoGoal,
        ConstructBoundGoal<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>,
        BackwardPropagationEntryPoint, ValueInfoContinuation {
    
    private final InfoKind kind;
    
    private final PythonConstruct construct;
    
    private final PythonDynamicContext dc;
    
    public ExpressionValueInfoGoal(PythonConstruct construct, PythonDynamicContext dc, InfoKind kind) {
        if (construct == null)
            throw new NullPointerException();
        if (dc == null)
            throw new NullPointerException();
        this.dc = dc;
        this.construct = construct;
        this.kind = kind;
    }
    
    public ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor) {
        return construct.evaluateValue(dc, kind, requestor, this);
    }
    
    @Override
    public String describeParameters() {
        return construct + " @ " + dc;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((construct == null) ? 0 : construct.hashCode());
        result = prime * result + ((dc == null) ? 0 : dc.hashCode());
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        ExpressionValueInfoGoal other = (ExpressionValueInfoGoal) obj;
        if (construct == null) {
            if (other.construct != null)
                return false;
        } else if (!construct.equals(other.construct))
            return false;
        if (dc == null) {
            if (other.dc != null)
                return false;
        } else if (!dc.equals(other.dc))
            return false;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        return true;
    }
    
    public int debugSlot() {
        return 2;
    }
    
    public PythonConstruct construct() {
        return construct;
    }
    
    public boolean backwardPropagation(final Goal goal, ContinuationScheduler requestor,
            final ValueInfoContinuation continuation) {
        if (construct instanceof CallC && goal instanceof ArgumentVariableValueInfoGoal) {
            CallC callC = (CallC) construct;
            final ArgumentVariableValueInfoGoal argg = (ArgumentVariableValueInfoGoal) goal;
            DtlArgumentVariable variable = argg.variable();
            int index = variable.index();
            List<PythonConstruct> arguments = callC.arguments();
            if (index >= arguments.size())
                continuation.consume(emptyValueInfo(), requestor);
            else {
                final PythonConstruct arg = arguments.get(index);
                requestor.schedule(new Continuation() {
                    
                    ValueInfoGoal argGoal = new ExpressionValueInfoGoal(arg, dc, argg.infoKind());
                    
                    public void provideSubgoals(SubgoalRequestor requestor) {
                        requestor.subgoal(argGoal);
                    }
                    
                    public void done(ContinuationScheduler requestor) {
                        continuation.consume(argGoal.result(goal), requestor);
                    }
                    
                });
            }
            return true;
        } else {
            return false;
        }
    }
    
    public Goal cloneGoal() {
        return new ExpressionValueInfoGoal(construct, dc, kind);
    }
    
}
