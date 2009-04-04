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
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.utils.CorePrinter;

public class BreakStatement extends SimpleStatement {
	public BreakStatement(DLTKToken breakToken, DLTKToken label, int end) {
		super(breakToken.getColumn(), end, null);
		if (label != null) {
			this.fExpression = new SimpleReference(label);
			this.setEnd(fExpression.sourceEnd());
		}
	}

	public BreakStatement(DLTKToken dltk, Object object, DLTKToken dltk2) {
		super(dltk.getColumn(), dltk2.getColumn(), null);
	}

	public int getKind() {
		return S_BREAK;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("break ");

		Expression expression = this.getExpression();
		if (expression != null) {
			expression.printNode(output);
		}
	}
}
