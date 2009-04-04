package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.BigNumericLiteral;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonAssertStatement;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;
import org.eclipse.dltk.python.parser.ast.PythonCallArgumentsList;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonDelStatement;
import org.eclipse.dltk.python.parser.ast.PythonExceptStatement;
import org.eclipse.dltk.python.parser.ast.PythonForStatement;
import org.eclipse.dltk.python.parser.ast.PythonGlobalStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportFromStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportStatement;
import org.eclipse.dltk.python.parser.ast.PythonRaiseStatement;
import org.eclipse.dltk.python.parser.ast.PythonTryStatement;
import org.eclipse.dltk.python.parser.ast.PythonWhileStatement;
import org.eclipse.dltk.python.parser.ast.PythonWithStatement;
import org.eclipse.dltk.python.parser.ast.PythonYieldStatement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;
import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;
import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;
import org.eclipse.dltk.python.parser.ast.expressions.PrintExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonAllImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonDictExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonForListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonFunctionDecorator;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonListForExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonSubscriptExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTestListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTupleExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;
import org.eclipse.dltk.python.parser.ast.statements.BreakStatement;
import org.eclipse.dltk.python.parser.ast.statements.ContinueStatement;
import org.eclipse.dltk.python.parser.ast.statements.EmptyStatement;
import org.eclipse.dltk.python.parser.ast.statements.ExecStatement;
import org.eclipse.dltk.python.parser.ast.statements.IfStatement;
import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;
import org.eclipse.dltk.python.parser.ast.statements.TryFinallyStatement;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.support.Comparison;
import com.yoursway.sadr.python.analysis.lang.constructs.support.ConstructNotFoundForNode;
import com.yoursway.utils.bugs.Bugs;

public class PythonConstructFactory {
    
    public static List<PythonConstruct> wrap(List<ASTNode> enclosedNodes, PythonLexicalContext staticContext,
            PythonConstructImpl<?> parent) {
        ArrayList<PythonConstruct> list = new ArrayList<PythonConstruct>();
        if (enclosedNodes != null)
            for (ASTNode node : enclosedNodes) {
                PythonConstruct construct = PythonConstructFactory.wrap(node, staticContext, parent);
                list.add(construct);
            }
        return list;
    }
    
    public static PythonConstruct wrap(ASTNode node, PythonLexicalContext staticContext,
            PythonConstructImpl<?> parent) {
        if (node instanceof ModuleDeclaration)
            throw new RuntimeException("ModuleDeclaration cannot be wrapped with wrap()");
        if (node instanceof StringLiteral)
            return new StringLiteralC(staticContext, (StringLiteral) node, parent);
        if (node instanceof NumericLiteral)
            return new IntegerLiteralC(staticContext, (NumericLiteral) node, parent);
        if (node instanceof ComplexNumericLiteral)
            return new ComplexLiteralC(staticContext, (ComplexNumericLiteral) node, parent);
        if (node instanceof FloatNumericLiteral)
            return new FloatLiteralC(staticContext, (FloatNumericLiteral) node, parent);
        if (node instanceof BigNumericLiteral)
            return new BigIntegerLiteralC(staticContext, (BigNumericLiteral) node, parent);
        if (node instanceof VariableReference)
            return new VariableReferenceC(staticContext, ((VariableReference) node), parent);
        if (node instanceof PythonCallExpression)
            return new CallC(staticContext, (PythonCallExpression) node, parent);
        if (node instanceof PythonVariableAccessExpression)
            return new FieldAccessC(staticContext, (PythonVariableAccessExpression) node, parent);
        if (node instanceof PythonArrayAccessExpression)
            return new ArrayAccessC(staticContext, (PythonArrayAccessExpression) node, parent);
        if (node instanceof ReturnStatement)
            return new ReturnC(staticContext, (ReturnStatement) node, parent);
        if (node instanceof Assignment)
            return new AssignmentC(staticContext, (Assignment) node, parent);
        if (node instanceof IfStatement)
            return new IfC(staticContext, (IfStatement) node, parent);
        if (node instanceof MethodDeclaration)
            return new MethodDeclarationC(staticContext, (MethodDeclaration) node, parent);
        if (node instanceof PythonClassDeclaration)
            return new ClassDeclarationC(staticContext, (PythonClassDeclaration) node, parent);
        if (node instanceof PythonLambdaExpression)
            return new PythonLambdaExpressionC(staticContext, (PythonLambdaExpression) node, parent);
        if (node instanceof PythonListExpression)
            return new PythonListExpressionC(staticContext, (PythonListExpression) node, parent);
        if (node instanceof PythonTupleExpression)
            return new PythonTupleExpressionC(staticContext, (PythonTupleExpression) node, parent);
        if (node instanceof PythonDictExpression)
            return new PythonDictExpressionC(staticContext, (PythonDictExpression) node, parent);
        if (node instanceof BinaryExpression)
            return wrapBinaryExpression(staticContext, (BinaryExpression) node, parent);
        if (node instanceof UnaryExpression)
            return wrapUnaryExpression(staticContext, (UnaryExpression) node, parent);
        if (node instanceof PythonCallArgument)
            return new CallArgumentC(staticContext, (PythonCallArgument) node, parent);
        if (node instanceof PythonImportFromStatement)
            return new ImportFromC(staticContext, (PythonImportFromStatement) node, parent);
        if (node instanceof PythonImportStatement)
            return new ImportModuleC(staticContext, (PythonImportStatement) node, parent);
        if (node instanceof PythonArgument)
            return new ArgumentC(staticContext, (PythonArgument) node, parent);
        if (node instanceof PythonGlobalStatement)
            return new GlobalC(staticContext, (PythonGlobalStatement) node, parent);
        if (node instanceof ASTListNode || node instanceof PythonForStatement || node instanceof Block
                || node instanceof EmptyStatement || node instanceof PythonTestListExpression
                || node instanceof PythonImportExpression || node instanceof PythonAllImportExpression
                || node instanceof PythonArgument || node instanceof PythonDelStatement
                || node instanceof PythonFunctionDecorator || node instanceof PythonWithStatement
                || node instanceof PythonRaiseStatement || node instanceof PythonTestListExpression
                || node instanceof PythonVariableAccessExpression || node instanceof PythonListForExpression
                || node instanceof PythonTestListExpression || node instanceof PythonSubscriptExpression
                || node instanceof PythonWhileStatement || node instanceof PythonYieldStatement
                || node instanceof PythonTryStatement || node instanceof PythonExceptStatement
                || node instanceof ContinueStatement || node instanceof PythonForListExpression
                || node instanceof BreakStatement || node instanceof ExecStatement
                || node instanceof TryFinallyStatement || node instanceof PythonAssertStatement) {
            return new UnhandledC(staticContext, node, parent);
        }
        Bugs.bug(new ConstructNotFoundForNode(node));
        return new UnhandledC(staticContext, node, parent);
    }
    
    private static PythonConstruct wrapBinaryExpression(PythonLexicalContext sc, BinaryExpression node,
            PythonConstructImpl<?> parent) {
        String operator = node.getOperator();
        if ("+".equals(operator)) {
            PythonCallArgumentsList arglist = new PythonCallArgumentsList();
            arglist.addArgument(node.getRight(), 0);
            return new CallC(sc, new PythonCallExpression(new PythonVariableAccessExpression(
                    (Expression) node.getLeft(), new VariableReference(-1, -1, "__add__")), arglist), parent);
        }
        if (BinaryOperationC.isBinaryOperation(operator))
            return new BinaryOperationC(sc, node, parent);
        if (BinaryOperationC.isBinAssOperation(operator))
            return new BinaryAssignmentC(sc, node, parent);
        Comparison comparison = BinaryComparisonC.parseComparison(operator);
        if (comparison != null)
            return new BinaryComparisonC(sc, node, parent);
        System.out.println("Warning: no construct for node " + node.getClass().getSimpleName() + "("
                + node.getOperator() + ")");
        return new UnhandledC(sc, node, parent);
    }
    
    private static PythonConstruct wrapUnaryExpression(PythonLexicalContext sc, UnaryExpression node,
            PythonConstructImpl<?> parent) {
        String operator = node.getOperator();
        if (UnaryOperationC.isUnaryOperation(operator))
            return new UnaryOperationC(sc, node, parent);
        if (!(node instanceof PrintExpression))
            System.out.println("Warning: no construct for node " + node.getClass().getSimpleName() + "("
                    + node.getOperator() + ")");
        return new UnaryC(sc, node, parent);
    }
    
}
