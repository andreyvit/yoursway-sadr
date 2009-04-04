/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonAssertStatement extends Statement
{
	private Expression fExpression1 = null;

	private Expression fExpression2 = null;

	public PythonAssertStatement( DLTKToken DLTKToken, Expression expr1, Expression expr2 ) {

		super( DLTKToken );
		this.fExpression1 = expr1;
		this.fExpression2 = expr2;
	}

	PythonAssertStatement( DLTKToken DLTKToken, Expression expr1 ) {

		super( DLTKToken );
		this.fExpression1 = expr1;
	}

	
	public int getKind( ) {

		return PythonConstants.S_ASSERT;
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			if( this.fExpression1 != null ) {
				this.fExpression1.traverse( pVisitor );
			}
			if( this.fExpression2 != null ) {
				this.fExpression2.traverse( pVisitor );
			}
			pVisitor.endvisit( this );
		}
	}
	
	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "assert:" );
		if( this.fExpression1 != null ) {
			this.fExpression1.printNode(output);
		}		
		if( this.fExpression2 != null ) {
			output.formatPrintLn(",");
			this.fExpression2.printNode(output);
		}		
	}

}
