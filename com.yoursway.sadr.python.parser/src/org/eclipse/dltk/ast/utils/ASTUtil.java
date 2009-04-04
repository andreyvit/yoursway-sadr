/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;

public class ASTUtil {
	public static List getStatements(ASTNode node) {
		if (node instanceof ModuleDeclaration) {
			return ((ModuleDeclaration) node).getStatements();
		} else if (node instanceof TypeDeclaration) {
			return ((TypeDeclaration) node).getStatements();
		} else if (node instanceof MethodDeclaration) {
			return ((MethodDeclaration) node).getStatements();
		}
		return null;
	}

	public static TypeDeclaration[] getTypes(
			List statements, List types) {
		List finalTypes = new ArrayList();
		finalTypes.addAll(types);
		
		if (statements != null) {
			Iterator it = statements.iterator();
			while (it.hasNext()) {
				ASTNode node = (ASTNode) it.next();
				if (node instanceof TypeDeclaration && !finalTypes.contains(node)) {
					finalTypes.add(node);
				}
			}
		}
		return (TypeDeclaration[]) finalTypes.toArray(new TypeDeclaration[finalTypes
				.size()]);
	}

	public static MethodDeclaration[] getMethods(List statements, List functions) {
		List finalMethods = new ArrayList();
		finalMethods.addAll(functions);
		
		if (statements != null) {
			Iterator it = statements.iterator();
			while (it.hasNext()) {
				ASTNode node = (ASTNode) it.next();
				if (node instanceof MethodDeclaration && !finalMethods.contains(node)) {
					finalMethods.add(node);
				}
			}
		}
		return (MethodDeclaration[]) finalMethods.toArray(new MethodDeclaration[finalMethods
				.size()]);
	}

	public static FieldDeclaration[] getVariables(List statements,
			List variables) {
		List finalVariables = new ArrayList();
		finalVariables.addAll(variables);

		if (statements != null) {
			Iterator it = statements.iterator();
			while (it.hasNext()) {
				ASTNode node = (ASTNode) it.next();
				if (node instanceof FieldDeclaration && !finalVariables.contains(node)) {
					finalVariables.add(node);
				}
			}
		}
		return (FieldDeclaration[]) finalVariables.toArray(new FieldDeclaration[finalVariables
				.size()]);
	}
	
}
