package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.ast.RubyReturnStatement;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.python.core.ast.visitor.RubyControlFlowTraverser;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.RubyBuiltinMethod;
import com.yoursway.sadr.python.core.runtime.RubyBuiltinProcedure;
import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DynamicMethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DynamicProcedureScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ProcedureScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class CallableReturnValueInfoGoal extends AbstractValueInfoGoal {
    
    private final Callable callable;
    private final ValueInfo receiver;
    private final ValueInfo[] arguments;
    private final InfoKind kind;
    
    public CallableReturnValueInfoGoal(Callable callable, InfoKind kind, ValueInfo receiver, ValueInfo[] args) {
        this.callable = callable;
        this.kind = kind;
        this.receiver = receiver;
        this.arguments = args;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        if (callable.isBuiltin()) {
            if (callable instanceof RubyBuiltinMethod) {
                RubyBuiltinMethod method = (RubyBuiltinMethod) callable;
                consume(method.evaluateBuiltin(receiver, arguments), requestor);
            } else {
                RubyBuiltinProcedure method = (RubyBuiltinProcedure) callable;
                consume(method.evaluateBuiltin(arguments), requestor);
            }
            return;
        }
        Construct<Scope, ASTNode> construct = callable.construct();
        if (construct != null) {
            Scope staticScope = construct.scope();
            Scope scope;
            if (staticScope instanceof MethodScope)
                scope = new DynamicMethodScope((MethodScope) staticScope, receiver, arguments);
            else
                scope = new DynamicProcedureScope((ProcedureScope) staticScope, arguments);
            final ReturnsVisitor visitor = new ReturnsVisitor(scope);
            new RubyControlFlowTraverser(this, construct.scope()).traverse(construct.node(), requestor,
                    visitor, new SimpleContinuation() {
                        
                        public void run(ContinuationRequestor requestor) {
                            requestor.subgoal(new MergeConstructsValueInfosContinuation(thou(), visitor
                                    .returns(), kind, CallableReturnValueInfoGoal.this));
                        }
                        
                    });
        }
    }
    
    class ReturnsVisitor extends RubyAstVisitor<ASTNode> {
        
        private final Collection<Construct<?, ?>> returns = new ArrayList<Construct<?, ?>>();
        
        private final Scope scope;
        
        public ReturnsVisitor(Scope scope) {
            super(null);
            this.scope = scope;
        }
        
        public Construct<?, ?>[] returns() {
            return returns.toArray(new Construct<?, ?>[returns.size()]);
        }
        
        @Override
        protected RubyAstVisitor<?> enterReturnStatement(RubyReturnStatement node) {
            ASTNode expr = (ASTNode) node.getValue().getChilds().get(0);
            if (expr instanceof RubyCallArgument) {
                RubyCallArgument argument = (RubyCallArgument) expr;
                returns.add(new Construct<Scope, ASTNode>(scope, argument.getValue()));
                
            }
            return null;
        }
        
    }
    
    @Override
    public String describeParameters() {
        return "" + callable;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arguments);
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
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
        final CallableReturnValueInfoGoal other = (CallableReturnValueInfoGoal) obj;
        if (!Arrays.equals(arguments, other.arguments))
            return false;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        return true;
    }
    
    public int debugSlot() {
        return 1;
    }
    
    public Goal cloneGoal() {
        return new CallableReturnValueInfoGoal(callable, kind, receiver, arguments);
    }
    
    @Override
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return true;
    }
    
}
