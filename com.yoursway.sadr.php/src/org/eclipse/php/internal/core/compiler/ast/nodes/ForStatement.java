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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a for statement
 * <pre>e.g.<pre>
 * for (expr1; expr2; expr3)
 * 	 statement;
 *
 * for (expr1; expr2; expr3):
 * 	 statement
 * 	 ...
 * endfor;
 */
public class ForStatement extends Statement {

	private final Expression[] initializations;
	private final Expression[] conditions;
	private final Expression[] increasements;
	private final Statement action;

	private ForStatement(int start, int end, Expression[] initializations, Expression[] conditions, Expression[] increasements, Statement action) {
		super(start, end);

		assert initializations != null && conditions != null && increasements != null && action != null;
		this.initializations = initializations;
		this.conditions = conditions;
		this.increasements = increasements;
		this.action = action;
	}

	public ForStatement(int start, int end, List<? extends Expression> initializations, List<? extends Expression> conditions, List<? extends Expression> increasements, Statement action) {
		this(start, end,
			initializations == null ? null : (Expression[]) initializations.toArray(new Expression[initializations.size()]),
			conditions == null ? null : (Expression[]) conditions.toArray(new Expression[conditions.size()]),
			increasements == null ? null : (Expression[]) increasements.toArray(new Expression[increasements.size()]),
		action);
	}

	public void traverse(ASTVisitor visitor) throws Exception  {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (Expression initialization : initializations) {
				initialization.traverse(visitor);
			}
			for (Expression condition : conditions) {
				condition.traverse(visitor);
			}
			for (Expression increasement : increasements) {
				increasement.traverse(visitor);
			}
			action.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.FOR_STATEMENT;
	}

	public Statement getAction() {
		return action;
	}

	public Expression[] getConditions() {
		return conditions;
	}

	public Expression[] getIncreasements() {
		return increasements;
	}

	public Expression[] getInitializations() {
		return initializations;
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
