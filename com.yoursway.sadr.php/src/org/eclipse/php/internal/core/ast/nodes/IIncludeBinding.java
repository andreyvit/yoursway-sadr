/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

/**
 * Represents the information of the include path
 * @author Roy, May 2008
 *
 */
public interface IIncludeBinding extends IBinding {

	/**
	 * Returns the name of the include represented by this binding. For named
	 * includes, this is the fully qualified package name (using "." for 
	 * separators). For unnamed packages, this is an empty string.
	 * 
	 * @return the name of the include represented by this binding, or
	 *    an empty string for an unnamed include
	 */
	public String getName();
	
	/**
	 * Returns the list of name component making up the name of the include
	 * represented by this binding. For example, for the include named
	 * "/com/example/tool", this method returns {"com", "example", "tool"}.
	 * Returns the empty list for unnamed packages.
	 * 
	 * @return the name of the package represented by this binding, or the
	 *    empty list for unnamed packages
	 */
	public String[] getNameComponents();
}
