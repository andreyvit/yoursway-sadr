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
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * While statement.
 */
public class UntilStatement extends Statement {
	private Statement fCondition;
	private Statement fAction;

	public UntilStatement(DLTKToken token) {
		super(token);
	}

	public UntilStatement(DLTKToken whileToken, Expression condition,
			Statement action) {
		this.setStart(whileToken.getColumn());
		this.fCondition = condition;
		this.fAction = action;
	}
	
	public UntilStatement(Statement condition, Statement action) {
		this.fCondition = condition;
		this.fAction = action;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (fCondition != null) {
				fCondition.traverse(pVisitor);
			}
			if (fAction != null) {
				fAction.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public int getKind() {
		return S_UNTIL;
	}

	public Statement getCondition() {
		return fCondition;
	}

	public Statement getAction() {
		return fAction;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("while");
		if (this.fCondition != null) {
			this.fCondition.printNode(output);
		}
		if (this.fAction != null) {
			output.indent();
			this.fAction.printNode(output);
			output.dedent();
		}
		output.formatPrint("");
	}
}
