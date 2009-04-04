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
 * Throw statement. Exception throwing.
 */
public class ThrowStatement extends SimpleStatement {
	public ThrowStatement(DLTKToken throwToken, Expression expression) {
		super(throwToken.getColumn(), -1, expression);
		if (expression != null) {
			this.setEnd(expression.sourceEnd());
		}
	}

	public int getKind() {
		return S_THROW;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("throw: ");
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.printNode(output);
		}
	}
}
