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

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;

/**
 * @see IIncludeBinding
 * @author Roy 2008
 */
public class IncludeBinding implements IIncludeBinding {

	private final ISourceModule model;
	private final String name;
	private ISourceModule includedSourceModule;

	public IncludeBinding(ISourceModule model, Include includeDeclaration) {
		super();
		final String scalars = ASTNodes.getScalars(includeDeclaration.getExpression());
		this.model = model;		
		this.name = scalars.replace("\'", "").replace("\"", "");
		this.includedSourceModule = FileNetworkUtility.findSourceModule(this.model, this.name);
	}	
	
	public String getName() {
		return name;
	}

	public String[] getNameComponents() {
		return null;
	}

	public String getKey() {
		return model.getHandleIdentifier() + "#" + name;
	}

	public int getKind() {
		return IBinding.INCLUDE;
	}

	public int getModifiers() {
		return 0;
	}

	/**
	 * TODO handle dirname(__FILE__) or other expressions 
	 */
	public IModelElement getPHPElement() {
		return this.includedSourceModule;
	}

	public boolean isDeprecated() {
		return false;
	}
}
