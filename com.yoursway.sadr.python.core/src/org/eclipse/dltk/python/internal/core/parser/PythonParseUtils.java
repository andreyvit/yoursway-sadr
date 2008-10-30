/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.internal.core.parser;


public class PythonParseUtils {
	
	public static int endLineOrSymbol(int from, String content) {
		int pos = 0;
		for (pos = from; pos < content.length(); ++pos) {
			char c = content.charAt(pos);
			if (c == '\n' || c == '\r' || c == ';') {
				return pos;
			}
			if (!Character.isWhitespace(c)) {
				return pos;
			}
		}
		if (pos == content.length()) {
			return pos;
		}
		return from;
	}

	public static int startLineOrSymbol(int from, String content) {
		if (from == -1) {
			from = 0;
		}
		if (from >= content.length())
			from--;
		for (int pos = from - 1; pos > 0; --pos) {
			char c = content.charAt(pos);
			if (c == '\n' || c == '\r' || c == ';') {
				return pos + 1;
			}
			if (!Character.isWhitespace(c)) {
				return pos + 1;
			}
		}
		return from;
	}

	public static int endLineOrNoSymbol(int from, String content) {
		int pos = 0;
		if (from == -1) {
			from = 0;
		}
		if (from >= content.length())
			from--;
		for (pos = from; pos < content.length(); ++pos) {
			if (checkBounds(content, pos)) {
				if (content.charAt(pos) == '$' && pos == from) {
					continue;
				}
				return pos;
			}
		}
		if (pos == content.length()) {
			return pos;
		}
		return pos;
	}

	private static boolean checkBounds(String content, int pos) {
		char[] syms = { ' ', '\t', '\n', '\r', ']', '[', '}', '{', '(', ')',
				'$' };
		char c = content.charAt(pos);
		for (int i = 0; i < syms.length; ++i) {
			if (syms[i] == c) {
				return true;
			}
		}
		return false;
	}

	public static int startLineOrNoSymbol(int from, String content) {
		if (from == -1) {
			from = 0;
		}
		if (from >= content.length())
			from--;
		int pos;
		for (pos = from; pos > 0; --pos) {
			if (checkBounds(content, pos)) {
				if (content.charAt(pos) == '$') {
					return pos;
				}
				return pos + 1;
			}
		}
		return pos;
	}
}
