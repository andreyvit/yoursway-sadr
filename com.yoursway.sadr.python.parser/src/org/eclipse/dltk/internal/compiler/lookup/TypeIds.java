/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.compiler.lookup;

public interface TypeIds {

    //base type void null undefined Object String
	//should have an id that is 0<= id <= 15
    // The IDs below must be representable using 4 bits so as to fit in operator signatures.
	final int T_undefined = 0; // should not be changed
	
	final int NoId = Integer.MAX_VALUE;	
}
