package org.eclipse.dltk.python.parser.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.utils.CorePrinter;

public class PythonVariableAccessExpression extends Expression implements ExtendedVariableReferenceInterface{

	private final Expression receiver;
	private final VariableReference variable;

	public PythonVariableAccessExpression(Expression receiver, VariableReference variable) {
		if (receiver == null || variable == null)
			throw new IllegalArgumentException();
		this.receiver = receiver;
		this.variable = variable;
	}
	
	public Expression receiver() {
		return receiver;
	}
	
	public VariableReference variable() {
		return variable;
	}
	
	public String fqnRepresentation() {
        return receiver.toString() + "." + variable.getName();
	}	
	
	@Override
	public int getKind() {
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			receiver.traverse(visitor);
			variable.traverse(visitor);
			visitor.endvisit(this);
		}
	}

	public List getFlatNodeList() {
		List list;
		if(receiver instanceof ExtendedVariableReferenceInterface){
			list = ((ExtendedVariableReferenceInterface) receiver).getFlatNodeList();
		} else {
			list = new ArrayList();
			list.add(receiver);
		}
		list.add(variable);
		return list;
	}

	public ASTNode getReceiver() {
		return receiver;
	}

	public String getName() {
		return variable.getName();
	}
	
	@Override
	public String toString() {
	    return fqnRepresentation();
	}
	
	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn(toString());
	}

}
