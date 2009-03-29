package org.eclipse.dltk.python.parser.ast.expressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;
import org.eclipse.dltk.python.parser.ast.PythonCallArgumentsList;
import org.eclipse.dltk.utils.CorePrinter;

public class PythonCallExpression extends Expression implements ExtendedVariableReferenceInterface {
    
    private final Expression function;
    private final PythonCallArgumentsList arguments;
    
    public PythonCallExpression(Expression function, PythonCallArgumentsList args) {
        if (function == null)
            throw new IllegalArgumentException();
        if (args == null)
            throw new IllegalArgumentException();
        this.function = function;
        this.arguments = args;
    }
    
    @Override
    public int getKind() {
        return 0;
    }
    
    @Override
    public void traverse(ASTVisitor visitor) throws Exception {
        if (visitor.visit(this)) {
            function.traverse(visitor);
            if (arguments != null) {
                arguments.traverse(visitor);
            }
            visitor.endvisit(this);
        }
    }
    
    public List getFlatNodeList() {
        List list;
        if (function instanceof ExtendedVariableReferenceInterface) {
            list = ((ExtendedVariableReferenceInterface) function).getFlatNodeList();
        } else {
            list = new ArrayList();
            list.add(function);
        }
        list.add(new CallHolder(this.sourceStart(), this.sourceEnd(), arguments));
        return list;
    }
    
    public PythonCallArgumentsList getArgs() {
        return arguments;
    }
    
    public ASTNode getReceiver() {
        //		if (function instanceof PythonVariableAccessExpression){
        //			PythonVariableAccessExpression expr = (PythonVariableAccessExpression) function;
        //			return expr.getReceiver();
        //		}
        return function;
    }
    
    public Expression getVariable() {
        return null;
        //		if (function instanceof PythonVariableAccessExpression){
        //			PythonVariableAccessExpression expr = (PythonVariableAccessExpression) function;
        //			return expr.variable();
        //		}
        //		return function;
    }
    
    @Override
    public String toString() {
        return function.toString() + "()";
    }
    
    public String getName() {
        return function.toString();
    }
    
    @Override
    public void printNode(CorePrinter output) {
        output.formatPrintLn(toString());
    }
    
    public List<PythonCallArgument> getArgumentsAsList() {
        if (arguments == null)
            return Collections.emptyList();
        return arguments.getChilds();
    }
    
}
