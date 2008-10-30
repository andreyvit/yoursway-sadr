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

import java.util.ArrayList;
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
 * @deprecated Should never appear in AST, use PythonCallExpression/PythonArrayAccessExpression/PythonVariableAccessExpression instead.
 */
public class ExtendedVariableReference extends Reference {
	/**
	 * List of expressions.
	 */
	private List fExpressions = new ArrayList();

	/**
	 * @param expr -
	 *            logically expr should be instance of VariableReference.
	 */
	public ExtendedVariableReference(Expression expr) {
		if (expr != null) {
			this.addExpression(expr);
			this.setStart(expr.sourceStart());
			this.setEnd(expr.sourceEnd());
		}
	}

	/**
	 * Return Identifier Kind.
	 */
	public int getKind() {
		return E_IDENTIFIER;
	}

	/**
	 * Adding expression to extended variable. Use only in parsers then
	 * construct.
	 * 
	 * @param expr
	 */
	public void addExpression(Expression expr) {
		if (expr != null) {
			this.fExpressions.add(expr);
			if (expr.sourceEnd() >= this.sourceEnd()) {
				setEnd(expr.sourceEnd());
			}
		}
	}

	/**
	 * Set Expressions. Use only in parsers then construct.
	 * 
	 * @param exprs
	 */
	public void setExpresssions(List exprs) {
		this.fExpressions = exprs;
	}

	/**
	 * Return expressions.
	 * 
	 * @return
	 */
	public List getExpressions() {
		return this.fExpressions;
	}

	// Checkers

	/**
	 * tests for next element of index is call
	 */
	public boolean isCall(int index) {
		List expressions = this.getExpressions();
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
	public boolean isIndex(int index) {
		List expressions = this.getExpressions();
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
	public boolean isDot(int index) {
		List expressions = this.getExpressions();
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
	public String getStringRepresentation() {
		StringBuffer b = new StringBuffer();
		Iterator i = this.fExpressions.iterator();
		while (i.hasNext()) {
			Expression e = (Expression) i.next();
			switch (e.getKind()) {
			case E_IDENTIFIER:
				Reference s = (Reference) e;
				b.append(s.getStringRepresentation());

				// TODO: Add to check for next is CallHolder, or next in
				// IndexHolder.
				b.append('.');
				break;
			// (often true)
			case E_INDEX:
				b.append("[]");
				break;
			case E_CALL:
				b.append("()");
				break;
			case E_CURLY:
				b.append("{}");
				break;
			}
		}
		return b.toString();
	}

	/**
	 * Return i'th expression. No index checking.
	 * 
	 * @param i
	 * @return expression on i' position.
	 */
	public Expression getExpression(int i) {
		if (this.fExpressions != null) {
			return (Expression) fExpressions.get(i);
		}
		return null;
	}

	/**
	 * Return expressions count.
	 * 
	 * @return
	 */
	public int getExpressionCount() {
		if (this.fExpressions != null) {
			return fExpressions.size();
		}
		return 0;
	}

	/**
	 * Return true then index == this.fExpressions.size() - 1
	 * 
	 * @param index
	 * @return
	 */
	public boolean isLast(int index) {
		if (this.fExpressions != null) {
			return this.fExpressions.size() - 1 == index;
		}
		return false;
	}

	/**
	 * Testing purposes only. Print extended variable.
	 */
	public void printNode(CorePrinter output) {
		List expressions = this.getExpressions();
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
