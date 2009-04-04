/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
/*
 * Created on 14.11.2004
 * 
 * TODO To change the template for this generated file go to Window - Preferences - Script - Code
 * Style - Code Templates
 */
package org.eclipse.dltk.ast.references;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

public abstract class Reference extends Expression {
	protected Reference() {
		super();
	}

	protected Reference(int sourceStart, int sourceEnd) {
		super(sourceStart, sourceEnd);
	}

	public int getKind() {
		return E_IDENTIFIER;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			pVisitor.endvisit(this);
		}
	}

	public abstract String getStringRepresentation();
}
