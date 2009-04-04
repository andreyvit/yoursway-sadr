/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.compiler.lookup;

import org.eclipse.dltk.ast.ASTNode;

public interface TagBits {
    
	// Tag bits in the tagBits int of every TypeBinding	
	long IsBaseType = ASTNode.Bit1;
	long IsArgument = ASTNode.Bit2;
	

	long DefaultValueResolved = ASTNode.Bit51L;
}
