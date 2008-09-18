/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.mixin.PHPDocField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;

/**
 * A PHP function binding.
 * This class is also the base class for the {@link MethodBinding} implementation.
 * 
 * @author shalom
 */
public class FunctionBinding implements IFunctionBinding {

	protected static final int VALID_MODIFIERS = Modifiers.AccPublic | Modifiers.AccProtected | Modifiers.AccPrivate | Modifiers.AccDefault | Modifiers.AccStatic | Modifiers.AccFinal | Modifiers.AccAbstract;
	protected BindingResolver resolver;
	protected IMethod modelElement;

	/**
	 * Constructs a new FunctionBinding.
	 * 
	 * @param resolver A {@link BindingResolver}.
	 * @param modelElement An {@link IMethod}.
	 */
	public FunctionBinding(BindingResolver resolver, IMethod modelElement) {
		this.resolver = resolver;
		this.modelElement = modelElement;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IFunctionBinding#getExceptionTypes()
	 */
	public ITypeBinding[] getExceptionTypes() {
		// Get an array of PHPDocFields
		IModelElement[] methodDoc = PHPMixinModel.getInstance().getMethodDoc(modelElement.getDeclaringType().getElementName(), modelElement.getElementName());
		ArrayList<ITypeBinding> exeptions = new ArrayList<ITypeBinding>();
		for (IModelElement element : methodDoc) {
			PHPDocField docField = (PHPDocField) element;
			PHPDocBlock docBlock = docField.getDocBlock();
			PHPDocTag[] docTags = docBlock.getTags();
			for (PHPDocTag tag : docTags) {
				if (tag.getTagKind() == PHPDocTag.THROWS) {
					SimpleReference[] references = tag.getReferences();
					// TODO - create ITypeBinding array from this SimpleReference array
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IFunctionBinding#getName()
	 */
	public String getName() {
		return modelElement.getElementName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IFunctionBinding#getParameterTypes()
	 */
	public ITypeBinding[] getParameterTypes() {
		// TODO - Create the parameters types according to the defined types in the function declaration 
		// and in its DocBlock.
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IFunctionBinding#getReturnType()
	 */
	public ITypeBinding getReturnType() {
		// TODO
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IFunctionBinding#isVarargs()
	 */
	public boolean isVarargs() {
		// TODO
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#getKey()
	 */
	public String getKey() {
		return modelElement.getHandleIdentifier();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#getKind()
	 */
	public int getKind() {
		return IBinding.METHOD;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#getModifiers()
	 */
	public int getModifiers() {
		try {
			return modelElement.getFlags() & VALID_MODIFIERS;
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#getPHPElement()
	 */
	public IModelElement getPHPElement() {
		return modelElement;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#isDeprecated()
	 */
	public boolean isDeprecated() {
		return false;
	}
}
