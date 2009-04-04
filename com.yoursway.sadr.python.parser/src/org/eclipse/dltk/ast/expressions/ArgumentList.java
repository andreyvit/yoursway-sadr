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
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Holds list of expressions.
 */
public class ArgumentList extends Expression {
	private List fExpressions = new ArrayList();

	/**
	 * Construct from position bindings.
	 */
	protected ArgumentList(int start, int end) {
		super(start, end);
	}

	/**
	 * Construct without position bindings. And without elements. By default
	 * expression list is initialized. So you can call getExpressions after
	 * creation.
	 */
	public ArgumentList() {

	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (fExpressions != null) {
				Iterator it = fExpressions.iterator();
				while (it.hasNext()) {
				    Argument e = (Argument) it.next();
					e.traverse(pVisitor);
				}
			}
			pVisitor.endvisit(this);
		}
	}

	public int getKind() {
		return E_EXPRESSION_LIST;
	}

	/**
	 * Add expression to current list. If expressions list is null, then is is
	 * created.
	 */
	public void add(Argument arg) {
		if (arg != null) {
			fExpressions.add(arg);
		}
	}

	public List getArguments() {
		return fExpressions;
	}

	/**
	 * Testing purposes only. Prints all expressions.
	 */
	public void printNode(CorePrinter output) {
		if (this.fExpressions != null) {
			Iterator i = fExpressions.iterator();
			while (i.hasNext()) {
				Expression expr = (Expression) i.next();

				expr.printNode(output);

				if (i.hasNext()) {
					output.formatPrintLn(", ");
				}
			}
		}
	}
}
