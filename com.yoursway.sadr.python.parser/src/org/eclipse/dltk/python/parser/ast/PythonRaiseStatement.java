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


public class PythonRaiseStatement extends Statement
{

	public PythonRaiseStatement( DLTKToken t ) {

		super( t );
	}

	protected Expression fExpression1;

	protected Expression fExpression2;

	protected Expression fExpression3;

	protected PythonRaiseStatement( int start, int end, Expression expression ) {

		super( start, end );
		this.fExpression1 = expression;
		if( expression !=null && expression.sourceEnd() > this.sourceEnd()) {
			this.setEnd(expression.sourceEnd());
		}
	}

	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {

			if( this.fExpression1 != null ) {
				this.fExpression1.traverse( pVisitor );
			}
			if( this.fExpression2 != null ) {
				this.fExpression2.traverse( pVisitor );
			}
			if( this.fExpression3 != null ) {
				this.fExpression3.traverse( pVisitor );
			}
			pVisitor.endvisit( this );
		}

	}

	public Expression getExpression1( ) {

		return this.fExpression1;
	}

	public Expression getExpression2( ) {

		return this.fExpression2;
	}

	public Expression getExpression3( ) {

		return this.fExpression3;
	}

	public void acceptExpression1( Expression expression ) {

		this.fExpression1 = expression;
		if( expression !=null && expression.sourceEnd() > this.sourceEnd()) {
			this.setEnd(expression.sourceEnd());
		}
	}

	public void acceptExpression2( Expression expression ) {

		this.fExpression2 = expression;
		if( expression !=null && expression.sourceEnd() > this.sourceEnd()) {
			this.setEnd(expression.sourceEnd());
		}
	}

	public void acceptExpression3( Expression expression ) {

		this.fExpression3 = expression;
		if( expression !=null && expression.sourceEnd() > this.sourceEnd()) {
			this.setEnd(expression.sourceEnd());
		}
	}

	
	public int getKind( ) {

		return PythonConstants.S_RAISE;
	}

	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "Raise " );
		if( this.fExpression1 != null ) {
			this.fExpression1.printNode( output );
		}
		if( this.fExpression2 != null ) {
			this.fExpression2.printNode( output );
		}
		if( this.fExpression3 != null ) {
			this.fExpression1.printNode( output );
		}

		output.formatPrint( "" );
	}
}
