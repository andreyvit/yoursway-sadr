/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.statements;

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Try statement.
 */
public class TryStatement extends Statement {
	private Block body;

	private List catchFinallyStatements;

	protected TryStatement(int start, int end) {
		super(start, end);
	}

	public TryStatement(DLTKToken tryToken, Block body, List catchFin) {

		super(tryToken);
		this.body = body;
		this.catchFinallyStatements = catchFin;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (this.body != null) {
				this.body.traverse(pVisitor);
			}
			if (this.catchFinallyStatements != null) {
				Iterator i = this.catchFinallyStatements.iterator();
				while (i.hasNext()) {
					Statement st = (Statement) i.next();
					st.traverse(pVisitor);
				}
			}
			pVisitor.endvisit(this);
		}
	}

	public int getKind() {
		return S_TRY;
	}

	public Block getBody() {
		return body;
	}

	public List getCatchFinallyStatements() {
		return catchFinallyStatements;
	}

	public void printNode(CorePrinter output) {
		output.formatPrint("try:");
		if (this.body != null) {
			this.body.printNode(output);
		}
		if (this.catchFinallyStatements != null) {
			Iterator i = this.catchFinallyStatements.iterator();
			while (i.hasNext()) {
				Statement st = (Statement) i.next();
				st.printNode(output);
				output.println("");
			}
		}
	}
}
