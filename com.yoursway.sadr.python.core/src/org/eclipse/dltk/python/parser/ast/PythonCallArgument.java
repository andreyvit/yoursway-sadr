/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.utils.CorePrinter;

public class PythonCallArgument extends ASTNode {
	
	private ASTNode value;
	
	private int star;

	public ASTNode getValue() {
		return value;
	}

	public void setValue(ASTNode value) {
		this.value = value;
	}

	public PythonCallArgument(ASTNode value) {
		super(value.sourceStart(), value.sourceEnd());
		this.value = value;
	}
	
	public PythonCallArgument(ASTNode value, int star) {
		super(value.sourceStart(), value.sourceEnd());
		this.value = value;
		this.star = star;
	}
	
	public int getStar() {
		return star;
	}
	
	public void printNode(CorePrinter output) {
        String simpleName = getClass().getName();
        simpleName = simpleName.substring(simpleName.lastIndexOf('.')+1);
        
        List children = getChilds();
        if (children.size() == 0)
            output.formatPrintLn(simpleName);
        else {
            if(star == PythonArgument.STAR)
                output.formatPrint("*");
            if(star == PythonArgument.DOUBLESTAR)
                output.formatPrint("**");
            value.printNode(output);
        }
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (getValue() != null)
				getValue().traverse(visitor);
			visitor.endvisit(this);
		}
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PythonCallArgument))
			return false;
		PythonCallArgument arg = (PythonCallArgument) obj;
		return (arg.star == star && arg.value == value);
	}
}
