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
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents an interface declaration
 * <pre>
 * <pre>e.g.<pre>
 * interface MyInterface { },
 * interface MyInterface extends Interface1, Interface2 {
 *	 const MY_CONSTANT = 3;
 *	 public function myFunction($a);
 * }
 */
public class InterfaceDeclaration extends TypeDeclaration implements IPHPDocAwareDeclaration {

	private PHPDocBlock phpDoc;

	public InterfaceDeclaration(int start, int end, int nameStart, int nameEnd, String interfaceName, List<TypeReference> interfaces, Block body, PHPDocBlock phpDoc) {
		super(interfaceName, nameStart, nameEnd, start, end);

		this.phpDoc = phpDoc;

		ASTListNode parentsList = new ASTListNode(start, end);
		for (TypeReference intface: interfaces) {
			parentsList.addNode(intface);
		}
		setSuperClasses(parentsList);

		setBody(body);
		
		setModifier(Modifiers.AccInterface);
	}

	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public int getKind() {
		return ASTNodeKinds.INTERFACE_DECLARATION;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
