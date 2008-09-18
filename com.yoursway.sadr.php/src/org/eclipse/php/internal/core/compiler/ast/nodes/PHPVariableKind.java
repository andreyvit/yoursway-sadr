/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.references.VariableKind;

public interface PHPVariableKind extends VariableKind {

	public class Local extends VariableKind.Local implements PHPVariableKind {
	}

	public class Instance extends VariableKind.Instance implements PHPVariableKind {
	}

	public class Class extends VariableKind.Class implements PHPVariableKind {
	}

	public class Constant implements PHPVariableKind {

		public static final int ID = LAST_CORE_VARIABLE_ID + 4;

		public int getId() {
			return ID;
		}
	}

	/**
	 * Variable, like: <code>$name</code> anywhere in the code
	 */
	public static final PHPVariableKind LOCAL = new Local();

	/**
	 * Instance variable, like: <code>$obj->var</code>
	 */
	public static final PHPVariableKind INSTANCE = new Instance();

	/**
	 * Class variable, like: <code>DAO::$instance</code>
	 */
	public static final PHPVariableKind CLASS = new Class();

	/**
	 * Constant, like: <code>E_ALL</code>
	 */
	public static final PHPVariableKind CONSTANT = new Constant();

}
