/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
/*
 * (c) 2002, 2005 xored software and others all rights reserved. http://www.xored.com
 */
package org.eclipse.dltk.python.parser.ast.expressions;

import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Instanceof expression.
 */
public class InstanceOfExpression extends BinaryExpression {

	/**
	 * Constructs from expression of variable and expression of type.
	 * 
	 * @param variable
	 * @param type
	 */
	public InstanceOfExpression(Expression variable, Expression type) {
		super(variable, E_INSTANSEOF, type);
	}
}
