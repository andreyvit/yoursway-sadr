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

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Base class for all expressions.
 * 
 */
public abstract class Expression extends Statement implements
		ExpressionConstants {

	protected Expression(int start, int end) {
		super(start, end);
	}

	protected Expression() {
	}

	public Expression(DLTKToken token) {
		super(token);
	}

	/**
	 * Return string representation of this expression operator. If this is
	 * aruthmetic, binary of assigment expression return expressin symbol. For
	 * some custom expressions return *.
	 * 
	 * @return string expression operator representation
	 */
	public String getOperator() {
		switch (this.getKind()) {
		case E_BAND:
			return " and ";
		case E_BOR:
			return " or ";
		case E_BXOR:
			return " xor ";
		case E_DIV:
			return "/";
		case E_EQUAL:
			return "==";
		case E_LT:
			return "<";
		case E_LE:
			return "<=";
		case E_GT:
			return ">";
		case E_GE:
			return ">=";
		case E_MOD:
			return "%";
		case E_CONCAT:
			return ".";
		case E_XOR:
			return " xor ";
		case E_LAND:
			return "&&";
		case E_LOR:
			return "||";
		case E_LSHIFT:
			return "<<";
		case E_MINUS:
			return "-";
		case E_PLUS:
			return "+";
		case E_MULT:
			return "*";
		case E_RSHIFT:
			return ">>";
		case E_DOT_ASSIGN:
			return ".=";
		case E_IDENTICAL:
			return "===";
		case E_NOTIDENTICAL:
			return "!===";
		case E_LNOT:
			return "!";
		case E_BNOT:
			return "~";
		case E_BNOT_ASSIGN:
			return "~=";
		case E_NOT_EQUAL:
			return "!=";
		case E_NOT_EQUAL2:
			return "<>";
		case E_DIV_ASSIGN:
			return "/=";
		case E_PLUS_ASSIGN:
			return "+=";
		case E_INC:
			return "++";
		case E_MINUS_ASSIGN:
			return "-=";
		case E_DEC:
			return "--";
		case E_MOD_ASSIGN:
			return "%=";
		case E_MULT_ASSIGN:
			return "*=";
		case E_SR_ASSIGN:
			return ">>=";
		case E_SL_ASSIGN:
			return "<<=";
		case E_BXOR_ASSIGN:
			return "^=";
		case E_BAND_ASSIGN:
			return "&=";
		case E_DOUBLE_ARROW:
			return "=>";
		case E_SINGLE_ARROW:
			return "->";
		case E_AT:
			return "@";
		case E_DOLLAR:
			return "$";
		case E_COMMA:
			return ",";
		case E_BOR_ASSIGN:
			return "|=";
		case E_CONCAT_ASSIGN:
			return ".=";
		case E_TILDE:
			return "~";
		case E_DOUBLESTAR_ASSIGN:
			return "**=";
		case E_DOUBLEDIV:
			return "//";
		case E_DOUBLEDIV_ASSIGN:
			return "//=";
        case E_POWER:
            return "**";
        case E_PRINT:
            return "print ";
		}

		return "(???)";
	}

	/**
	 * Testing purposes only. Print expression.
	 */
	public void printNode(CorePrinter output) {
		output.formatPrintLn("Expression" + getSourceRange() + ":" + getKind());
	}

	public boolean equals(Object obj) {
		if (obj instanceof Statement) {
			Statement s = (Statement) obj;
			return sourceStart() == s.sourceStart()
					&& sourceEnd() == s.sourceEnd() && getKind() == s.getKind();
		}

		return false;
	}
}
