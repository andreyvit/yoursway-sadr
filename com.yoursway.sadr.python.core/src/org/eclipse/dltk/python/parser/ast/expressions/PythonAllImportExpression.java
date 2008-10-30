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
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.PythonConstants;
import org.eclipse.dltk.utils.CorePrinter;

public class PythonAllImportExpression extends Expression
{
	
	public int getKind( ) {

		return PythonConstants.E_ALLIMPORTS;
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {
			pVisitor.endvisit( this );
		}
	}

	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "*" );
	}

}
