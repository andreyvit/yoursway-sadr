package org.eclipse.dltk.python.parser.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;

public class PythonArrayAccessExpression extends Expression implements ExtendedVariableReferenceInterface{

	private final Expression array;
	private final Expression index;

	public PythonArrayAccessExpression(Expression array, Expression index) {
		if (array == null || index == null)
			throw new IllegalArgumentException();
		this.array = array;
		this.index = index;
	}
	
	public Expression getArray() {
        return array;
    }
	
	public Expression getIndex() {
        return index;
    }
	
	@Override
	public int getKind() {
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			array.traverse(visitor);
			if(index != null)
				index.traverse(visitor);
			visitor.endvisit(this);
		}
	}
	
	public List getFlatNodeList() {
		List list;
		if(array instanceof ExtendedVariableReferenceInterface){
			list = ((ExtendedVariableReferenceInterface) array).getFlatNodeList();
		} else {
			list = new ArrayList();
			list.add(array);
		}
		list.add(new IndexHolder(this.sourceStart(), this.sourceEnd(), index));
		return list;
	}
	
	@Override
	public String toString() {
		return array+"["+index+"]";
	}
	
	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn(toString());
	}
}
