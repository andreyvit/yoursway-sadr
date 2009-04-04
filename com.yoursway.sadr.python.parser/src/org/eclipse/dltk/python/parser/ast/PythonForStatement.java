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
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.EmptyExpression;
import org.eclipse.dltk.python.parser.ast.statements.ClassicalForStatement;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonForStatement extends ClassicalForStatement
{
	private Statement fElseStatement = null;

	private Expression fMainArguments; // Main arguments

	public PythonForStatement( DLTKToken ft, Expression mains, Expression condition, Statement action ) {

		super( ft, new EmptyExpression( ), condition, new EmptyExpression( ), action );
		this.fMainArguments = mains;
		this.setStart(ft.getColumn());
		this.setEnd(action.sourceEnd());
	}

	public void acceptElse( Statement elseExpression ) {

		this.fElseStatement = elseExpression;
		this.setEnd(elseExpression.sourceEnd());
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			Expression condition = this.getCondition( );
			Statement action = this.getAction( );

			if( this.fMainArguments != null ) {
				this.fMainArguments.traverse( pVisitor );
			}
			if( condition != null ) {
				condition.traverse( pVisitor );
			}
			if( action != null ) {
				action.traverse( pVisitor );
			}

			if( this.fElseStatement != null ) {
				this.fElseStatement.traverse( pVisitor );
			}

			pVisitor.endvisit( this );
		}
	}

	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "for: " );

		Expression condition = this.getCondition( );
		Statement action = this.getAction( );

		if( this.fMainArguments != null ) {
			this.fMainArguments.printNode( output );
		}
		if( condition != null ) {
			output.formatPrintLn( " in " );
			condition.printNode( output );
		}
		if( action != null ) {
			if( ! ( action instanceof Block ) ) {
				output.indent( );
			}
			action.printNode( output );
			if( ! ( action instanceof Block ) ) {
				output.dedent( );
			}
		}

		if( this.fElseStatement != null ) {
			output.formatPrintLn( "else:" );
			this.fElseStatement.printNode( output );
		}
	}

}
