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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a class declaration
 * <pre>
 * <pre>e.g.<pre>
 * class MyClass { },
 * class MyClass extends SuperClass implements Interface1, Interface2 {
 *   const MY_CONSTANT = 3;
 *   public static final $myVar = 5, $yourVar;
 *   var $anotherOne;
 *   private function myFunction($a) { }
 * }
 */
public class ClassDeclaration extends TypeDeclaration implements IPHPDocAwareDeclaration {

	private PHPDocBlock phpDoc;
	private TypeReference superClass;
	private List<TypeReference> interfaceList;

	public ClassDeclaration(int start, int end, int nameStart, int nameEnd, int modifier, String className, TypeReference superClass, List<TypeReference> interfaces, Block body, PHPDocBlock phpDoc) {
		super(className, nameStart, nameEnd, start, end);

		setModifiers(modifier);
		this.phpDoc = phpDoc;
		
		this.superClass = superClass;
		this.interfaceList = interfaces;

		setBody(body);
	}
	
	public TypeReference getSuperClass() {
		return superClass;
	}
	
	public String getSuperClassName() {
		if (superClass != null) {
			return superClass.getName();
		}
		return null;
	}
	
	public List<TypeReference> getInterfaceList() {
		return interfaceList;
	}
	
	public String[] getInterfaceNames() {
		if (interfaceList != null) {
			String[] names = new String[interfaceList.size()];
			int i = 0;
			for (TypeReference iface : interfaceList) {
				names[i++] = iface.getName();
			}
			return names;
		}
		return null;
	}
	
	public void setSuperClass(TypeReference superClass) {
		this.superClass = superClass;
	}
	
	public void addInterface(TypeReference iface) {
		if (interfaceList == null) {
			interfaceList = new LinkedList<TypeReference>();
		}
		interfaceList.add(iface);
	}
	
	public void setInterfaceList(List<TypeReference> interfaceList) {
		this.interfaceList = interfaceList;
	}
	
	public ASTListNode getSuperClasses() {
		ASTListNode listNode = new ASTListNode(sourceStart(), sourceEnd());
		if (superClass != null) {
			listNode.addNode(superClass);
		}
		if (interfaceList != null) {
			for (TypeReference iface : interfaceList) {
				listNode.addNode(iface);
			}
		}
		return listNode;
	}
	
	public List<String> getSuperClassNames() {
		List<String> names = new LinkedList<String>();
		if (superClass != null) {
			names.add(superClass.getName());
		}
		if (interfaceList != null) {
			for (TypeReference iface : interfaceList) {
				names.add(iface.getName());
			}
		}
		return names;
	}

	public final void addSuperClass(ASTNode expression) {
		throw new IllegalStateException("Use setSuperClass() or setInterfaceList()/addInterface() instead");
	}

	public final void setSuperClasses(ASTListNode exprList) {
		throw new IllegalStateException("Use setSuperClass() or setInterfaceList()/addInterface() instead");
	}

	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_DECLARATION;
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
