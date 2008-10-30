/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.python.parser.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.PositionInformation;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTestListExpression;
import org.eclipse.dltk.python.parser.ast.statements.SimpleStatement;
import org.eclipse.dltk.utils.CorePrinter;


/**
 * import statement.
 * Recive PythonListExpression as expression argument.
 * @author haiodo
 *
 */
public class PythonImportStatement extends SimpleStatement
{

	public PythonImportStatement( DLTKToken t, Expression expression ) {

		super( t, expression );
	}

	
	public int getKind( ) {
		return PythonConstants.S_IMPORT;
	}

	
	public void printNode( CorePrinter output ) {

		output.formatPrintLn( "import " );
		if( this.fExpression != null ) {
			this.fExpression.printNode(output);
		}
	}

	public List/*< Expression >*/ getImports( ) {

		Expression imexpr = this.getExpression( );
		List/*< Expression >*/ imports = new ArrayList/*< Expression >*/( );

		if( imexpr instanceof PythonTestListExpression ) {
			PythonTestListExpression testList = ( PythonTestListExpression )imexpr;
			imports = testList.getExpressions( );
		}
		else {
			imports.add( imexpr );
		}
		return imports;
	}
	public PositionInformation getPosition( ) {

		int start = sourceStart();
		int end = sourceEnd();
		return new PositionInformation(start,end, start,end );
	}
}
