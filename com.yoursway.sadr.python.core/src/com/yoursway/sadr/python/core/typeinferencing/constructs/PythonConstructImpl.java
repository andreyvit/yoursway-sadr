package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.engine.util.Lists.filter;

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonForStatement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;
import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PrintExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTestListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;
import org.eclipse.dltk.python.parser.ast.statements.EmptyStatement;
import org.eclipse.dltk.python.parser.ast.statements.IfStatement;
import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;

import com.google.common.base.Predicate;
import com.yoursway.sadr.core.constructs.AbstractConstruct;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;

public abstract class PythonConstructImpl<N extends ASTNode> extends
        AbstractConstruct<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> implements
        PythonConstruct {
    
    protected final N node;
    
    public PythonConstructImpl(PythonStaticContext sc, N node) {
        super(sc);
        this.node = node;
    }
    
    public PythonConstruct staticallyEnclosingConstruct() {
        throw new UnsupportedOperationException();
    }
    
    public N node() {
        return node;
    }
    
    @Override
    protected PythonConstruct wrap(PythonStaticContext sc, ASTNode node) {
        if (node instanceof ModuleDeclaration)
            throw new RuntimeException("ModuleDeclaration cannot be wrapped with wrap()");
        if (node instanceof StringLiteral)
            return new StringLiteralC(sc, (StringLiteral) node);
        if (node instanceof NumericLiteral)
            return new IntegerLiteralC(sc, (NumericLiteral) node);
        if (node instanceof VariableReference)
            return wrapVariableReference(sc, (VariableReference) node);
        if (node instanceof PythonCallExpression) {
            PythonCallExpression expression = (PythonCallExpression) node;
            if (expression.getReceiver() == null)
                return new ProcedureCallC(sc, (PythonCallExpression) node);
            return new MethodCallC(sc, (PythonCallExpression) node);
        }
        if (node instanceof PythonVariableAccessExpression)
            return new FieldAccessC(sc, (PythonVariableAccessExpression) node);
        if (node instanceof ReturnStatement)
            return new ReturnC(sc, (ReturnStatement) node);
        if (node instanceof Assignment)
            return new AssignmentC(sc, (Assignment) node);
        if (node instanceof IfStatement)
            return new IfC(sc, (IfStatement) node);
        if (node instanceof MethodDeclaration)
            return new MethodDeclarationC(sc, (MethodDeclaration) node);
        if (node instanceof PythonClassDeclaration)
            return new ClassDeclarationC(sc, (PythonClassDeclaration) node);
        if (node instanceof BinaryExpression)
            return wrapBinaryExpression(sc, (BinaryExpression) node);
        if (node instanceof ASTListNode || node instanceof PythonForStatement || node instanceof Block
                || node instanceof PrintExpression || node instanceof EmptyStatement
                || node instanceof PythonArgument || node instanceof PythonListExpression
                || node instanceof PythonTestListExpression || node instanceof ExpressionList)
            return new UnhandledC(sc, node);
        throw new RuntimeException("No construct found for node " + node.getClass());
    }
    
    private PythonConstruct wrapVariableReference(PythonStaticContext sc, VariableReference node) {
        String name = node.getName();
        if (name.equals("self"))
            return new SelfC(sc, node);
        if (name.equals("super"))
            return new SuperC(sc, node);
        return new VariableReferenceC(sc, node);
    }
    
    private PythonConstruct wrapBinaryExpression(PythonStaticContext sc, BinaryExpression node) {
        String operator = node.getOperator();
        if (operator.equals("+"))
            return new BinaryAdditionC(sc, node);
        Comparison comparison = BinaryComparisonC.parseComparison(operator);
        if (comparison != null)
            return new BinaryComparisonC(sc, node, comparison);
        return new UnhandledC(sc, node);
    }
    
    public PythonConstruct subconstructFor(ASTNode node) {
        PythonConstruct result = innerSubsonstructFor(node);
        if (result == null)
            throw new IllegalArgumentException("Subconstruct not found for node "
                    + node.getClass().getSimpleName() + " in " + this);
        return result;
    }
    
    PythonConstruct innerSubsonstructFor(ASTNode node) {
        if (isNode(node))
            return this;
        // TODO check offset here
        List<PythonConstruct> enclosedConstructs = enclosedConstructs();
        for (PythonConstruct c : enclosedConstructs) {
            PythonConstruct sc = ((PythonConstructImpl<?>) c).innerSubsonstructFor(node);
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
            ControlFlowGraphRequestor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> continuation) {
        List<PythonConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
        continuation.process(
                new ControlFlowGraph<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>(
                        constructs), requestor);
    }
    
    public List<PythonConstruct> enclosedConstructs() {
        return wrap(innerContext(), enclosedNodes());
    }
    
    protected PythonStaticContext innerContext() {
        return staticContext();
    }
    
    @SuppressWarnings("unchecked")
    protected List<ASTNode> enclosedNodes() {
        return node.getChilds();
    }
    
    protected static final Predicate<PythonConstruct> NOT_METHOD = new Predicate<PythonConstruct>() {
        
        public boolean apply(PythonConstruct t) {
            return !(t instanceof MethodDeclarationC);
        }
        
    };
    
}
