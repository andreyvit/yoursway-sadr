package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonFileC;
import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DtlArgumentVariable;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class ExpressionValueInfoGoal extends AbstractValueInfoGoal implements ValueInfoGoal,
        ConstructBoundGoal, BackwardPropagationEntryPoint, ValueInfoContinuation {
    
    private final Scope scope;
    
    private final ASTNode node;
    
    private final InfoKind kind;
    
    public ExpressionValueInfoGoal(Scope scope, ASTNode node, InfoKind kind) {
        this.kind = kind;
        if (scope == null)
            throw new NullPointerException();
        if (node == null)
            throw new NullPointerException();
        this.scope = scope;
        this.node = node;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        PythonFileC fileC = new PythonFileC(scope.fileScope(), scope.fileScope().node());
        IConstruct construct = fileC.subconstructFor(node);
        construct.evaluateValue(scope, kind, requestor, this);
    }
    
    @Override
    public String describeParameters() {
        return node + " @ " + scope;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ExpressionValueInfoGoal other = (ExpressionValueInfoGoal) obj;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        if (node == null) {
            if (other.node != null)
                return false;
        } else if (!node.equals(other.node))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        } else if (!scope.equals(other.scope))
            return false;
        return true;
    }
    
    public int debugSlot() {
        return 2;
    }
    
    public Construct<Scope, ASTNode> construct() {
        return new Construct<Scope, ASTNode>(scope, node);
    }
    
    public boolean backwardPropagation(final Goal goal, ContinuationRequestor requestor,
            final ValueInfoContinuation continuation) {
        if (node instanceof CallExpression && goal instanceof ArgumentVariableValueInfoGoal) {
            final ArgumentVariableValueInfoGoal argg = (ArgumentVariableValueInfoGoal) goal;
            CallExpression call = (CallExpression) node;
            DtlArgumentVariable variable = argg.variable();
            int index = variable.index();
            ASTNode[] arguments = RubyUtils.argumentsOf(call);
            if (index >= arguments.length)
                continuation.consume(emptyValueInfo(), requestor);
            else {
                final ASTNode arg = arguments[index];
                requestor.subgoal(new Continuation() {
                    
                    ValueInfoGoal argGoal = new ExpressionValueInfoGoal(scope, arg, argg.infoKind());
                    
                    public void provideSubgoals(SubgoalRequestor requestor) {
                        requestor.subgoal(argGoal);
                    }
                    
                    public void done(ContinuationRequestor requestor) {
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
        return new ExpressionValueInfoGoal(scope, node, kind);
    }
    
}
