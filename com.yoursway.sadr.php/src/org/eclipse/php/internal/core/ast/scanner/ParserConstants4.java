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

//----------------------------------------------------
// The following code was generated by CUP v0.10k
// Tue Apr 15 11:34:43 IDT 2008
//----------------------------------------------------

package org.eclipse.php.internal.core.ast.scanner;

/** CUP generated interface containing symbol constants. */
public interface ParserConstants4 {
  /* terminals */
  public static final int T_FILE = 51;
  public static final int T_REFERENCE = 88;
  public static final int T_SEMICOLON = 83;
  public static final int T_CASE = 28;
  public static final int T_DNUMBER = 5;
  public static final int T_GLOBAL = 37;
  public static final int T_ARRAY = 47;
  public static final int T_TILDA = 105;
  public static final int T_CLASS_C = 48;
  public static final int T_PAAMAYIM_NEKUDOTAYIM = 57;
  public static final int T_EXTENDS = 43;
  public static final int T_VAR_COMMENT = 58;
  public static final int T_USE = 36;
  public static final int T_MINUS_EQUAL = 72;
  public static final int T_INT_CAST = 109;
  public static final int T_BOOLEAN_OR = 84;
  public static final int T_INCLUDE = 60;
  public static final int T_EMPTY = 41;
  public static final int T_XOR_EQUAL = 79;
  public static final int T_CLASS = 42;
  public static final int T_END_HEREDOC = 53;
  public static final int T_FOR = 19;
  public static final int T_STRING = 6;
  public static final int T_DIV = 102;
  public static final int T_START_HEREDOC = 52;
  public static final int T_AT = 116;
  public static final int T_AS = 25;
  public static final int T_STRING_CAST = 111;
  public static final int T_STATIC = 123;
  public static final int T_EQUAL = 70;
  public static final int T_WHILE = 17;
  public static final int T_CLOSE_RECT = 118;
  public static final int T_SR = 98;
  public static final int T_ENDFOREACH = 22;
  public static final int T_FUNC_C = 49;
  public static final int T_EVAL = 62;
  public static final int T_OPEN_RECT = 117;
  public static final int T_NEKUDA = 106;
  public static final int T_SL = 97;
  public static final int T_INC = 107;
  public static final int T_KOVA = 87;
  public static final int T_BOOLEAN_AND = 85;
  public static final int T_ENDWHILE = 18;
  public static final int T_STRING_VARNAME = 7;
  public static final int T_DIV_EQUAL = 74;
  public static final int T_BREAK = 30;
  public static final int T_DEFINE = 59;
  public static final int T_BACKQUATE = 129;
  public static final int T_AND_EQUAL = 77;
  public static final int T_DEFAULT = 29;
  public static final int T_SR_EQUAL = 81;
  public static final int T_VARIABLE = 8;
  public static final int T_SL_EQUAL = 80;
  public static final int T_PRINT = 69;
  public static final int T_CURLY_OPEN = 55;
  public static final int T_ENDIF = 120;
  public static final int T_ELSEIF = 121;
  public static final int T_MINUS = 100;
  public static final int T_IS_EQUAL = 89;
  public static final int T_UNSET_CAST = 115;
  public static final int T_INCLUDE_ONCE = 61;
  public static final int T_OLD_FUNCTION = 33;
  public static final int T_BAD_CHARACTER = 12;
  public static final int T_OBJECT_CAST = 113;
  public static final int T_OR_EQUAL = 78;
  public static final int T_INLINE_HTML = 10;
  public static final int T_NEW = 119;
  public static final int T_SINGLE_QUATE = 130;
  public static final int T_UNSET = 39;
  public static final int T_MOD_EQUAL = 76;
  public static final int T_DOLLAR = 127;
  public static final int T_ENDSWITCH = 27;
  public static final int T_FOREACH = 21;
  public static final int T_NEKUDOTAIM = 126;
  public static final int EOF = 0;
  public static final int T_PLUS = 99;
  public static final int T_NUM_STRING = 9;
  public static final int T_ENDFOR = 20;
  public static final int T_IS_SMALLER_OR_EQUAL = 93;
  public static final int T_REQUIRE_ONCE = 64;
  public static final int T_LNUMBER = 4;
  public static final int T_FUNCTION = 32;
  public static final int T_QUATE = 128;
  public static final int T_IS_NOT_EQUAL = 90;
  public static final int T_ENDDECLARE = 24;
  public static final int T_CURLY_CLOSE = 56;
  public static final int T_PRECENT = 103;
  public static final int T_PLUS_EQUAL = 71;
  public static final int error = 1;
  public static final int T_ELSE = 122;
  public static final int T_DO = 16;
  public static final int T_RGREATER = 95;
  public static final int T_CONTINUE = 31;
  public static final int T_IS_IDENTICAL = 91;
  public static final int T_ECHO = 15;
  public static final int T_DOUBLE_ARROW = 45;
  public static final int T_CHARACTER = 11;
  public static final int T_TIMES = 101;
  public static final int T_REQUIRE = 63;
  public static final int T_ARRAY_CAST = 112;
  public static final int T_CONSTANT_ENCAPSED_STRING = 14;
  public static final int T_ENCAPSED_AND_WHITESPACE = 13;
  public static final int T_SWITCH = 26;
  public static final int T_DOUBLE_CAST = 110;
  public static final int T_LINE = 50;
  public static final int T_BOOL_CAST = 114;
  public static final int T_CONST = 34;
  public static final int T_RETURN = 35;
  public static final int T_IS_NOT_IDENTICAL = 92;
  public static final int T_IS_GREATER_OR_EQUAL = 94;
  public static final int T_LOGICAL_AND = 68;
  public static final int T_EXIT = 2;
  public static final int T_DOLLAR_OPEN_CURLY_BRACES = 54;
  public static final int T_LOGICAL_OR = 66;
  public static final int T_CLOSE_PARENTHESE = 125;
  public static final int T_NOT = 104;
  public static final int T_CONCAT_EQUAL = 75;
  public static final int T_LOGICAL_XOR = 67;
  public static final int T_ISSET = 40;
  public static final int T_QUESTION_MARK = 82;
  public static final int T_OPEN_PARENTHESE = 124;
  public static final int T_LIST = 46;
  public static final int T_OR = 86;
  public static final int T_COMMA = 65;
  public static final int T_DEC = 108;
  public static final int T_MUL_EQUAL = 73;
  public static final int T_VAR = 38;
  public static final int T_LGREATER = 96;
  public static final int T_IF = 3;
  public static final int T_DECLARE = 23;
  public static final int T_OBJECT_OPERATOR = 44;
}

