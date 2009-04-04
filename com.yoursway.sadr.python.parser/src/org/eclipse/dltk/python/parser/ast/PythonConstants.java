/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

public interface PythonConstants
{

	// Statements
	public static final int S_ASSERT 	= 37000;
	public static final int S_DELETE 	= 37001;
	public static final int S_IMPORTFROM= 37002;
	public static final int S_IMPORT 	= 37003;
	public static final int S_RAISE 	= 37004;
	public static final int S_YIELD 	= 37005;
	public static final int S_WITH 		= 37006;
	
	// Expressions
	public static final int E_ALLIMPORTS= 38001;
	public static final int E_DICT 		= 38002;
	public static final int E_IMPORTAS	= 38003;
	public static final int E_IMPORT	= 38004;
	public static final int E_SUBSCRIPT	= 38005;
	public static final int E_TESTLIST	= 38006;
	public static final int E_LISTFOR 	= 38007;
	public static final int E_FORIFLIST = 38008;
	public static final int E_TURPLE 	= 38009;
	public static final int S_EXCEPT 	= 38010;
	public static final int E_LAMBDA 	= 38011;
}
