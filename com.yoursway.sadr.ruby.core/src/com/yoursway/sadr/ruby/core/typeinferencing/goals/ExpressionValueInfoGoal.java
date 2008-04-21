package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.propagation.ConstructBoundGoal;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.DtlArgumentVariable;

public class ExpressionValueInfoGoal extends AbstractValueInfoGoal implements ValueInfoGoal,
        ConstructBoundGoal<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode>,
        BackwardPropagationEntryPoint, ValueInfoContinuation {
    
    private final InfoKind kind;
    
    private final RubyConstruct construct;
    
    private final RubyDynamicContext dc;
    
    public ExpressionValueInfoGoal(RubyConstruct construct, RubyDynamicContext dc, InfoKind kind) {
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
    
    public RubyConstruct construct() {
        return construct;
    }
    
    public boolean backwardPropagation(final Goal goal, ContinuationScheduler requestor,
            final ValueInfoContinuation continuation) {
        if (construct instanceof CallC && goal instanceof ArgumentVariableValueInfoGoal) {
            CallC callC = (CallC) construct;
            final ArgumentVariableValueInfoGoal argg = (ArgumentVariableValueInfoGoal) goal;
            DtlArgumentVariable variable = argg.variable();
            int index = variable.index();
            List<RubyConstruct> arguments = callC.arguments();
            if (index >= arguments.size())
                continuation.consume(ValueInfo.emptyValueInfo(), requestor);
            else {
                final RubyConstruct arg = arguments.get(index);
                requestor.schedule(new Continuation() {
                    
                    ValueInfoGoal argGoal = new ExpressionValueInfoGoal(arg, dc, argg.infoKind());
                    
                    public Goal[] provideSubgoals() {
                        return new Goal[] { argGoal };
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
