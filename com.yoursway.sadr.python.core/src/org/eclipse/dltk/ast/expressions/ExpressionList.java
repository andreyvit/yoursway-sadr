/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
/*
 * (c) 2002, 2005 xored software and others all rights reserved. http://www.xored.com
 */
package org.eclipse.dltk.ast.expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Holds list of expressions.
 * @deprecated
 */
public class ExpressionList extends Expression {
	private List fExpressions = new ArrayList();

	/**
	 * Construct from position bindings.
	 */
	protected ExpressionList(int start, int end) {
		super(start, end);
	}

	/**
	 * Construct without position bindings. And without elements. By default
	 * expression list is initialized. So you can call getExpressions after
	 * creation.
	 */
	public ExpressionList() {

	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (fExpressions != null) {
				Iterator it = fExpressions.iterator();
				while (it.hasNext()) {
					Expression e = (Expression) it.next();
					e.traverse(pVisitor);
				}
			}
			pVisitor.endvisit(this);
		}
	}

	public int getKind() {
		return E_EXPRESSION_LIST;
	}

	public void setExpresssions(List exs) {
		//TODO: rewrite
		Iterator i = exs.iterator();
		while (i.hasNext()) {
			//TODO: XXX: We need to change expression list into statement list.
			Object e = i.next();
			if( e instanceof Expression ) {
				this.addExpression((Expression)e);
			}
			else {
				if( DLTKCore.DEBUG ) {
					System.err.println("TODO: Add expression to statemenet conversion or change expressin list to statement list...");
				}
			}
		}
	}

	/**
	 * Add expression to current list. If expressions list is null, then is is
	 * created.
	 * @param properties 
	 */
	public void addExpression(Expression ex) {
		if (fExpressions == null) {
			fExpressions = new ArrayList();
		}
		if (ex != null) {
			fExpressions.add(ex);
		}
	}

	public List getExpressions() {
		return fExpressions;
	}

	/**
	 * Testing purposes only. Prints all expressions.
	 */
	public void printNode(CorePrinter output) {
		if (this.fExpressions != null) {
			int index = 0;
			Iterator i = fExpressions.iterator();
			while (i.hasNext()) {
				Expression expr = (Expression) i.next();

				expr.printNode(output);

				if (index != this.fExpressions.size() - 1) {
					output.formatPrintLn(", ");
				}
				index += 1;
			}
		}
	}
}
