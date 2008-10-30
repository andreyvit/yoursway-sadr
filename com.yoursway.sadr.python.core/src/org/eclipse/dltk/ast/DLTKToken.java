/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast;

public class DLTKToken {
	public static final int INVALID_TYPE = 0;
	public static final int EOF_TYPE = 1;
	public static final int SKIP = -1;

	protected int line;
	protected String text;
	protected int col;

	// each Token has at least a token type
	protected int type = INVALID_TYPE;

	// the illegal token object
	public static DLTKToken badToken = new DLTKToken(INVALID_TYPE, "<no text>");

	public DLTKToken() {
		line = 0;
		col = 0;
	}

	public DLTKToken(int t) {
		type = t;
	}

	public DLTKToken(int t, String txt) {
		type = t;
		setText(txt);
		line = 0;
		col = 0;
	}

	public int getColumn() {
		return this.col;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String t) {
		this.text = t;
	}

	public void setColumn(int c) {
		this.col = c;
	}

	public int getType() {
		return type;
	}

	public void setType(int t) {
		type = t;
	}

	public String toString() {
		return "[\"" + getText() + "\",<" + getType() + ">]";
	}

	public void setLine(int line) {
		this.line = line;
	}
}