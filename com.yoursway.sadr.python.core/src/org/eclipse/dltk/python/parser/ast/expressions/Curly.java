/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Curly expression.
 */
public class Curly extends UnaryExpression {
	/**
	 * Construct curly.
	 * 
	 * @param start
	 * @param end
	 * @param e
	 */
	public Curly(int start, int end, Expression e) {
		super(start, end, E_CURLY, e);
	}

	/**
	 * Testing purposes only. Print curly with expression.
	 */
	public void printNode(CorePrinter output) {
		output.formatPrintLn(" { ");
		if (this.expression != null) {
			this.expression.printNode(output);
		}
		output.formatPrintLn(" } ");
	}
}
