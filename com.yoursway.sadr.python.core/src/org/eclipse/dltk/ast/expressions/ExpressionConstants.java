/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
/*
 * (c) 2002, 2005 xored software and others all rights reserved. http://www.xored.com
 */
package org.eclipse.dltk.ast.expressions;

/**
 * 
 * Holds all constants used in expression. This is language independent set of
 * constants.
 * 
 */
public interface ExpressionConstants {
	/**
	 * Expression Constants
	 */
	static public final int E_PLUS = 1000;

	static public final int E_MINUS = 1001;

	static public final int E_MULT = 1002;

	static public final int E_DIV = 1003;

	static public final int E_LOR = 1004;
	
	static public final int E_LOR_ASSIGN = 1104;

	static public final int E_XOR = 1005;

	static public final int E_BOR = 1006;

	static public final int E_EMPTY = 1006;

	static public final int E_BAND = 1007;

	static public final int E_LAND = 1008;
	
	static public final int E_LAND_ASSIGN = 1108;

	static public final int E_LSHIFT = 1009;

	static public final int E_RSHIFT = 1010;
	
	static public final int E_LSHIFT_ASSIGN = 1109;

	static public final int E_RSHIFT_ASSIGN = 1110;

	static public final int E_BXOR = 1011;

	static public final int E_ALLOC = 1012;

	static public final int E_EQUAL = 1013;

	static public final int E_LT = 1014;

	static public final int E_LE = 1015;

	static public final int E_GT = 1016;

	static public final int E_GE = 1017;

	static public final int E_CONCAT = 1018;

	static public final int E_MOD = 1019;

	static public final int E_DOT_ASSIGN = 1020;

	static public final int E_ARRAY = 1021;

	static public final int E_IDENTICAL = 1022;

	static public final int E_LIST = 1023;

	static public final int E_NOTIDENTICAL = 1024;

	static public final int E_NOT_IDENTICAL = 1025;

	static public final int E_LNOT = 1026;

	static public final int E_BNOT = 1027;

	static public final int E_BNOT_ASSIGN = 1028;

	static public final int E_NOT_EQUAL = 1029;

	static public final int E_NOT_EQUAL2 = 1030;

	static public final int E_DIV_ASSIGN = 1031;

	static public final int E_PLUS_ASSIGN = 1032;

	static public final int E_INC = 1033;

	static public final int E_MINUS_ASSIGN = 1034;

	static public final int E_DEC = 1035;

	static public final int E_MOD_ASSIGN = 1036;

	static public final int E_MULT_ASSIGN = 1037;

	static public final int E_SR_ASSIGN = 1038;

	static public final int E_SL_ASSIGN = 1039;

	static public final int E_BXOR_ASSIGN = 1040;

	static public final int E_BAND_ASSIGN = 1041;

	static public final int E_DOUBLE_ARROW = 1042;

	static public final int E_SINGLE_ARROW = 1043;

	static public final int E_DOLLAR = 1045;

	static public final int E_COMMA = 1046;

	static public final int E_BOR_ASSIGN = 1047;

	static public final int E_CONCAT_ASSIGN = 1048;

	static public final int E_INDEX = 1049;

	static public final int E_NOTIN = 1050;

	static public final int E_IS = 1051;

	static public final int E_ISNOT = 1052;

	static public final int E_IN = 1053;

	static public final int E_ASSIGN = 1054;

	static public final int NUMBER_LITERAL = 1055;

	static public final int STRING_LITERAL = 1056;

	static public final int BOOLEAN_LITERAL = 1057;

	static public final int E_IMPORT = 1058;

	static public final int E_INSTANSEOF = 1059;

	static public final int E_CAST = 1060;

	static public final int E_PAREN = 1061;

	static public final int E_CONDITIONAL = 1062;

	static public final int E_REF = 1063;

	static public final int E_EXPRESSION_LIST = 1064;

	static public final int E_REFERENCE = 1065;

	static public final int E_PRINT = 1066;

	static public final int E_CALL = 1067;

	static public final int E_DOT = 1068;

	static public final int E_CURLY = 1069;

	static public final int E_FACTOR = 1070;

	static public final int E_IDENTIFIER = 1071;

	static public final int E_DICT = 1072;

	static public final int E_SUBSCRIPT = 1073;

	static public final int E_AT = 1074;

	static public final int E_TILDE = 1075;

	static public final int E_DOUBLESTAR_ASSIGN = 1076;

	static public final int E_DOUBLEDIV_ASSIGN = 1077;

	static public final int E_POWER = 1078;
	
	static public final int E_POWER_ASSIGN = 1079;

	public static final int E_DOUBLEDIV = 1080;


	static public final int USER_EXPRESSION_START = 10000;
}
