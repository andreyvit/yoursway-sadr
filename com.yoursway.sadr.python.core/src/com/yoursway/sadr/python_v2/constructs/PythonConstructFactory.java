package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.BigNumericLiteral;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonAssertStatement;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonDelStatement;
import org.eclipse.dltk.python.parser.ast.PythonExceptStatement;
import org.eclipse.dltk.python.parser.ast.PythonForStatement;
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

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class PythonConstructFactory {
    
    public static List<PythonConstruct> wrap(List<ASTNode> enclosedNodes, Scope scope) {
        ArrayList<PythonConstruct> list = new ArrayList<PythonConstruct>();
        for (ASTNode node : enclosedNodes) {
            PythonConstruct construct = PythonConstructFactory.wrap(node, scope);
            list.add(construct);
        }
        return list;
    }
    
    public static PythonConstruct wrap(ASTNode node, Scope scope) {
        if (node instanceof ModuleDeclaration)
            throw new RuntimeException("ModuleDeclaration cannot be wrapped with wrap()");
        if (node instanceof StringLiteral) {
            return new StringLiteralC(scope, (StringLiteral) node);
        }
        if (node instanceof NumericLiteral)
            return new IntegerLiteralC(scope, (NumericLiteral) node);
        if (node instanceof ComplexNumericLiteral)
            return new ComplexLiteralC(scope, (ComplexNumericLiteral) node);
        if (node instanceof FloatNumericLiteral)
            return new FloatLiteralC(scope, (FloatNumericLiteral) node);
        if (node instanceof BigNumericLiteral)
            return new BigIntegerLiteralC(scope, (BigNumericLiteral) node);
        if (node instanceof VariableReference)
            return new VariableReferenceC(scope, ((VariableReference) node));
        if (node instanceof PythonCallExpression) {
            //            PythonCallExpression expression = (PythonCallExpression) node;
            //            if (expression.getReceiver() == null)
            return new ProcedureCallC(scope, (PythonCallExpression) node);
            //            return new MethodCallC(scope, (PythonCallExpression) node);
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
        if (node instanceof PythonLambdaExpression)
            return new PythonLambdaExpressionC(scope, (PythonLambdaExpression) node);
        if (node instanceof PythonListExpression)
            return new PythonListExpressionC(scope, (PythonListExpression) node);
        if (node instanceof PythonTupleExpression)
            return new PythonTupleExpressionC(scope, (PythonTupleExpression) node);
        if (node instanceof PythonDictExpression)
            return new PythonDictExpressionC(scope, (PythonDictExpression) node);
        if (node instanceof BinaryExpression)
            return wrapBinaryExpression(scope, (BinaryExpression) node);
        if (node instanceof UnaryExpression)
            return wrapUnaryExpression(scope, (UnaryExpression) node);
        if (node instanceof PythonCallArgument)
            return new CallArgumentC(scope, (PythonCallArgument) node);
        if (node instanceof PythonImportFromStatement)
            return new ImportFromC(scope, (PythonImportFromStatement) node);
        if (node instanceof PythonImportStatement)
            return new ImportModuleC(scope, (PythonImportStatement) node);
        if (node instanceof PythonArgument)
            return new ArgumentC(scope, (PythonArgument) node);
        if (node instanceof ASTListNode || node instanceof PythonForStatement || node instanceof Block
                || node instanceof EmptyStatement || node instanceof PythonTestListExpression
                || node instanceof PythonImportExpression || node instanceof PythonAllImportExpression
                || node instanceof PythonArgument || node instanceof PythonDelStatement
                || node instanceof PythonFunctionDecorator || node instanceof PythonWithStatement
                || node instanceof PythonRaiseStatement || node instanceof PythonTestListExpression
                || node instanceof PythonVariableAccessExpression || node instanceof ExpressionList
                || node instanceof PythonSubscriptExpression || node instanceof PythonWhileStatement
                || node instanceof PythonYieldStatement || node instanceof PythonTryStatement
                || node instanceof PythonExceptStatement || node instanceof ContinueStatement
                || node instanceof PythonForListExpression || node instanceof BreakStatement
                || node instanceof ExecStatement || node instanceof TryFinallyStatement
                || node instanceof PythonAssertStatement) {
            //            System.out.println("Warning: no construct for node " + node.getClass().getSimpleName());
            return new UnhandledC(scope, node);
        }
        System.out.println("ERROR: no construct for node " + node.getClass().getSimpleName());
        return new UnhandledC(scope, node);
        //        throw new RuntimeException("No construct found for node " + node.getClass());
    }
    
    private static PythonConstruct wrapBinaryExpression(Scope sc, BinaryExpression node) {
        String operator = node.getOperator();
        if (BinaryOperationC.isBinaryOperation(operator))
            return new BinaryOperationC(sc, node);
        if (BinaryOperationC.isBinAssOperation(operator))
            return new BinaryAssignmentC(sc, node);
        Comparison comparison = BinaryComparisonC.parseComparison(operator);
        if (comparison != null)
            return new BinaryComparisonC(sc, node);
        System.out.println("Warning: no construct for node " + node.getClass().getSimpleName() + "("
                + node.getOperator() + ")");
        return new UnhandledC(sc, node);
    }
    
    private static PythonConstruct wrapUnaryExpression(Scope sc, UnaryExpression node) {
        String operator = node.getOperator();
        if (UnaryOperationC.isUnaryOperation(operator))
            return new UnaryOperationC(sc, node);
        if (!(node instanceof PrintExpression))
            System.out.println("Warning: no construct for node " + node.getClass().getSimpleName() + "("
                    + node.getOperator() + ")");
        return new UnaryC(sc, node);
    }
    
}
