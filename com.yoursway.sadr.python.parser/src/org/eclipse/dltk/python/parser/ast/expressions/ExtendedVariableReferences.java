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

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.Reference;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Holds extended variables as list of VariableReference, CallHolder,
 * IndexHolder.. Example: 1) z.a.f() it is be: ( VariableReference,
 * VariableReference, VariableReference, CallHolder ) 2) a.f().q[2] it is be: (
 * VariableReference, VariableReference, CallHolder, VariableReference,
 * IndexHolder )
 * 
 */
public final class ExtendedVariableReferences {
	/**
	 * tests for next element of index is call
	 */
	public static boolean isCall(List expressions, int index) {
		if (expressions == null) {
			return false;
		}

		if (index < expressions.size() - 1) {
			Expression afterIndexExpression = (Expression) expressions
					.get(index + 1);

			if (afterIndexExpression instanceof CallHolder) {
				return true;
			}

		}
		return false;
	}

	/**
	 * tests for next element of index is index
	 */
	public static boolean isIndex(List expressions, int index) {
		if (expressions == null) {
			return false;
		}
		if (index < expressions.size() - 1) {
			Expression afterIndexExpression = (Expression) expressions
					.get(index + 1);
			if (afterIndexExpression instanceof IndexHolder) {
				return true;
			}

		}
		return false;
	}

	/**
	 * tests for next element of index is dot
	 */
	public static boolean isDot(List expressions, int index) {
		if (expressions == null) {
			return false;
		}
		if (index < expressions.size() - 1) {
			Expression afterIndexExpression = (Expression) expressions
					.get(index + 1);
			if (!(afterIndexExpression instanceof CallHolder)
					&& !(afterIndexExpression instanceof IndexHolder)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Used to get name of this variable from sub names.
	 */

	// TODO: FIX MY PERFOMANCE
	public static String getStringRepresentation(List expressions) {
		StringBuffer b = new StringBuffer();
		Iterator i = expressions.iterator();
		while (i.hasNext()) {
			Expression e = (Expression) i.next();
			switch (e.getKind()) {
			case Expression.E_IDENTIFIER:
				Reference s = (Reference) e;
				b.append(s.getStringRepresentation());

				// TODO: Add to check for next is CallHolder, or next in
				// IndexHolder.
				b.append('.');
				break;
			// (often true)
			case Expression.E_INDEX:
				b.append("[]");
				break;
			case Expression.E_CALL:
				b.append("()");
				break;
			case Expression.E_CURLY:
				b.append("{}");
				break;
			}
		}
		return b.toString();
	}

	/**
	 * Return true then index == this.fExpressions.size() - 1
	 * 
	 * @param index
	 * @return
	 */
	public static boolean isLast(List expressions, int index) {
		if (expressions != null) {
			return expressions.size() - 1 == index;
		}
		return false;
	}

	/**
	 * Testing purposes only. Print extended variable.
	 */
	public static void printNode(List expressions, CorePrinter output) {
		boolean bFirst = true;

		if (expressions != null) {
			Iterator i = expressions.iterator();
			while (i.hasNext()) {
				Expression expr = (Expression) i.next();
				if (bFirst) {
					bFirst = false;
				} else {
					if (!(expr instanceof CallHolder)
							&& !(expr instanceof IndexHolder)) {
						output.formatPrintLn(".");
					}
				}
				expr.printNode(output);
			}
		}
	}
}
