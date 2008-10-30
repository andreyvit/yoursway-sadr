/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast;

public class ASTCaching {

	private ASTCaching() {
	}
	
	public static final ASTCaching ALLOW_ANY = new ASTCaching();
	
	public static final ASTCaching REPARSE = new ASTCaching();
	
	public static final ASTCaching CACHED_ONLY = new ASTCaching();
	
}
