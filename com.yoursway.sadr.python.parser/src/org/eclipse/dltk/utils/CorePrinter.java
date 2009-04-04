/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.utils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class CorePrinter extends PrintWriter {
	private int fTabLevel = 0;
	private boolean fAfterNewLine = false;

	public CorePrinter(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
	}

	public CorePrinter(OutputStream out) {
		super(out);
	}

	public CorePrinter(Writer out, boolean autoFlush) {
		super(out, autoFlush);
	}

	public CorePrinter(Writer out) {
		super(out);
	}

	public void indent() {
		this.print("{");
		this.println("");
		this.formatPrint(" ");
		fTabLevel += 1;
	}

	public void dedent() {
		if (fTabLevel > 0) {
			this.fTabLevel -= 1;
		}
		this.printTabs();
		this.print("}");
		this.fAfterNewLine = true;
	}

	private void printTabs() {
		String tabs = "";
		for (int i = 0; i < this.fTabLevel; ++i) {
			tabs += "\t";
		}
		this.print(tabs);
	}

	public void formatPrintLn(String text) {
		if (fAfterNewLine) {
			fAfterNewLine = false;
			this.printTabs();
		}
		this.print(text.replaceAll("\n", "/n"));
	}

	public void formatPrint(String text) {
		String strs[] = text.split("\n");
		int count = 0;
		for (int i = 0; i < strs.length; ++i) {
			this.printTabs();
			if (count != strs.length) {
				this.println(strs[i]);
			} else {
				this.print(strs[i]);
			}
			count += 1;
		}
		fAfterNewLine = true;
	}
}
