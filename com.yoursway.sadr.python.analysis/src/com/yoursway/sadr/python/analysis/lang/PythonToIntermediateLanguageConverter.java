package com.yoursway.sadr.python.analysis.lang;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.BigNumericLiteral;
import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonAssertStatement;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;
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

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.analysis.Range;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.ActualArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.Argument;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.CallArgument;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.Starness;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;
import com.yoursway.sadr.python.analysis.context.lexical.areas.ModuleArea;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.ClassScope;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.LambdaScope;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.MethodScope;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.ModuleScope;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.data.PassedReceiverArgumentInfo;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexAttributeWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AttributeUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.subscription.LiteralIntegerIndexUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.subscription.UnknownIndexUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ListLiteralUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.CallUnode;
import com.yoursway.sadr.python.analysis.objectmodel.types.ComplexType;
import com.yoursway.sadr.python.analysis.objectmodel.types.FloatType;
import com.yoursway.sadr.python.analysis.objectmodel.types.InstanceType;
import com.yoursway.sadr.python.analysis.objectmodel.types.LongType;
import com.yoursway.sadr.python.analysis.objectmodel.types.StringType;
import com.yoursway.sadr.python.analysis.objectmodel.types.UnicodeType;
import com.yoursway.sadr.python.analysis.objectmodel.values.FunctionObject;
import com.yoursway.sadr.python.analysis.objectmodel.values.IntegerValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.LambdaObject;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class PythonToIntermediateLanguageConverter {
    
    private final IndexRequest index;
    
    public PythonToIntermediateLanguageConverter(IndexRequest index) {
        if (index == null)
            throw new NullPointerException("index is null");
        this.index = index;
    }
    
    public PythonLexicalContext processModule(ModuleDeclaration node, SourceUnit sourceUnit) {
        PythonLexicalContext lc = new PythonLexicalContext(new ModuleScope(sourceUnit), new ModuleArea());
        processNodes(node.getStatements(), lc);
        return lc;
    }
    
    void processNodes(List<ASTNode> nodes, PythonLexicalContext lc) {
        for (ASTNode node : nodes)
            processNode(node, lc);
    }
    
    private Bnode processBNode(ASTNode node, PythonLexicalContext lc) {
        return bnode(processNode(node, lc), lc);
    }
    
    private Unode processNode(ASTNode node, PythonLexicalContext lc) {
        if (node == null)
            return null;
        
        // literals
        if (node instanceof StringLiteral)
            return processStringLiteral((StringLiteral) node);
        if (node instanceof NumericLiteral)
            return processIntegerLiteral((NumericLiteral) node);
        if (node instanceof ComplexNumericLiteral)
            return processComplexLiteral((ComplexNumericLiteral) node);
        if (node instanceof FloatNumericLiteral)
            return processFloatLiteral((FloatNumericLiteral) node);
        if (node instanceof BigNumericLiteral)
            return processBigIntegerLiteral((BigNumericLiteral) node);
        if (node instanceof PythonLambdaExpression)
            return processLambda((PythonLambdaExpression) node, lc);
        if (node instanceof PythonListExpression)
            return processList((PythonListExpression) node, lc);
        if (node instanceof PythonTupleExpression)
            return processTuple((PythonTupleExpression) node, lc);
        if (node instanceof PythonDictExpression)
            return processDict((PythonDictExpression) node, lc);
        
        // indexable unodes
        if (node instanceof VariableReference)
            return processVariableReference(((VariableReference) node));
        if (node instanceof PythonVariableAccessExpression)
            return processAttributeAccess((PythonVariableAccessExpression) node, lc);
        if (node instanceof PythonArrayAccessExpression)
            return processSubscriptionOrSlicing((PythonArrayAccessExpression) node, lc);
        
        // nodes with side effects
        if (node instanceof PythonCallExpression)
            return processCall((PythonCallExpression) node, lc);
        if (node instanceof Assignment)
            return processAssignment((Assignment) node, lc);
        if (node instanceof ReturnStatement)
            return processReturn((ReturnStatement) node, lc);
        
        // unprocessed
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
                || node instanceof TryFinallyStatement || node instanceof PythonAssertStatement)
            return null;
        
        if (node instanceof BinaryExpression)
            return processBinaryExpression((BinaryExpression) node, lc);
        if (node instanceof UnaryExpression)
            return processUnaryExpression((UnaryExpression) node, lc);
        
        // declarations
        if (node instanceof MethodDeclaration)
            processMethodDeclaration((MethodDeclaration) node, lc);
        else if (node instanceof PythonClassDeclaration)
            processClassDeclaration((PythonClassDeclaration) node, lc);
        else if (node instanceof IfStatement)
            processIf((IfStatement) node, lc);
        else if (node instanceof PythonImportFromStatement)
            processImportFrom((PythonImportFromStatement) node, lc);
        else if (node instanceof PythonImportStatement)
            processImportModule((PythonImportStatement) node, lc);
        else if (node instanceof PythonGlobalStatement)
            processGlobal((PythonGlobalStatement) node, lc);
        return null;
    }
    
    private void processGlobal(PythonGlobalStatement node, PythonLexicalContext lc) {
        for (VariableReference var : node.getDeclaredVariables())
            lc.getScope().addGlobalVariable(var.getName());
    }
    
    private void processImportModule(PythonImportStatement node, PythonLexicalContext lc) {
    }
    
    private void processImportFrom(PythonImportFromStatement node, PythonLexicalContext lc) {
    }
    
    private void processIf(IfStatement node, PythonLexicalContext lc) {
        processNode(node.getCondition(), lc);
        processNodes(node.getSaneThen(), lc);
        processNodes(node.getSaneElse(), lc);
    }
    
    private Unode processUnaryExpression(UnaryExpression node, PythonLexicalContext lc) {
        return null;
    }
    
    private Unode processBinaryExpression(BinaryExpression node, PythonLexicalContext lc) {
        String operator = node.getOperator();
        if ("+".equals(operator)) {
            // TODO too tedious, should be much simpler (say hello to effects?)   
            ActualArgumentsBuilder argumentsBuilder = new ActualArgumentsBuilder(lc);
            argumentsBuilder.addPositional(processBNode(node.getRight(), lc));
            Arguments arguments = argumentsBuilder.build();
            Unode receiver = processNode(node.getLeft(), lc);
            if (receiver == null)
                return null;
            else {
                Bnode callable = new Bnode(new AttributeUnode(receiver, "__add__"), lc);
                index.addPassedArgument(receiver, new PassedReceiverArgumentInfo(callable));
                return new CallUnode(callable, arguments);
            }
        }
        return null;
    }
    
    private Unode processClassDeclaration(PythonClassDeclaration node, PythonLexicalContext lc) {
        String name = node.getName();
        PythonLexicalContext inner = lc.with(new ClassScope(lc.getScope(), name, rangeOf(node)));
        InstanceType type = new InstanceType(name);
        new PythonToIntermediateLanguageConverter(index.with(new IndexAttributeWrappingStrategy(
                new VariableUnode(name)))).processNodes(node.getBody().getChilds(), inner);
        index.addAssignment(new VariableUnode(name), lc, new Bnode(new ScalarLiteralUnode(new PythonValueSet(
                type)), lc));
        return null;
    }
    
    private void processMethodDeclaration(MethodDeclaration node, PythonLexicalContext lc) {
        String name = node.getName();
        PythonLexicalContext inner = lc.with(new MethodScope(lc.getScope(), name, rangeOf(node))).with(
            new MethodArea());
        processNodes(node.getBody().getChilds(), inner);
        DeclaredArguments arguments = processArguments(node.getArguments(), lc);
        index.addAssignment(new VariableUnode(name), lc, new Bnode(new ScalarLiteralUnode(new PythonValueSet(
                new FunctionObject(name, arguments, inner))), lc));
        arguments.addToIndex(index, inner);
    }
    
    private Unode processReturn(ReturnStatement node, PythonLexicalContext lc) {
        Bnode value = processBNode(node.getExpression(), lc);
        MethodArea area = lc.getArea().getReturnableArea();
        if (area != null && value != null)
            index.addReturnedValue(area, value);
        return null;
    }
    
    private Unode processAssignment(Assignment node, PythonLexicalContext lc) {
        Unode lhs = processNode(node.getLeft(), lc);
        Bnode rhs = processBNode(node.getRight(), lc);
        if (lhs == null || rhs == null)
            return null;
        index.addAssignment(lhs, lc, rhs);
        return rhs.unode();
    }
    
    private Unode processCall(PythonCallExpression node, PythonLexicalContext lc) {
        Bnode callable = processBNode(node.getReceiver(), lc);
        Arguments arguments = processActualArguments(callable, node.getArgumentsAsList(), lc);
        if (callable == null)
            return null;
        return new CallUnode(callable, arguments);
    }
    
    private Unode processDict(PythonDictExpression node, PythonLexicalContext lc) {
        return null;
    }
    
    private Unode processTuple(PythonTupleExpression node, PythonLexicalContext lc) {
        return new ListLiteralUnode(processToBnodes(node.getChilds(), lc));
    }
    
    private Unode processList(PythonListExpression node, PythonLexicalContext lc) {
        return new ListLiteralUnode(processToBnodes(node.getChilds(), lc));
    }
    
    private Unode processLambda(PythonLambdaExpression node, PythonLexicalContext lc) {
        PythonLexicalContext inner = lc.with(new LambdaScope(lc.getScope(), rangeOf(node)));
        Unode body = processNode(node.getBodyExpression(), inner);
        DeclaredArguments arguments = processArguments(node.getArguments(), lc);
        if (body == null)
            return null;
        return new ScalarLiteralUnode(new PythonValueSet(new LambdaObject(arguments, new Bnode(body, lc))));
    }
    
    private Unode processSubscriptionOrSlicing(PythonArrayAccessExpression node, PythonLexicalContext lc) {
        Unode receiver = processNode(node.getArray(), lc);
        PythonSubscriptExpression subscript = (PythonSubscriptExpression) node.getIndex();
        ASTNode i = subscript.getIndexOrSliceStart();
        processNode(i, lc);
        processNode(subscript.getSliceEnd(), lc);
        processNode(subscript.getSliceStep(), lc);
        
        if (!subscript.isSubscription() || receiver == null)
            return null;
        else if (i instanceof NumericLiteral)
            return new LiteralIntegerIndexUnode(receiver, (int) ((NumericLiteral) i).getIntValue());
        else
            return new UnknownIndexUnode(receiver);
    }
    
    private Unode processAttributeAccess(PythonVariableAccessExpression node, PythonLexicalContext lc) {
        Unode receiver = processNode(node.getReceiver(), lc);
        if (receiver == null)
            return null;
        else {
            AttributeUnode me = new AttributeUnode(receiver, node.getName());
            index.addPassedArgument(receiver, new PassedReceiverArgumentInfo(new Bnode(me, lc)));
            return me;
        }
    }
    
    private Unode processVariableReference(VariableReference node) {
        return new VariableUnode(node.getName());
    }
    
    private Unode processBigIntegerLiteral(BigNumericLiteral node) {
        return new ScalarLiteralUnode(new PythonValueSet(LongType.wrap(node.getLongValue())));
    }
    
    private Unode processFloatLiteral(FloatNumericLiteral node) {
        return new ScalarLiteralUnode(new PythonValueSet(FloatType.wrap(node.getDoubleValue())));
    }
    
    private Unode processComplexLiteral(ComplexNumericLiteral node) {
        return new ScalarLiteralUnode(new PythonValueSet(ComplexType.wrap(0, node.getDoubleValue())));
    }
    
    private Unode processIntegerLiteral(NumericLiteral node) {
        return new ScalarLiteralUnode(new PythonValueSet(new IntegerValue(node.getIntValue())));
    }
    
    private Unode processStringLiteral(StringLiteral node) {
        String v = node.getValue();
        boolean isUnicode;
        if (v.startsWith("u") || v.startsWith("U")) {
            isUnicode = true;
            v = v.substring(1);
        } else {
            isUnicode = false;
        }
        v = PythonToIntermediateLanguageConverter.unquote(v);
        String value = v.replaceAll("\\n", "\n");
        return new ScalarLiteralUnode(new PythonValueSet(isUnicode ? UnicodeType.wrap(value) : StringType
                .wrap(value)));
    }
    
    private DeclaredArguments processArguments(List<PythonArgument> args, PythonLexicalContext lc) {
        DeclaredArgumentsBuilder builder = new DeclaredArgumentsBuilder();
        int i = 0;
        for (PythonArgument arg : args)
            new Argument(arg.getName(), i++, Starness.fromCount(arg.getStar()), bnode(processNode(arg
                    .getInitialization(), lc), lc)).addTo(builder);
        return builder.build();
    }
    
    private Arguments processActualArguments(Bnode callable, List<PythonCallArgument> args,
            PythonLexicalContext lc) {
        ActualArgumentsBuilder builder = new ActualArgumentsBuilder(lc);
        int i = 0;
        for (PythonCallArgument arg : args)
            processCallArgument(callable, arg, lc, i++).addTo(builder);
        return builder.build();
    }
    
    private CallArgument processCallArgument(Bnode callable, PythonCallArgument arg, PythonLexicalContext lc,
            int i) {
        ASTNode value = arg.getValue();
        if (value == null)
            throw new IllegalArgumentException("call argument's value() should be != null");
        String name;
        Unode v;
        if (value instanceof Assignment) {
            Assignment assignment = (Assignment) value;
            SimpleReference lhs = (SimpleReference) assignment.getLeft();
            name = lhs.getName();
            v = processNode(assignment.getRight(), lc);
        } else {
            name = null;
            v = processNode(value, lc);
        }
        if (v == null)
            v = ScalarLiteralUnode.UNKNOWN;
        CallArgument result = new CallArgument(new Bnode(v, lc), name, arg.getStar());
        result.actOnIndex(index, callable, i);
        return result;
    }
    
    private static Bnode bnode(Unode unode, PythonLexicalContext lc) {
        if (unode == null)
            return null;
        return new Bnode(unode, lc);
    }
    
    private List<Bnode> processToBnodes(List<ASTNode> nodes, PythonLexicalContext lc) {
        List<Bnode> result = new ArrayList<Bnode>(nodes.size());
        for (ASTNode item : nodes)
            result.add(processBNode(item, lc));
        return result;
    }
    
    public static boolean isQuoted(String text, String quotes) {
        return text.startsWith(quotes) && text.endsWith(quotes);
    }
    
    public static String unquote(String text) {
        if (isQuoted(text, "\"\"\"") || isQuoted(text, "'''"))
            return text.substring(3, text.length() - 3);
        if (isQuoted(text, "\"") || isQuoted(text, "'") || isQuoted(text, "`"))
            return text.substring(1, text.length() - 1);
        return text;
    }
    
    private Range rangeOf(ASTNode node) {
        return new Range(node.sourceStart(), node.sourceEnd());
    }
    
}
