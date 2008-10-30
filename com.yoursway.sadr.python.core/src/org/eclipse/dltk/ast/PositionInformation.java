/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast;

public final class PositionInformation {
	public final int sourceStart;
	public final int sourceEnd;
	public final int nameStart;
	public final int nameEnd;

	public PositionInformation(int nameStart, int nameEnd, int sourceStart,
			int sourceEnd) {

		this.nameStart = nameStart;
		this.nameEnd = nameEnd;
		this.sourceEnd = sourceEnd;
		this.sourceStart = sourceStart;
	}

	public String toString() {
		return "[" + sourceStart + "," + sourceEnd + "]";
	}

	public boolean equals(Object o) {
		if (o instanceof PositionInformation) {
			PositionInformation position = (PositionInformation) o;
			return sourceStart == position.sourceStart
					&& sourceEnd == position.sourceEnd
					&& nameStart == position.nameStart
					&& nameEnd == position.nameEnd;
		}

		return false;
	}
}
