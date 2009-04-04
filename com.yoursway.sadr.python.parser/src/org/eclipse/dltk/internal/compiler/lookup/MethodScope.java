/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.compiler.lookup;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.compiler.env.lookup.Scope;

public class MethodScope extends BlockScope {
	MethodDeclaration referenceMethod;
	public MethodScope(Scope parent, MethodDeclaration referenceMethod ) {
		super(METHOD_SCOPE, parent);
	}
}
