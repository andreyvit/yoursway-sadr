package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.engine.util.Lists.filter;

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.NilLiteral;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyBinaryExpression;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.ast.RubyDotExpression;
import org.eclipse.dltk.ruby.ast.RubyForStatement2;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;
import org.eclipse.dltk.ruby.ast.RubyMethodArgument;
import org.eclipse.dltk.ruby.ast.RubyReturnStatement;
import org.eclipse.dltk.ruby.ast.RubySelfReference;
import org.eclipse.dltk.ruby.ast.RubySuperExpression;

import com.google.common.base.Predicate;
import com.yoursway.sadr.core.constructs.AbstractConstruct;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.EmptyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;

public abstract class DtlConstruct<N extends ASTNode> extends
        AbstractConstruct<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> implements
        RubyConstruct { //> rename to RubyConstructImpl

    protected final N node;
    
    public DtlConstruct(RubyStaticContext sc, N node) {
        super(sc);
        this.node = node;
    }
    
    public RubyConstruct staticallyEnclosingConstruct() {
        throw new UnsupportedOperationException();
    }
    
    public N node() {
        return node;
    }
    
    public RubyConstruct parent() {
        return staticContext().parentConstruct();
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
        if (node instanceof SimpleReference)
            return new SymbolC(sc, (SimpleReference) node);
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
        //        if (node instanceof RubyArrayAccessExpression)
        //            return new ArrayAccessC(sc, (ArrayAccess) node);
        if (node instanceof ASTListNode || node instanceof RubyReturnStatement
                || node instanceof RubyCallArgument || node instanceof RubyForStatement2
                || node instanceof Block || node instanceof RubyMethodArgument
                || node instanceof RubyDotExpression)
            return new UnhandledC(sc, node);
        
        if (node == null)
            return new EmptyConstruct(sc);
        throw new RuntimeException("No construct found for node " + node.getClass());
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
            RubyConstruct sc = ((DtlConstruct<?>) c).innerSubsonstructFor(node);
            if (sc != null)
                return sc;
        }
        return null;
    }
    
    protected boolean isNode(ASTNode node) {
        return node == this.node;
    }
    
    public void calculateEffectiveControlFlowGraph(
            ContinuationRequestor requestor,
            ControlFlowGraphRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        List<RubyConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
        continuation.process(
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
    
}