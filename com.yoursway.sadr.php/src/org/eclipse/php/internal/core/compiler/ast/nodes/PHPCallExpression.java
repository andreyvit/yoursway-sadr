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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPCallExpression extends CallExpression {

	public PHPCallExpression(int start, int end, ASTNode receiver, String name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public PHPCallExpression(int start, int end, ASTNode receiver, SimpleReference name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public PHPCallExpression(ASTNode receiver, String name, CallArgumentsList args) {
		super(receiver, name, args);
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if( pVisitor.visit( this ) ) {
			if( receiver != null ) {
				receiver.traverse( pVisitor );
			}
			getCallName().traverse(pVisitor);
			if(getArgs() != null ) {
				getArgs().traverse( pVisitor );
			}
			pVisitor.endvisit( this );
		}
	}

	public int getKind() {
		return ASTNodeKinds.METHOD_INVOCATION;
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
