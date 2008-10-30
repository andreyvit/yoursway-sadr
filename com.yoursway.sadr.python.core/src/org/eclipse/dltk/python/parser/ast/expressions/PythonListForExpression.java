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


/**
 * 
 * Expression what construct list by using PythonListForExpressions.
 * 
 * Example: [ (a,b) for a in [1,2,3] for b in ["a", "b", "c" ] ] (a,b) this is maker. May be almost any expression. for a in [1,2,3] this is
 * PythonForListExpression.
 * 
 */
public class PythonListForExpression extends ExpressionList
{

	private Expression fMaker;

	public PythonListForExpression( Expression maker ) {
		super();
		this.fMaker = maker;
		this.setStart(maker.sourceStart());
		this.setEnd(maker.sourceEnd());
	}

	public void addExpression(Expression ex) {
		super.addExpression(ex);
		setEnd(ex.sourceEnd());
	}
	public int getKind( ) {

		return PythonConstants.E_LISTFOR;
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			if( this.fMaker != null ) {
				this.fMaker.traverse( pVisitor );
			}
			List/*< Expression >*/ expressions = this.getExpressions( );
			if( expressions != null ) {
				Iterator i = expressions.iterator();
				while( i.hasNext() ) {
					Expression expr = (Expression)i.next();
					expr.traverse( pVisitor );
				}
			}
			pVisitor.endvisit( this );
		}

	}

	
	public void printNode( CorePrinter output ) {

		if( this.fMaker != null ) {
			this.fMaker.printNode( output );
		}
		output.formatPrintLn( " " );
		List/*< Expression >*/ exprs = this.getExpressions( );
		Iterator i = exprs.iterator();
		while( i.hasNext() ) {
			Expression e = (Expression)i.next();
			e.printNode( output );
			output.formatPrintLn( " " );
		}
	}

}
