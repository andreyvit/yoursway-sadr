/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast.expressions;


import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.python.parser.ast.PythonConstants;
import org.eclipse.dltk.utils.CorePrinter;


public class PythonImportAsExpression extends PythonImportExpression
{	
	private DLTKToken fAsName;
	public PythonImportAsExpression( DLTKToken name, DLTKToken asName ) {

		super( name );
		
		this.fAsName = asName;
		this.setEnd(asName.getColumn() + asName.getText().length());
	}
	
	public int getKind() {
		return PythonConstants.E_IMPORTAS;
	}
	
	public String getAsName() {
		if( this.fAsName != null ) {
			return this.fAsName.getText();
		}
		else {
			return null;
		}
	}	
	
	public void printNode( CorePrinter output ) {
		String name = this.getName();
		if( this.fAsName != null && name != null ) {
			output.formatPrintLn( name + "as " + this.fAsName.getText() );
		}
	}
}
