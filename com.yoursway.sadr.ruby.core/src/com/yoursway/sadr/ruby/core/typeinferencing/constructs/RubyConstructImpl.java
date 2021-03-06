package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import static com.yoursway.sadr.engine.util.Lists.filter;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.sun.mirror.declaration.MethodDeclaration;
import com.yoursway.sadr.core.constructs.AbstractConstruct;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AccessInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public abstract class RubyConstructImpl<N extends ASTNode> extends
        AbstractConstruct<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> implements
        RubyConstruct {
    
    protected final N node;
    
    public RubyConstructImpl(RubyStaticContext sc, N node) {
        super(sc);
        this.node = node;
    }
    
    public RubyConstruct staticallyEnclosingConstruct() {
        throw new UnsupportedOperationException();
    }
    
    protected Scope nearestScope() {
        return staticContext().nearestScope();
    }
    
    public N node() {
        return node;
    }
    
    @Override
    protected RubyConstruct wrap(RubyStaticContext sc, ASTNode node) {
        if (node instanceof ModuleDeclaration)
            throw new RuntimeException("ModuleDeclaration cannot be wrapped with wrap()");
        if (node instanceof StringLiteral)
            return new StringLiteralC(sc, (StringLiteral) node);
        if (node instanceof NumericLiteral)
            return new IntegerLiteralC(sc, (NumericLiteral) node);
        if (node instanceof NilLiteral)
            return new NilLiteralC(sc, (NilLiteral) node);
        if (node instanceof RubySelfReference)
            return new SelfC(sc, (RubySelfReference) node);
        if (node instanceof RubySuperExpression)
            return new SuperC(sc, (RubySuperExpression) node);
        if (node instanceof VariableReference
                && ((VariableReference) node).getVariableKind() == RubyVariableKind.INSTANCE) {
            return new FieldAccessC(sc, (VariableReference) node);
        }
        if (node instanceof SimpleReference) {
            return new SymbolC(sc, (SimpleReference) node);
        }
        if (node instanceof CallExpression)
            return wrapCall(sc, (CallExpression) node);
        if (node instanceof RubyAssignment)
            return new AssignmentC(sc, (RubyAssignment) node);
        if (node instanceof RubyIfStatement)
            return new IfC(sc, (RubyIfStatement) node);
        if (node instanceof MethodDeclaration)
            return new MethodDeclarationC(sc, (MethodDeclaration) node);
        if (node instanceof RubyBinaryExpression)
            return wrapBinaryExpression(sc, (RubyBinaryExpression) node);
        if (node instanceof RubyClassDeclaration)
            return new ClassDeclarationC(sc, (RubyClassDeclaration) node);
        if (node instanceof RubyReturnStatement)
            return new ReturnC(sc, (RubyReturnStatement) node);
        //        if (node instanceof RubyArrayAccessExpression)
        //            return new ArrayAccessC(sc, (ArrayAccess) node);
        if (node instanceof ASTListNode || node instanceof RubyCallArgument
                || node instanceof RubyForStatement2 || node instanceof Block
                || node instanceof RubyMethodArgument || node instanceof RubyDotExpression
                || node instanceof RubyHashPairExpression)
            return new UnhandledC(sc, node);
        
        if (node == null)
            return new EmptyConstruct(sc);
        throw new RuntimeException("No construct found for node " + node.getClass().getSimpleName());
    }
    
    private RubyConstruct wrapCall(RubyStaticContext sc, CallExpression node) {
        if (node.getReceiver() == null)
            return new ProcedureCallC(sc, node);
        else {
            if (node.getName().equals("+")) {
                return new BinaryAdditionC(sc, node);
            }
            Comparison comparison = BinaryComparisonC.parseComparison(node.getName());
            if (comparison != null)
                return new BinaryComparisonC(sc, node, comparison);
            return new MethodCallC(sc, node);
        }
    }
    
    private RubyConstruct wrapBinaryExpression(RubyStaticContext sc, RubyBinaryExpression e) {
        return new UnhandledC(sc, e);
    }
    
    public RubyConstruct subconstructFor(ASTNode node) {
        RubyConstruct result = innerSubsonstructFor(node);
        if (result == null)
            throw new IllegalArgumentException("Subconstruct not found for node "
                    + node.getClass().getSimpleName() + " in " + this);
        return result;
    }
    
    RubyConstruct innerSubsonstructFor(ASTNode node) {
        if (isNode(node))
            return this;
        // TODO check offset here
        List<RubyConstruct> enclosedConstructs = enclosedConstructs();
        for (RubyConstruct c : enclosedConstructs) {
            RubyConstruct sc = ((RubyConstructImpl<?>) c).innerSubsonstructFor(node);
            if (sc != null)
                return sc;
        }
        return null;
    }
    
    protected boolean isNode(ASTNode node) {
        return node == this.node;
    }
    
    public ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(
            ContinuationScheduler requestor,
            ControlFlowGraphRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        List<RubyConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
        return continuation.process(
                new ControlFlowGraph<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode>(
                        constructs), requestor);
    }
    
    public List<RubyConstruct> enclosedConstructs() {
        return wrap(innerContext(), enclosedNodes());
    }
    
    protected RubyStaticContext innerContext() {
        return staticContext();
    }
    
    @SuppressWarnings("unchecked")
    protected List<ASTNode> enclosedNodes() {
        return node.getChilds();
    }
    
    protected static final Predicate<RubyConstruct> NOT_METHOD = new Predicate<RubyConstruct>() {
        
        public boolean apply(RubyConstruct t) {
            return !(t instanceof MethodDeclarationC);
        }
        
    };
    
    public Collection<AccessInfo> accessInfos() {
        return null;
    }
    
}