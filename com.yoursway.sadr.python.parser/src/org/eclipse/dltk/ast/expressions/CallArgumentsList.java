/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast.expressions;

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;

public class CallArgumentsList extends ASTListNode {
	public static final CallArgumentsList EMPTY = new CallArgumentsList() {

		public void addNode(ASTNode s) {
			throw new IllegalStateException("This object is unmodifiable");
		}

		public void setChilds(List l) {
			throw new IllegalStateException("This object is unmodifiable");
		}

		public void setEnd(int end) {
			throw new IllegalStateException("This object is unmodifiable");
		}

		public void setStart(int start) {
			throw new IllegalStateException("This object is unmodifiable");
		}

	};

	public CallArgumentsList() {
		super();
	}

	public CallArgumentsList(int start, int end) {
		super(start, end);
	}
}
