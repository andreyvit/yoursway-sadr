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
 * ( x ) expression.
 */
public class ParenExpression extends UnaryExpression {

	/**
	 * Construct from position bindings.
	 * 
	 * @param start -
	 *            start position in associated file.
	 * @param end -
	 *            end position in associated file.
	 * @param parenExpression
	 */
	public ParenExpression(int start, int end, Expression parenExpression) {
		super(start, end, E_PAREN, parenExpression);
	}
}
