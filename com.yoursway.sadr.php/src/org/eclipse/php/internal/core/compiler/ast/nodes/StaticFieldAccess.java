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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents a static field access.
 * <pre>e.g.<pre> MyClass::$a
 * MyClass::$$a[3]
 */
public class StaticFieldAccess extends StaticDispatch {

	private final Expression field;

	public StaticFieldAccess(int start, int end, Expression dispatcher, Expression field) {
		super(start, end, dispatcher);

		assert field != null;
		this.field = field;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			getDispatcher().traverse(visitor);
			field.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.STATIC_FIELD_ACCESS;
	}

	public Expression getField() {
		return field;
	}
}
