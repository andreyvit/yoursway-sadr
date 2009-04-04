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
package org.eclipse.dltk.ast.expressions;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * 
 * Literal expression. Base class for various literals such as Number or String.
 */
public abstract class Literal extends Expression {

	
	public static Literal createNumericLiteral(DLTKToken token){
		String text = token.getText();
		if(text.endsWith("l") || text.endsWith("L")){
			return new BigNumericLiteral(token);
		}
		try{
			return new NumericLiteral(token);
		}catch(NumberFormatException exc) {
			return new BigNumericLiteral(token);
		}
	}
	/**
	 * Value.
	 */
	protected String fLiteralValue;

	/**
	 * Construct with position bindings.
	 * 
	 * @param start -
	 *            start position in associated file.
	 * @param end -
	 *            end position in associated file.
	 */
	protected Literal(int start, int end) {
		super(start, end);
	}

	/**
	 * Construcs from ANTLR token with position bindings. Token holds value.
	 * 
	 * @param token -
	 *            ANTLR token.
	 */
	protected Literal(DLTKToken token) {
		super(token);
		this.fLiteralValue = token.getText();
	}

	/**
	 * Traverse to this node.
	 */
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			pVisitor.endvisit(this);
		}
	}

	/**
	 * Return value of this literal.
	 */
	public String toString() {
		return fLiteralValue;
	}

	/**
	 * Return value of this literal.
	 */
	public String getValue() {
		return fLiteralValue;
	}

	/**
	 * Testing purposes only. Print literal.
	 */
	public void printNode(CorePrinter output) {
		output.formatPrintLn("Literal" + this.getSourceRange().toString() + ":" + this.getValue());
	}
}
