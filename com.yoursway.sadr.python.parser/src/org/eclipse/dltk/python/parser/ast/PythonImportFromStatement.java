/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.PositionInformation;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.PythonAllImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportAsExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTestListExpression;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonImportFromStatement extends Statement {
	private Expression fImportExpressions;

	private Expression fModuleExpression;

	public PythonImportFromStatement(DLTKToken t, Expression moduleExpression,
			Expression importExpressions) {

		super(t);
		this.fModuleExpression = moduleExpression;
		this.fImportExpressions = importExpressions;
		if( importExpressions != null ) {
//			this.setEnd(importExpressions.sourceEnd());
		}
	}

	public int getKind() {

		return PythonConstants.S_IMPORTFROM;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {

		if (pVisitor.visit(this)) {

			if (fModuleExpression != null) {
				fModuleExpression.traverse(pVisitor);
			}
			if (fImportExpressions != null) {
				fImportExpressions.traverse(pVisitor);
			}

			pVisitor.endvisit(this);
		}
	}

	/**
	 * return import from module name.
	 * 
	 * @return
	 */
	public String getImportModuleName() {
		if (this.fModuleExpression != null
				&& this.fModuleExpression instanceof SimpleReference) {
			return ((SimpleReference) this.fModuleExpression).getName();
		}
		return "";
	}

	public Map/* < String, String > */getImportedAsNames() {
		Map/* < String, String > */importedAsNames = new HashMap/*
																 * < String,
																 * String>
																 */();
		if (this.fImportExpressions != null
				&& this.fImportExpressions instanceof PythonTestListExpression) {
			PythonTestListExpression testList = (PythonTestListExpression) this.fImportExpressions;
			List/* < Expression > */expressions = testList.getExpressions();
			if (expressions != null) {
				Iterator i = expressions.iterator();
				while( i.hasNext()) {
					Expression exp = (Expression)i.next();
					if (exp instanceof PythonImportExpression) {
						String name = ((PythonImportExpression) exp).getName();
						if (name != null) {
							importedAsNames.put(name, name);
						}
					}
					if (exp instanceof PythonImportAsExpression) {
						PythonImportAsExpression importAsExpression = (PythonImportAsExpression) exp;
						String name = importAsExpression.getName();
						String asName = importAsExpression.getAsName();
						if (name != null && asName != null) {
							importedAsNames.put(name, asName);
						}
					}
				}
			}
		}
		return importedAsNames;
	}

	public void printNode(CorePrinter output) {

		output.formatPrintLn("from ");
		if (this.fModuleExpression != null) {
			this.fModuleExpression.printNode(output);
		}
		output.formatPrintLn(" import ");
		if (this.fImportExpressions != null) {
			this.fImportExpressions.printNode(output);
		}
	}

	public boolean isAllImport() {
		if (this.fImportExpressions != null
				&& this.fImportExpressions instanceof PythonAllImportExpression) {
			return true;
		}
		return false;
	}

	public PositionInformation getPosition() {

		int start = sourceStart();
		int end = sourceEnd();
		return new PositionInformation(start, end, start, end);
	}
}
