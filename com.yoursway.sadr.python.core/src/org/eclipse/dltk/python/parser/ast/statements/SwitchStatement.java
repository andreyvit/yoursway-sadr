/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Switch statement.
 */
/*
 * TODO: Concept need be fixed. Where are statements?
 * 
 * 
 * Where echo is holding in this model?
 */
public class SwitchStatement extends Statement {
	/**
	 * expression.
	 */
	private Expression fExpression;

	/**
	 * List of statements.
	 */
	private ArrayList fStatements = new ArrayList();

	/**
	 * Construct from ANTLR token for position binsings and expression.
	 * 
	 * @param switchToken
	 * @param exp
	 */
	public SwitchStatement(DLTKToken switchToken, Expression exp) {

		super(switchToken.getColumn(), -1);
		this.fExpression = exp;
		if (this.fExpression != null) {
			this.setEnd(this.fExpression.sourceEnd());
		}
	}

	/**
	 * Traverse.
	 */
	public void traverse(ASTVisitor pVisitor) throws Exception {

		if (pVisitor.visit(this)) {

			if (fExpression != null) {
				fExpression.traverse(pVisitor);
			}
			if (this.fStatements != null) {
				Iterator i = this.fStatements.iterator();
				while (i.hasNext()) {
					Statement statement = (Statement) i.next();
					statement.traverse(pVisitor);
				}
			}
			pVisitor.endvisit(this);
		}

	}

	/**
	 * Return switch Kind.
	 */

	public int getKind() {
		return S_SWITCH;
	}

	/**
	 * Accept case statements group.
	 */
	public void acceptGroup(List l) {

		fStatements.addAll(l);
	}

	/**
	 * Return expression.
	 * 
	 * @return
	 */
	public Expression getExpression() {
		return fExpression;
	}

	/**
	 * Return list of case statements.
	 * 
	 * @return
	 */
	public List getStatements() {
		return fStatements;
	}

	/**
	 * Testing purpose only. Print switch statement.
	 */
	public void printNode(CorePrinter output) {
		output.formatPrintLn("switch:");
		if (this.fExpression != null) {
			this.fExpression.printNode(output);
		}
		if (this.fStatements != null) {
			output.indent();
			Iterator i = this.fStatements.iterator();
			while (i.hasNext()) {
				Statement statement = (Statement) i.next();
				statement.printNode(output);
				output.formatPrint("");
			}
			output.dedent();
		}
	}
}
