/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast.expressions;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * 
 * Numeric literal. Used to hold ints and long int numbers.
 * 
 * Immutable.
 * 
 */
public class NumericLiteral extends Literal {

	private long intValue;
	
	/**
	 * Construct from ANTLR token. With appropriate position information.
	 * 
	 * @param number
	 */
	public NumericLiteral(DLTKToken number) {
		super(number);
		if (fLiteralValue.equals("0")) 
			this.intValue = 0;
		else if(fLiteralValue.startsWith("0x") || fLiteralValue.startsWith("0X"))
			this.intValue = Long.parseLong(fLiteralValue.substring(2), 16);
		else if(fLiteralValue.startsWith("0"))
			this.intValue = Long.parseLong(fLiteralValue.substring(1), 8);
		else
			this.intValue = Long.parseLong(fLiteralValue);
	}

	public NumericLiteral(int start, int end, long value) {
		super(start, end);
		this.intValue = value;
	}
	
	public long getIntValue () {
		return intValue;
	}

	
	/**
	 * Return kind.
	 */

	public int getKind() {
		return NUMBER_LITERAL;
	}

	/**
	 * Testing purposes only. Used to print number.
	 */

	public void printNode(CorePrinter output) {
		output.formatPrintLn(this.getValue());
	}
}
