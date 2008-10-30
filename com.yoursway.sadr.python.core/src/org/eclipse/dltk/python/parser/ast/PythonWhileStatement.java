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
import org.eclipse.dltk.python.parser.ast.statements.WhileStatement;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonWhileStatement extends WhileStatement
{

	private Statement fElseStatement;

	public PythonWhileStatement( DLTKToken t ) {

		super( t );
	}

	public PythonWhileStatement( DLTKToken ft, Expression cond, Statement action ) {

		super( ft, cond, action );
	}

	public void setElseStatement( Statement st ) {

		this.fElseStatement = st;
		if (null != st)
			this.setEnd(st.sourceEnd());
	}

	public Statement getElseStatement( ) {

		return this.fElseStatement;
	}
	
	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			Statement condition = this.getCondition();
			Statement action = this.getAction();			
			
			if( condition != null ) {
				condition.traverse( pVisitor );
			}
			if( action !=null ) {
				action.traverse( pVisitor );
			}
			if( this.fElseStatement != null ) {
				this.fElseStatement.traverse(pVisitor );
			}
			pVisitor.endvisit( this );
		}
	}
	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "while: " );
		
		Statement condition = this.getCondition();
		Statement action = this.getAction();
		
		if( condition != null  ) {
			condition.printNode(output);			
		}
		if( action != null ) {
			if( !( action instanceof Block ) ) {
				output.indent();				
			}
			action.printNode(output);
			if( !( action instanceof Block ) ) {
				output.dedent();				
			}
		}
		if( this.fElseStatement != null ) {
			output.formatPrintLn("Else");
			if( !( this.fElseStatement instanceof Block ) ) {
				output.indent();				
			}			
			this.fElseStatement.printNode(output);
			if( !( this.fElseStatement instanceof Block ) ) {
				output.dedent();				
			}
		}
		output.formatPrint( "" );
	}
}
