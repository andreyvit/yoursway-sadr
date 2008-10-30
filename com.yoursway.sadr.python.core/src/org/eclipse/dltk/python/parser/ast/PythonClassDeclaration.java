/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

public class PythonClassDeclaration extends TypeDeclaration {
	public PythonClassDeclaration(DLTKToken nameDLTKToken, int start, int end) {

		super(nameDLTKToken, start, end);
	}

	public PythonClassDeclaration(DLTKToken dltk, DLTKToken dltk2) {
		super(dltk, dltk2.getColumn(), -1);
	}

	public void setParents(DLTKToken pStart, ExpressionList supers,
			DLTKToken pEnd) {
		ASTListNode s = new ASTListNode();
		s.getChilds().addAll(supers.getExpressions());
		s.setStart(supers.sourceStart());
		s.setEnd(supers.sourceEnd());
		this.setSuperClasses(s);
		this.setParentStart(pStart.getColumn());
		this.setParentEnd(pEnd.getColumn());
	}

	/**
	 * Return super class names.
	 * 
	 * @return
	 */
	public List getSuperClassNames() {
		List names = new ArrayList();
		if (this.fSuperClasses != null) {
			List superClasseExpressions = this.fSuperClasses.getChilds();
			Iterator i = superClasseExpressions.iterator();
			while (i.hasNext()) {
				Expression expr = (Expression) i.next();
				if (expr instanceof SimpleReference) {
					names.add(((SimpleReference) expr).getName());
				} else if (expr instanceof PythonVariableAccessExpression) {
					String fqn = ((PythonVariableAccessExpression) expr)
							.fqnRepresentation();
					if (fqn != null)
						names.add(fqn);
				}

			}
		}
		return names;
	}

	public void setBody(DLTKToken dltk, Block sa, int sourceEnd) {
		this.setBody(dltk.getColumn(), sa, sourceEnd);
		this.setEnd(sourceEnd);
	}
}
