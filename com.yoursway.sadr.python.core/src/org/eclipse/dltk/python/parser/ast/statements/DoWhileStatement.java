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
 * Do while statement.
 * 
 * 
 */
public class DoWhileStatement extends Statement {

	/**
	 * while condition.
	 */
	private Expression fCondition;

	/**
	 * Action Statement.
	 */
	private Statement fAction;

	/**
	 * Construct Do While statement from doToken, action, condition and final
	 * position.
	 * 
	 * @param doToken -
	 *            do ANTLR token.
	 * @param action -
	 *            action.
	 * @param condition -
	 *            condition.
	 * @param endPosition -
	 *            end position.
	 */
	public DoWhileStatement(DLTKToken doToken, Statement action,
			Expression condition, int endPosition) {
		super(doToken.getColumn(), endPosition);
		this.fAction = action;
		this.fCondition = condition;
	}

	/**
	 * Traverse
	 */
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (fAction != null) {
				fAction.traverse(pVisitor);
			}
			if (fCondition != null) {
				fCondition.traverse(pVisitor);
			}

			pVisitor.endvisit(this);
		}
	}

	/**
	 * Return Do While Kind.
	 */

	public int getKind() {
		return S_DOWHILE;
	}

	/**
	 * return Action.
	 * 
	 * @return - action statement.
	 */
	public Statement getAction() {
		return fAction;
	}

	/**
	 * Return condition.
	 * 
	 * @return - condition expression.
	 */
	public Expression getCondition() {
		return fCondition;
	}

	/**
	 * Testing purpose only. Print do while expression.
	 */

	public void printNode(CorePrinter output) {
		output.formatPrintLn("do:");
		output.indent();

		if (this.fAction != null) {
			this.fAction.printNode(output);
		}
		output.dedent();
		if (fCondition != null) {
			output.formatPrintLn("while: ");
			this.fCondition.printNode(output);
		}
	}
}
