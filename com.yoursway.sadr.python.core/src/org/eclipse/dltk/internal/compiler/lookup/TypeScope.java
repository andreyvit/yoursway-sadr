/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.compiler.lookup;

import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.compiler.env.lookup.Scope;

public class TypeScope extends Scope {

	public TypeDeclaration referenceContext;

	public TypeScope(Scope parent, TypeDeclaration context) {
		super(CLASS_SCOPE, parent);
		this.referenceContext = context;
	}

	/*
	 * Answer the reference type of this scope. It is the nearest enclosing type
	 * of this scope.
	 */
	public TypeDeclaration referenceType() {
		return referenceContext;
	}

	public String toString() {
		if (referenceContext != null) {
			return "--- Class Scope ---\n\n"; //$NON-NLS-1$
		}
		return "--- Class Scope ---\n\n Binding not initialized"; //$NON-NLS-1$
	}
}
