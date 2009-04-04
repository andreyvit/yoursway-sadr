/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.PythonConstants;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonDictExpression extends Expression
{
	private static class DictNode
	{
		private Expression key;

		private Expression value;

		public DictNode( Expression key, Expression value ) {

			this.key = key;
			this.value = value;
		}

		public Expression getKey( ) {

			return this.key;
		}

		public Expression getValue( ) {

			return this.value;
		}
	}

	private List/*< DictNode >*/ fDictionary = new ArrayList/*< DictNode >*/( );

	public PythonDictExpression( ) {

	}

	public PythonDictExpression( DLTKToken t ) {

		super( t );
	}

	
	public int getKind( ) {

		return PythonConstants.E_DICT;
	}

	
	public void traverse( ASTVisitor pVisitor ) throws Exception {

		if( pVisitor.visit( this ) ) {

			Iterator i = this.fDictionary.iterator();
			while( i.hasNext()) {
				DictNode node = (DictNode)i.next();
				Expression key = node.getKey( );
				if( key != null ) {
					key.traverse( pVisitor );
				}
				Expression value = node.getValue( );
				if( value != null ) {
					value.traverse( pVisitor );
				}
			}
			pVisitor.endvisit( this );
		}
	}

	public void putExpression( Expression key, Expression value ) {

		this.fDictionary.add( new DictNode( key, value ) );
	}

	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "{" );
		if( this.fDictionary != null ) {

			boolean first = true;
			Iterator i = this.fDictionary.iterator();
			while( i.hasNext()) {
				DictNode node = (DictNode)i.next();
				if( first ) {
					first = false;
				}
				else {
					output.formatPrintLn( ", " );
				}
				Expression key = node.getKey( );
				if( key != null ) {
					key.printNode( output );
					output.formatPrintLn( ":" );
				}
				Expression value = node.getValue( );
				if( value != null ) {
					value.printNode( output );
				}
			}
		}
		output.formatPrintLn( "}" );
	}

}
