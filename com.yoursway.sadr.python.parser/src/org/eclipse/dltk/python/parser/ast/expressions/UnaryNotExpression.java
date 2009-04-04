/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import org.eclipse.dltk.ast.expressions.Expression;

public class UnaryNotExpression extends UnaryExpression {

	public UnaryNotExpression(int start, int end, Expression expression) {
		super(start, end, E_BNOT, expression);
	}

}
