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

public class ContinueStatement extends SimpleStatement {

	public ContinueStatement(DLTKToken continueToken, DLTKToken labelToken,
			int end) {
		super(continueToken.getColumn(), end, null);
		if (labelToken != null) {
			this.fExpression = new SimpleReference(labelToken);
			this.setEnd(fExpression.sourceEnd());
		}
	}

	public ContinueStatement(DLTKToken dltk, Object object, DLTKToken dltk2) {
		super(dltk.getColumn(), dltk2.getColumn(), null );
	}

	public int getKind() {
		return S_CONTINUE;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("continue ");

		Expression expression = this.getExpression();
		if (expression != null) {
			expression.printNode(output);
		}
	}
}
