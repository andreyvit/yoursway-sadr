/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;

import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.python.parser.ast.PythonConstants;

public class PythonTestListExpression extends ExpressionList {
	public PythonTestListExpression() {
		
	}
	
	public int getKind() {
		return PythonConstants.E_TESTLIST;
	}
}
