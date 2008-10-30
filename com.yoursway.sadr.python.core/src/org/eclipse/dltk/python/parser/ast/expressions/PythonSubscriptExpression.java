/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.PythonConstants;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonSubscriptExpression extends Expression
{

	private Expression fTest;

	private Expression fCondition;

	private Expression fSlice;

	public PythonSubscriptExpression( ) {

		super( );
	}

	public PythonSubscriptExpression( DLTKToken t ) {

		super( t );
	}

	public PythonSubscriptExpression( DLTKToken t, Expression test, Expression cond, Expression slice ) {

		super( t );
		this.fTest = test;
		this.fCondition = cond;
		this.fSlice = slice;
	}

	
	public int getKind( ) {

		return PythonConstants.E_SUBSCRIPT;
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {

			if( this.fTest != null ) {
				this.fTest.traverse( pVisitor );
			}
			if( this.fCondition != null ) {
				this.fCondition.traverse( pVisitor );
			}
			if( this.fSlice != null ) {
				this.fSlice.traverse( pVisitor );
			}

			pVisitor.endvisit( this );
		}

	}

	public Expression getCondition( ) {

		return fCondition;
	}

	public void setCondition( Expression condition ) {

		fCondition = condition;
	}

	public Expression getSlice( ) {

		return fSlice;
	}

	public void setSlice( Expression slice ) {

		fSlice = slice;
	}

	public Expression getTest( ) {

		return fTest;
	}

	public void setTest( Expression test ) {

		fTest = test;
	}

	public void printNode( CorePrinter output ) {

		if( this.fTest != null ) {
			this.fTest.printNode( output );
		}
		if( this.fCondition != null ) {
			this.fCondition.printNode( output );
		}
		if( this.fSlice != null ) {
			this.fSlice.printNode( output );
		}
	}

}
