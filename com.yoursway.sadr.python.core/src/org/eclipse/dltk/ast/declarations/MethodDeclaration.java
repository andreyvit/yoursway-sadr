/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *

 *******************************************************************************/
/*
 * (c) 2002, 2005 xored software and others all rights reserved. http://www.xored.com
 */

package org.eclipse.dltk.ast.declarations;

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.ArgumentList;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.internal.compiler.lookup.MethodScope;
import org.eclipse.dltk.utils.CorePrinter;

public class MethodDeclaration extends Declaration {
	public MethodScope scope;

	protected ArgumentList arguments;

	private Block body = new Block();

	private List decorators;

	private String declaringTypeName;

	public MethodDeclaration(DLTKToken function_t, DLTKToken name) {
		super(name, function_t.getColumn(), name.getColumn()
				+ name.getText().length());
	}

	public MethodDeclaration(String name, int nameStart, int nameEnd,
			int declStart, int declEnd) {
		super(declStart, declEnd);
		this.setName(name);
		this.setNameStart(nameStart);
		this.setNameEnd(nameEnd);
	}

	public MethodDeclaration(int start, int end) {
		super(start, end);
	}

	public void setDecorators(List decorators) {
		this.decorators = decorators;
	}

	public List getDecorators() {
		return this.decorators;
	}

	public int getKind() {
		return D_METHOD;
	}

	public void traverse(ASTVisitor visitor) throws Exception {

		if (visitor.visit(this)) {
			// Deocrators
			if (this.decorators != null) {
				if (this.decorators != null) {
					Iterator it = this.decorators.iterator();
					while (it.hasNext()) {
						Decorator dec = (Decorator) it.next();
						dec.traverse(visitor);
					}
				}
			}

			// Arguments
			if (this.arguments != null) {
				this.arguments.traverse(visitor);
			}

			// Body
			if (this.body != null) {
				this.body.traverse(visitor);
			}

			visitor.endvisit(this);
		}
	}

	public List getArguments() {
		return this.arguments.getArguments();
	}

	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}

	public void acceptArguments(ArgumentList params) {
		this.arguments = params;
	}

	public void acceptBody(Block block) {
		this.acceptBody(block, true);
	}

	public void setBody(ASTListNode statement) {
		Block b = new Block(statement.sourceStart(), statement.sourceEnd());
		b.acceptStatements(statement.getChilds());
		this.acceptBody(b, true);
	}

	public void acceptBody(Block block, boolean replace) {
		this.body = block;

		if (block != null) {
			if (replace) {
				this.setEnd(block.sourceEnd());
			}
		}
	}

	public List getStatements() {
		if (this.body == null) {
			this.body = new Block(this.sourceStart(), this.sourceEnd());
		}
		return this.body.getStatements();
	}

	public Block getBody() {
		return this.body;
	}

	public void printNode(CorePrinter output) {
		if (this.decorators != null) {
			Iterator i = this.decorators.iterator();
			while (i.hasNext()) {
				((Decorator) i.next()).printNode(output);
			}
		}
		output.formatPrintLn("Method" + this.getSourceRange().toString()
				+ this.getNameSourceRange().toString() + ": "
				+ super.toString());
		output.formatPrintLn("(");
		if (this.arguments != null && this.arguments.getArguments().size() > 0) {
			this.arguments.printNode(output);
		}
		output.formatPrintLn(")");
		if (this.body != null) {
			this.body.printNode(output);
		}
	}

	public void setDeclaringTypeName(String name) {
		if (name != null && name.length() > 0) {
			this.declaringTypeName = name;
		}
	}

	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof MethodDeclaration)) {
			return false;
		}
		MethodDeclaration d = (MethodDeclaration) obj;
		if ((this.declaringTypeName == null && d.declaringTypeName != null)
				|| (this.declaringTypeName != null && d.declaringTypeName == null)) {
			return false;
		}
		// Only name.
		return d.getName().equals(this.getName())
				&& d.getNameStart() == this.getNameStart()
				&& d.getNameEnd() == this.getNameEnd()
				&& (this.declaringTypeName == null || this.declaringTypeName
						.equals(d.declaringTypeName));
	}
}
