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
 * For Statement.
 * @deprecated
 */
public class ClassicalForStatement extends Statement {

	private Expression fInitialization;

	private Expression fCondition;

	private Expression fIncrement;

	private Statement fAction;

	/**
	 * Construct from dltk token, initialization, condition, increment
	 * expressions and action statement
	 * 
	 * @param forToken -
	 *            dltk token.
	 * @param initialization -
	 *            initialization expression.
	 * @param condition -
	 *            condition expression.
	 * @param increment -
	 *            increment expression.
	 * @param action -
	 *            action statement.
	 */
	public ClassicalForStatement(DLTKToken forToken, Expression initialization,
			Expression condition, Expression increment, Statement action) {

		this.fInitialization = initialization;
		this.fCondition = condition;
		this.fIncrement = increment;
		this.fAction = action;
		this.setStart(forToken.getColumn());
		if (action != null) {
			this.setEnd(action.sourceEnd());
		}
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {

		if (pVisitor.visit(this)) {
			if (fInitialization != null) {
				fInitialization.traverse(pVisitor);
			}
			if (fCondition != null) {
				fCondition.traverse(pVisitor);
			}
			if (fIncrement != null) {
				fIncrement.traverse(pVisitor);
			}
			if (fAction != null) {
				fAction.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public int getKind() {
		return S_FOR;
	}

	public Statement getAction() {
		return fAction;
	}

	public Expression getCondition() {
		return fCondition;
	}

	public Expression getIncrement() {
		return fIncrement;
	}

	public Expression getInitialization() {
		return fInitialization;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("for:");
		if (this.fCondition != null) {
			output.formatPrintLn("condition:");
			this.fCondition.printNode(output);
		}
		if (this.fIncrement != null) {
			output.formatPrintLn("increment:");
			this.fIncrement.printNode(output);
		}
		if (this.fInitialization != null) {
			output.formatPrintLn("initialization:");
			this.fInitialization.printNode(output);
		}
		if (this.fAction != null) {
			output.indent();
			this.fAction.printNode(output);
			output.dedent();
		}

	}
}
