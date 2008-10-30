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
 * C like switch case conception. this is switch ( x ) { case A: case B: case C: }
 * only case and expression of case.
 */
public class CaseStatement extends SimpleStatement {
	public CaseStatement(DLTKToken caseToken, Expression caseExpression) {
		super(caseToken, caseExpression);
	}

	public CaseStatement(DLTKToken defaultToken) {
		super(defaultToken.getColumn(), defaultToken.getColumn()
				+ defaultToken.getText().length(), null);
	}

	public int getKind() {
		return S_CASE;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("case: ");
		output.indent();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.printNode(output);
		}
		output.dedent();
	}
}
