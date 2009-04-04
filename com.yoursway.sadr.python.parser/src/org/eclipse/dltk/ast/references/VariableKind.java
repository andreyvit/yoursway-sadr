/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast.references;

public interface VariableKind {
	
	public static final int FIRST_VARIABLE_ID = 0;
	
	int getId();
	
	public class Unknown implements VariableKind {
		
		public static final int ID = FIRST_VARIABLE_ID + 0;

		public int getId() {
			return ID;
		}
	}

	public class Local implements VariableKind {
		
		public static final int ID = FIRST_VARIABLE_ID + 1;

		public int getId() {
			return ID;
		}

	}

	public class Global implements VariableKind {
		
		public static final int ID = FIRST_VARIABLE_ID + 2;

		public int getId() {
			return ID;
		}

	}

	public class Instance implements VariableKind {
		
		public static final int ID = FIRST_VARIABLE_ID + 3;

		public int getId() {
			return ID;
		}

	}

	public class Class implements VariableKind {
		
		public static final int ID = FIRST_VARIABLE_ID + 4;

		public int getId() {
			return ID;
		}
		
	}
	
	public class Mixin implements VariableKind {
		
		public static final int ID = FIRST_VARIABLE_ID + 5;

		public int getId() {
			return ID;
		}
		
	}
	
	public class Argument implements VariableKind {
		
		public static final int ID = FIRST_VARIABLE_ID + 6;

		public int getId() {
			return ID;
		}
		
	}
	
	public static final int LAST_CORE_VARIABLE_ID = FIRST_VARIABLE_ID + 50;
	
	public static final int LAST_VARIABLE_ID = LAST_CORE_VARIABLE_ID + 50;

	public static final VariableKind UNKNOWN = new Unknown();
	
	public static final VariableKind LOCAL = new Local();

	public static final VariableKind GLOBAL = new Global();

	public static final VariableKind INSTANCE = new Instance();

	public static final VariableKind CLASS = new Class();
	
	public static final VariableKind MIXIN = new Mixin();
	
	public static final VariableKind ARGUMENT = new Argument();

}
