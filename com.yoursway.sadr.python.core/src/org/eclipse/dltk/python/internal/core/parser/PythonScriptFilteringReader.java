/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
/*
 * Created on 21.05.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Script - Code Style - Code Templates
 */
package org.eclipse.dltk.python.internal.core.parser;

import java.io.IOException;
import java.io.Reader;

/**
 * @author kor
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Script - Code Style - Code Templates
 */
public final class PythonScriptFilteringReader extends Reader {

	private boolean ls;

	private boolean mn;

	private boolean ma;

	private boolean inString;

	private int sttype;

	private boolean last;

	private char[] content;

	private int position;

//	private PythonElementReporter reporter;

	/**
	 * Constants
	 */

	public final static int NONE = 0;

	public final static int SINGLE_QUATA = 1;

	public final static int DOUBLE_QUATA = 2;

	public final static int TRIPLE_QUATA = 3;

	public final static int TRIPLE_Q = 4;

	private char c;

	public PythonScriptFilteringReader(char[] content ) {
		this.content = content;
		this.position = 0;
	}

	public void reset() throws IOException {
		position = 0;
	}

	public void appendNewLine() {
		/*if (this.fCompilationResult == null)
			return;
		int[] nlines = new int[this.fCompilationResult.lineSeparatorPositions.length + 1];
		System.arraycopy(fCompilationResult.lineSeparatorPositions, 0, nlines, 0,
				fCompilationResult.lineSeparatorPositions.length);
		nlines[nlines.length - 1] = position;
		fCompilationResult.lineSeparatorPositions = nlines;*/
	}

	private boolean isEscape() {
		int ma = position - 2;
		boolean esc = false;
		while ((ma > 0) && (content[ma] == '\\')) {
			ma--;
			esc = !esc;
		}
		return esc;

	}

	private int get(int p) {
		if (p < content.length)
			return content[p];
		return -1;
	}

	private void checkInString() {
		if (!ma) {
			switch (sttype) {
			case NONE:
				if (c == '\"') {
					if (get(position) == '\"')
						if (get(position + 1) == '\"') {
							sttype = TRIPLE_QUATA;
							break;
						}
					sttype = DOUBLE_QUATA;
					// check triple string;
				}
				if (c == '\'') {
					if (get(position) == '\'')
						if (get(position + 1) == '\'') {
							sttype = TRIPLE_Q;
							break;
						}
					sttype = SINGLE_QUATA;
					// check triple string;
				}
				// if (c=='\'') sttype=SINGLE_QUATA;
				break;
			case SINGLE_QUATA:
				if (c == '\'') {
					if (!isEscape())
						sttype = 0;
				}
				break;
			case DOUBLE_QUATA:
				if (c == '\"') {
					if (!isEscape())
						sttype = 0;
				}
				break;
			case TRIPLE_QUATA:
				if (c == '\"')
					if (get(position) == '\"')
						if (get(position + 1) == '\"')
							sttype = NONE;
				break;
			case TRIPLE_Q:
				if (c == '\'')
					if (get(position) == '\'')
						if (get(position + 1) == '\'')
							sttype = NONE;
			}
			inString = (sttype != 0);
		}
	}

	public int read() throws IOException {
		if (ready()) {
			c = content[position];
			if (c == '\n')
				appendNewLine();
			position += 1;
			checkInString();
			if (inString == false) {
				if (c == '#') {
					ma = true;
					return ' ';
				}
				/*if (c == '@') {
					ma = true;
					//TODO: ADD corrections
					//String s = new String(content, position, 6);
					//if (s.equals("static"))
					return ' ';
				}*/
			}
			if (c == '\n')
				ma = false;
			if (c == '\r')
				ma = false;
			if (ma)
				return ' ';
			if (c == '\n') {
				if (mn) {
					mn = false;
					return ' ';
				}
			}
			if (c == '\\') {
				if (position == content.length)
					return c;
				char la = content[position];
				if ((la != ' ') && (la != '\n') && (la != '\r') && (la != '\r'))
					return c;
				ls = true;
				return ' ';
			}
			if (ls == true) {

				if (c == '\t')
					return c;
				if (c == '\n') {
					ls = false;
					return ' ';
				}
				if (c == '\r') {
					ls = false;
					mn = true;
					return ' ';
				}
				if (c == '\t')
					if (inString)
						return ' ';
				return c;
			}
			if (inString)
				if (c == '\t') {
					return ' ';
				}
			return c;
		} else {
			if (last == false) {
				last = true;
				return '\n';
			}
			return -1;
		}
	}

	public boolean ready() throws IOException {
		return position < content.length;
	}

	public void close() throws IOException {
	}

	public int read(char[] pArg0, int off, int length) throws IOException {
		int code = 0;
		int e = off + length;
		for (int a = off; a < e; a++) {
			code = read();
			pArg0[a] = (char) code;
		}
		return code;
	}
}
