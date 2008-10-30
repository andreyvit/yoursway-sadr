package org.eclipse.dltk.python.parser.ast;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;

public class PythonWithStatement extends Statement
{

	private Expression what;
	private Expression as;
	private Block block;
	
	public PythonWithStatement(DLTKToken token, Expression what, Expression as, Block block, int start, int end)
	{
		super(token);
		setStart(start);
		setEnd(end);
		this.what = what;
		this.as = as;
		this.block = block;
	}

	public int getKind()
	{
		return PythonConstants.S_WITH;
	}

	public void traverse(ASTVisitor visitor) throws Exception
	{
		if (visitor.visit(this))
		{
			what.traverse(visitor);
			if (null != as) as.traverse(visitor);
			block.traverse(visitor);
			visitor.endvisit(this);
		}
	}
}
