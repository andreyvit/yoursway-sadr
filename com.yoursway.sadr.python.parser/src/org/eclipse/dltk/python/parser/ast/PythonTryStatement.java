/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.statements.TryStatement;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonTryStatement extends TryStatement
{

	private Statement fElseStatement;

	public PythonTryStatement( DLTKToken q, Block body, List/*< Statement >*/ catchFin ) {

		super( q, body, catchFin );
		if( body != null && body.sourceEnd() > this.sourceEnd() ) {
			setEnd( body.sourceEnd());
		}
		if( catchFin != null ) {
			Iterator i = catchFin.iterator();
			while( i.hasNext()) {
				ASTNode node = (ASTNode) i.next();
				if( node.sourceEnd() > this.sourceEnd() ) {
					this.setEnd( node.sourceEnd());
				}
			}
		}
	}

	public void setElseStatement( Statement elseStatement ) {

		this.fElseStatement = elseStatement;
		if( this.fElseStatement != null && this.fElseStatement.sourceEnd() > this.sourceEnd() ) {
			setEnd( this.fElseStatement.sourceEnd());
		}
	}

	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			Statement body = this.getBody( );
			List/*< Statement >*/ catchsFinallyStatements = this.getCatchFinallyStatements( );

			if( body != null ) {
				body.traverse( pVisitor );
			}
			if( catchsFinallyStatements != null ) {
				Iterator i = catchsFinallyStatements.iterator();
				while( i.hasNext()) {
					Statement st = (Statement)i.next();
					st.traverse( pVisitor );
				}
			}
			if( this.fElseStatement != null ) {
				this.fElseStatement.traverse( pVisitor );
			}
			pVisitor.endvisit( this );
		}
	}

	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "try:" );
		Statement body = this.getBody( );
		List/*< Statement >*/ catchFinallyStatements = this.getCatchFinallyStatements( );
		if( body != null ) {
			body.printNode( output );
		}
		if( catchFinallyStatements != null ) {
			Iterator i = catchFinallyStatements.iterator();
			while( i.hasNext()) {
				Statement st = (Statement)i.next();
				st.printNode( output );
			}
		}
		if( this.fElseStatement != null ) {
			output.formatPrintLn( "else:" );
			if( ! ( this.fElseStatement instanceof Block ) ) {
				output.indent( );
			}
			this.fElseStatement.printNode( output );
			if( ! ( this.fElseStatement instanceof Block ) ) {
				output.dedent( );
			}
		}
	}
}
