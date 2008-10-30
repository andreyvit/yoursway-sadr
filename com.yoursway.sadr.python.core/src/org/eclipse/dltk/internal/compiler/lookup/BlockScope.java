/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.compiler.lookup;

import org.eclipse.dltk.compiler.env.lookup.Scope;

public class BlockScope extends Scope {

	public Scope[] subscopes = new Scope[1]; // need access from code assist
	public int subscopeCount = 0; // need access from code assist

	public BlockScope(BlockScope parent) {
		this(parent, true);
	}

	public BlockScope(BlockScope parent, boolean addToParentScope) {
		this(Scope.BLOCK_SCOPE, parent);

		if (addToParentScope) {
			parent.addSubscope(this);
		}
	}

	public BlockScope(BlockScope parent, int variableCount) {
		this(Scope.BLOCK_SCOPE, parent);
		parent.addSubscope(this);
	}

	protected BlockScope(int kind, Scope parent) {
		super(kind, parent);
	}

	public void addSubscope(Scope childScope) {
		if (this.subscopeCount == this.subscopes.length)
			System.arraycopy(this.subscopes, 0,
					(this.subscopes = new Scope[this.subscopeCount * 2]), 0,
					this.subscopeCount);
		this.subscopes[this.subscopeCount++] = childScope;
	}

	/*
	 * Answer the index of this scope relatively to its parent. For method
	 * scope, answers -1 (not a classScope relative position)
	 */
	public int scopeIndex() {
		if (this instanceof MethodScope)
			return -1;
		BlockScope parentScope = (BlockScope) this.parent;
		Scope[] parentSubscopes = parentScope.subscopes;
		for (int i = 0, max = parentScope.subscopeCount; i < max; i++) {
			if (parentSubscopes[i] == this)
				return i;
		}
		return -1;
	}

	public String toString() {
		return toString(0);
	}

	public String toString(int tab) {
		String s = "";
		for (int i = 0; i < this.subscopeCount; i++)
			if (this.subscopes[i] instanceof BlockScope)
				s += ((BlockScope) this.subscopes[i]).toString(tab + 1) + "\n"; //$NON-NLS-1$
		return s;
	}
}
