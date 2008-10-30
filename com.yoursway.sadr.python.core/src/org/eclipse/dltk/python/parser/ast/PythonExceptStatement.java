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
import org.eclipse.dltk.python.parser.ast.statements.TryCatchStatement;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonExceptStatement extends TryCatchStatement
{

	private Expression fMessage;
	
	public PythonExceptStatement( DLTKToken t, Expression expr, Expression message, Block body ) {

		super( t, expr, body );
		this.fMessage = message;
		
		if( fMessage != null && fMessage.sourceEnd() > this.sourceEnd() ) {
			this.setEnd( fMessage.sourceEnd());
		}
		if( expr != null && expr.sourceEnd() > this.sourceEnd() ) {
			this.setEnd( expr.sourceEnd());
		}
		if( body != null && body.sourceEnd() > this.sourceEnd() ) {
			this.setEnd( body.sourceEnd() );
		}
	}
	
	public int getKind( ) {
		return PythonConstants.S_EXCEPT;
	}
	
	public Expression getMessage( ) {
		return this.fMessage;
	}
	
	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit(this) ) {
			Expression expression = this.getExpression();
			Block body = this.getBody();
			if( expression != null ) {
				expression.traverse(pVisitor);				
			}
			
			if( this.fMessage != null ) {
				this.fMessage.traverse(pVisitor);
			}
						
			if( body != null ) {
				body.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}

	}

	
	public void printNode( CorePrinter output ) {
		output.formatPrintLn( "except:" );
		Expression expression = this.getExpression();
		Block body = this.getBody();
		
		if( expression != null ) {
			expression.printNode(output);			
		}
		if( this.fMessage != null ) {
			output.formatPrintLn(",");
			this.fMessage.printNode(output);
		}
		if( body != null ) {
			body.printNode(output);
		}
	}

}
