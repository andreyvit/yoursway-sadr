/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.statements;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Return statement.
 */
public class ReturnStatement extends SimpleStatement {
	public ReturnStatement(DLTKToken rt, Expression exp, int end) {
		super(rt.getColumn(), end, exp);
	}

	

	public int getKind() {
		return S_RETURN;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("return ");

		Expression expression = this.getExpression();
		if (expression != null) {
			expression.printNode(output);
		}
	}
}
