/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ASTListNode extends ASTNode {

	private final List nodes;

	public ASTListNode(int start, int end, List nodes) {
		super(start, end);
		this.nodes = nodes;
	}

	public ASTListNode(int start, int end) {
		super(start, end);
		this.nodes = new ArrayList();
	}

	public ASTListNode() {
		super(0, -1);
		this.nodes = new ArrayList();
	}

	public void addNode(ASTNode s) {
		if (s != null) {
			nodes.add(s);
		}
	}

	public List getChilds() {
		return nodes;
	}

	/**
	 * 
	 * @deprecated use getChilds(), this method are present only for compatiblity
	 *             with old code
	 */
	public List getExpressions() {
		return nodes;
	}

	/**
	 * 
	 * @deprecated use getChilds(), this method are present only for compatiblity
	 *             with old code
	 */
	public List getStatements() {
		return nodes;
	}

	public void setChilds(List l) {
		this.nodes.clear();
		this.nodes.addAll(l);
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (nodes != null) {
				for (Iterator iter = nodes.iterator(); iter.hasNext();) {
					ASTNode s = (ASTNode) iter.next();
					s.traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

}
