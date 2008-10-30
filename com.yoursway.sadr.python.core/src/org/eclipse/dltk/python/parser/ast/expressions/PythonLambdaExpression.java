/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.ArgumentList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.PythonConstants;
import org.eclipse.dltk.utils.CorePrinter;

public class PythonLambdaExpression extends Expression
{

	private final ArgumentList fArguments;

	private Expression fBodyExpressions;

	public PythonLambdaExpression( DLTKToken t, ArgumentList args , Expression body ) {

		super( t );
		if( body != null ) {
			this.setEnd( body.sourceEnd( ) );
		}
		this.fArguments = args;
		this.fBodyExpressions = body;
	}

	public List getArguments( ) {

		return this.fArguments.getChilds();
	}

	public Expression getBodyExpression( ) {

		return this.fBodyExpressions;
	}

	
	public int getKind( ) {

		return PythonConstants.E_LAMBDA;
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			if( this.fArguments != null ) {
				this.fArguments.traverse( pVisitor );
			}
			if( this.fBodyExpressions != null ) {
				this.fBodyExpressions.traverse( pVisitor );
			}
			pVisitor.endvisit( this );
		}

	}

	@Override
	public String toString() {
		return "<lambda>";
	}
	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "lambda " );
		if( this.fArguments != null ) {
			this.fArguments.printNode( output );
		}
		output.formatPrintLn( ":" );
		if( this.fBodyExpressions != null ) {
			this.fBodyExpressions.printNode( output );
		}
	}
}
