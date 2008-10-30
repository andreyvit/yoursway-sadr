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

import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * NonStrict Assignment representation. Used to hold assignments such as +=, -=
 * etc.
 * 
 */
public class NotStrictAssignment extends Assignment {

	/**
	 * Construct from Expression and expression type.
	 * 
	 * @param left -
	 *            left expression.
	 * @param type -
	 *            expression type.
	 * @param right -
	 *            right expression.
	 */
	public NotStrictAssignment(Statement left, int type, Statement right) {
		super(left, type, right);
	}

	/**
	 * Testing purposes only. Used to print expression.
	 */

	public void printNode(CorePrinter output) {
		if (getLeft() != null) {
			getLeft().printNode(output);
		}
		output.formatPrintLn(this.getOperator());
		if (getRight() != null) {
			getRight().printNode(output);
		}
	}
}
