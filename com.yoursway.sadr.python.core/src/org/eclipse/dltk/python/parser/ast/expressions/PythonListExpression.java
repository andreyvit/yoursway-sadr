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

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonListExpression extends ExpressionList
{
	public PythonListExpression( ) {

	}	
	
	public void printNode( CorePrinter output ) {
		List/*< Expression >*/ expressions = this.getExpressions();
		
		output.formatPrintLn( "[ " );		
		if( expressions != null ) {
			int index = 0;
			Iterator i = expressions.iterator();
			while( i.hasNext() ) {
				Expression expr = (Expression)i.next();
		
				expr.printNode(output);
			
				if( index != expressions.size() - 1 ) {
					output.formatPrintLn(", ");
				}
				index += 1;
			}
		}
		output.formatPrintLn( "]" );		
	}
}
