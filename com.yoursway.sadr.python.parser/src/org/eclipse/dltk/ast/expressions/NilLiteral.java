/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast.expressions;

public class NilLiteral extends Literal {

	public NilLiteral(int start, int end) {
		super(start, end);
	}

	public String getValue() {
		return "nil";
	}

	public int getKind() {
		return 0;
	}

}
