/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.statements.SimpleStatement;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonYieldStatement extends SimpleStatement {

	public PythonYieldStatement(DLTKToken t, Expression expression) {
		super(t, expression);		
	}

	
	public int getKind() {
		return PythonConstants.S_YIELD;
	}

	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "yield " );
		if( this.fExpression != null ) {
			this.fExpression.printNode(output);
		}
	}
}
