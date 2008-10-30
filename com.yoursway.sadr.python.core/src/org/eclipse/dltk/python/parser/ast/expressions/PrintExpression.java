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
package org.eclipse.dltk.python.parser.ast.expressions;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Print expression.
 */
public class PrintExpression extends UnaryExpression {

	/**
	 * Construct from ANTLR token and right expression.
	 * 
	 * @param p
	 * @param right
	 */
	public PrintExpression(DLTKToken p, Expression right) {
		super(p, E_PRINT, right);

		if (right != null) {
			this.setEnd(right.sourceEnd());
		}
	}

	/**
	 * Testing purposes only. Print "print" expression.
	 */
	public void printNode(CorePrinter output) {
		output.formatPrintLn("print ");
		Statement expr = this.getExpression();
		if (expr != null) {
			expr.printNode(output);
		}
	}

}
