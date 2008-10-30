/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.ISourceRange;

public class SourceRange implements ISourceRange {
    
    private final int offset, length;
    
    public SourceRange(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }
    
    public int getLength() {
        return length;
    }
    
    public int getOffset() {
        return offset;
    }
    
    @Override
    public String toString() {
        int end = getOffset() + getLength() - 1;
        return "[" + getOffset() + "," + end + "]";
        //		StringBuffer buffer = new StringBuffer();
        //		buffer.append("[offset="); //$NON-NLS-1$
        //		buffer.append(offset);
        //		buffer.append(", length="); //$NON-NLS-1$
        //		buffer.append(length);
        //		buffer.append("]"); //$NON-NLS-1$
        //		return buffer.toString();
    }
}
