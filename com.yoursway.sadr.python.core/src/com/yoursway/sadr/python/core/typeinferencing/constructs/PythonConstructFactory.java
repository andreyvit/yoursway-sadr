package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.BigNumericLiteral;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonDelStatement;
import org.eclipse.dltk.python.parser.ast.PythonForStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportFromStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportStatement;
import org.eclipse.dltk.python.parser.ast.PythonRaiseStatement;
import org.eclipse.dltk.python.parser.ast.PythonWithStatement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;
import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PrintExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonAllImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonDictExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonFunctionDecorator;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportAsExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTestListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;
import org.eclipse.dltk.python.parser.ast.statements.EmptyStatement;
import org.eclipse.dltk.python.parser.ast.statements.IfStatement;
import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;

public class PythonConstructFactory {
    
    public static List<PythonConstruct> wrap(Scope scope, List<ASTNode> enclosedNodes) {
        ArrayList<PythonConstruct> list = new ArrayList<PythonConstruct>();
        for (ASTNode node : enclosedNodes) {
            PythonConstruct construct = PythonConstructFactory.wrapConstruct(node, scope);
            list.add(construct);
        }
        return list;
    }
    
    public static PythonConstruct wrapConstruct(ASTNode node, Scope scope) {
        if (node instanceof ModuleDeclaration)
            throw new RuntimeException("ModuleDeclaration cannot be wrapped with wrap()");
        if (node instanceof StringLiteral)
            return new StringLiteralC(scope, (StringLiteral) node);
        if (node instanceof NumericLiteral)
            return new IntegerLiteralC(scope, (NumericLiteral) node);
        if (node instanceof BigNumericLiteral)
            return new BigIntegerLiteralC(scope, (BigNumericLiteral) node);
        if (node instanceof VariableReference)
            return wrapVariableReference(scope, (VariableReference) node);
        if (node instanceof PythonCallExpression) {
            PythonCallExpression expression = (PythonCallExpression) node;
            if (expression.getReceiver() == null)
                return new ProcedureCallC(scope, (PythonCallExpression) node);
            return new MethodCallC(scope, (PythonCallExpression) node);
        }
        if (node instanceof PythonVariableAccessExpression)
            return new FieldAccessC(scope, (PythonVariableAccessExpression) node);
        if (node instanceof PythonArrayAccessExpression)
            return new ArrayAccessC(scope, (PythonArrayAccessExpression) node);
        if (node instanceof ReturnStatement)
            return new ReturnC(scope, (ReturnStatement) node);
        if (node instanceof Assignment)
            return new AssignmentC(scope, (Assignment) node);
        if (node instanceof IfStatement)
            return new IfC(scope, (IfStatement) node);
        if (node instanceof MethodDeclaration)
            return new MethodDeclarationC(scope, (MethodDeclaration) node);
        if (node instanceof PythonClassDeclaration)
            return new ClassDeclarationC(scope, (PythonClassDeclaration) node);
        if (node instanceof BinaryExpression)
            return wrapBinaryExpression(scope, (BinaryExpression) node);
        if (node instanceof ASTListNode || node instanceof PythonForStatement || node instanceof Block
                || node instanceof PrintExpression || node instanceof EmptyStatement
                || node instanceof PythonImportFromStatement || node instanceof PythonAllImportExpression
                || node instanceof PythonArgument || node instanceof PythonDelStatement
                || node instanceof PythonImportStatement || node instanceof PythonFunctionDecorator
                || node instanceof PythonDictExpression || node instanceof PythonWithStatement
                || node instanceof PythonLambdaExpression || node instanceof PythonRaiseStatement
                || node instanceof PythonImportExpression || node instanceof PythonImportAsExpression
                || node instanceof PythonListExpression || node instanceof PythonTestListExpression
                || node instanceof PythonVariableAccessExpression || node instanceof ExpressionList
                || node instanceof UnaryExpression || node instanceof PythonTestListExpression)
            return new UnhandledC(scope, node);
        throw new RuntimeException("No construct found for node " + node.getClass());
    }
    
    private static PythonConstruct wrapVariableReference(Scope sc, VariableReference node) {
        String repr = node.getName();
        if (repr != null && (repr.equals(BooleanValue.TRUE) || repr.equals(BooleanValue.FALSE)))
            return new BooleanLiteralC(sc, node);
        return new VariableReferenceC(sc, node);
    }
    
    private static PythonConstruct wrapBinaryExpression(Scope sc, BinaryExpression node) {
        String operator = node.getOperator();
        if (operator.equals("+"))
            return new BinaryAdditionC(sc, node);
        if (operator.equals("%"))
            return new BinaryPercentC(sc, node);
        Comparison comparison = BinaryComparisonC.parseComparison(operator);
        if (comparison != null)
            return new BinaryComparisonC(sc, node, comparison);
        return new UnhandledC(sc, node);
    }
    
}
