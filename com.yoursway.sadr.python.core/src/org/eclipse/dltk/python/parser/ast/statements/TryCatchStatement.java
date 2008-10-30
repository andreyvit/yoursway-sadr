/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.statements;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Try catch statement.
 */
public class TryCatchStatement extends Statement {
	private Block fBody;

	private Expression fExpression;

	public TryCatchStatement(DLTKToken catchToken,
			Expression exceptionExpression, Block body) {
		super(catchToken);
		this.fExpression = exceptionExpression;
		this.fBody = body;
	}

	public int getKind() {
		return Statement.S_TRY_CATCH;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (this.fExpression != null) {
				this.fExpression.traverse(pVisitor);
			}
			if (this.fBody != null) {
				this.fBody.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public Expression getExpression() {
		return this.fExpression;
	}

	public Block getBody() {
		return this.fBody;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("catch:");
		if (this.fExpression != null) {
			this.fExpression.printNode(output);
		}
		if (this.fBody != null) {
			this.fBody.printNode(output);
		}
	}
}
