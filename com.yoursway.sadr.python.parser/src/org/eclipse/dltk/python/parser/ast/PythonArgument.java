/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.expressions.Expression;


public class PythonArgument extends Argument
{
    public static final int NOSTAR = 0;
	public static final int STAR = 1;
	public static final int DOUBLESTAR = 2;
	private int star = NOSTAR;

	public PythonArgument( DLTKToken name ) {
		super(name, name.getColumn(), name.getColumn() + name.getText().length(), null );		
	}

	public PythonArgument( DLTKToken name, int star) {
		super(name, name.getColumn(), name.getColumn() + name.getText().length(), null );		
		this.star = star;
	}
	
	public PythonArgument(DLTKToken name, int start, int end, Expression init) {
		super(name, start, end, init);		
	}

	public PythonArgument() {
		super();		
	}	
	
	public void assign( Expression expr ) {
		this.setInitializationExpression( expr );
	}
	
	public int getStar() {
		return star;
	}
}
