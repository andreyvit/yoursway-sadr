/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.PythonCallArgumentsList;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Holds call arguments. Used to ExtendedVariableReference holding.
 * 
 * @author haiodo
 * 
 */
public class CallHolder extends Expression {
	/**
	 * Can be EmptyExpression if no arguments are specified. Overwise it
	 * possible list of expressions, if only one argument too.
	 */
	private PythonCallArgumentsList fArguments = null;

	public CallHolder(int start, int end, PythonCallArgumentsList arguments) {

		super(start, end);
		this.fArguments = arguments;
	}

	public CallHolder(DLTKToken dltk, DLTKToken dltk2, PythonCallArgumentsList k) {
		super(dltk.getColumn(), dltk2.getColumn() + 1);
		this.fArguments = k;
	}

	public int getKind() {
		return Expression.E_CALL;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (this.fArguments != null) {
				this.fArguments.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public List getArguments() {
		return fArguments.getChilds();
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("( ");
		if (this.fArguments != null) {
			this.fArguments.printNode(output);
		}
		output.formatPrintLn(" )");
	}
}
