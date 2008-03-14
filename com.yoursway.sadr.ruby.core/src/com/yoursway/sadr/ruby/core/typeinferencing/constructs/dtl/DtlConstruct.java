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
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.AbstractConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.ControlFlowGraph;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;

public abstract class DtlConstruct<N extends ASTNode> extends AbstractConstruct {
    
    protected final N node;
    
    public DtlConstruct(StaticContext sc, N node) {
        super(sc);
        this.node = node;
    }
    
    @Override
    public IConstruct staticallyEnclosingConstruct() {
        // FIXME: context might have changed!
        return wrap(staticContext(), staticContext().parentNodeOf(node));
    }
    
    public N node() {
        return node;
    }
    
    @Override
    protected IConstruct wrap(StaticContext sc, ASTNode node) {
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
        throw new RuntimeException("No construct found for node " + node.getClass());
    }
    
    private IConstruct wrapCall(StaticContext sc, CallExpression node) {
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
    
    private IConstruct wrapBinaryExpression(StaticContext sc, RubyBinaryExpression e) {
        return new UnhandledC(sc, e);
    }
    
    @Override
    public IConstruct subconstructFor(ASTNode node) {
        StaticContext subcontext = staticContext().subcontextFor(node);
        return wrap(subcontext, node);
    }
    
    public void calculateEffectiveControlFlowGraph(ContinuationRequestor requestor,
            ControlFlowGraphRequestor continuation) {
        List<IConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
        continuation.process(new ControlFlowGraph(constructs), requestor);
    }
    
    protected List<IConstruct> enclosedConstructs() {
        return wrap(staticContext(), enclosedNodes());
    }
    
    @SuppressWarnings("unchecked")
    protected List<ASTNode> enclosedNodes() {
        return node.getChilds();
    }
    
    protected static final Predicate<IConstruct> NOT_METHOD = new Predicate<IConstruct>() {
        
        public boolean apply(IConstruct t) {
            return !(t instanceof MethodDeclarationC);
        }
        
    };
    
}
