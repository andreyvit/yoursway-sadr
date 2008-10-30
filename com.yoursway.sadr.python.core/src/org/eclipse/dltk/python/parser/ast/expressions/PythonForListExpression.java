/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.python.parser.ast.PythonConstants;
import org.eclipse.dltk.utils.CorePrinter;


/*
 * for a in v expression
 */
public class PythonForListExpression extends Expression
{

	// for a in [1,2,3] if a > 2
	private Expression fVars; // this is a expression.

	private Expression fFrom; // this is [1,2,3] expression

	private ExpressionList fIfList; // this a > 2 expression.

	public PythonForListExpression( Expression vars, Expression from ) {

		super( );
		this.fVars = vars;
		this.fFrom = from;
		this.setStart(vars.sourceStart());
		this.setEnd(from.sourceEnd());
	}

	
	public int getKind( ) {

		return PythonConstants.E_FORIFLIST;
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			if( this.fVars != null ) {
				this.fVars.traverse( pVisitor );
			}
			if( this.fFrom != null ) {
				this.fFrom.traverse( pVisitor );
			}
			if( this.fIfList != null ) {
				this.fIfList.traverse( pVisitor );
			}
			pVisitor.endvisit( this );
		}

	}

	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "for " );
		if( this.fVars != null ) {
			this.fVars.printNode( output );
		}

		output.formatPrintLn( " in " );
		if( this.fFrom != null ) {
			this.fFrom.printNode( output );
		}
		if( this.fIfList != null ) {
			List/*< Expression >*/ ifs = this.fIfList.getExpressions( );
			Iterator i = ifs.iterator();
			while( i.hasNext() ) {
				Expression ife = (Expression)i.next();
				output.formatPrintLn( "if " );
				ife.printNode( output );
			}
		}
	}

	public void setIfListExpression( ExpressionList ifBlock ) {

		this.fIfList = ifBlock;
		this.setEnd(ifBlock.sourceEnd());
	}

}
