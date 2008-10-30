lexer grammar python_v3;
options {
  language=Java;

}
@members {
/** Handles context-sensitive lexing of implicit line joining such as
 *  the case where newline is ignored in cases like this:
 *  a = [3,
 *       4]
 */
public int implicitLineJoiningLevel = 0;
public int startPos=-1;
public void emitErrorMessage(String msg) {
}
}
@header {
	package org.eclipse.dltk.python.internal.core.parsers;
}

T69 : 'def' ;
T70 : 'print' ;
T71 : 'del' ;
T72 : 'pass' ;
T73 : 'break' ;
T74 : 'continue' ;
T75 : 'return' ;
T76 : 'yield' ;
T77 : 'raise' ;
T78 : 'import' ;
T79 : 'from' ;
T80 : 'as' ;
T81 : 'global' ;
T82 : 'exec' ;
T83 : 'in' ;
T84 : 'assert' ;
T85 : 'if' ;
T86 : 'elif' ;
T87 : 'else' ;
T88 : 'while' ;
T89 : 'for' ;
T90 : 'try' ;
T91 : 'except' ;
T92 : 'finally' ;
T93 : 'or' ;
T94 : 'and' ;
T95 : 'not' ;
T96 : 'is' ;
T97 : 'lambda' ;
T98 : 'with' ;
T99 : 'class' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1668
LPAREN	: '(' {implicitLineJoiningLevel++;} ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1670
RPAREN	: ')' {implicitLineJoiningLevel--;} ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1672
LBRACK	: '[' {implicitLineJoiningLevel++;} ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1674
RBRACK	: ']' {implicitLineJoiningLevel--;} ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1676
COLON 	: ':' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1678
COMMA	: ',' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1680
SEMI	: ';' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1682
PLUS	: '+' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1684
MINUS	: '-' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1686
STAR	: '*' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1688
SLASH	: '/' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1690
VBAR	: '|' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1692
AMPER	: '&' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1694
LESS	: '<' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1696
GREATER	: '>' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1698
ASSIGN	: '=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1700
PERCENT	: '%' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1702
BACKQUOTE	: '`' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1704
LCURLY	: '{' {implicitLineJoiningLevel++;} ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1706
RCURLY	: '}' {implicitLineJoiningLevel--;} ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1708
CIRCUMFLEX	: '^' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1710
TILDE	: '~' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1712
EQUAL	: '==' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1714
NOTEQUAL	: '!=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1716
ALT_NOTEQUAL: '<>' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1718
LESSEQUAL	: '<=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1720
LEFTSHIFT	: '<<' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1722
GREATEREQUAL	: '>=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1724
RIGHTSHIFT	: '>>' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1726
PLUSEQUAL	: '+=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1728
MINUSEQUAL	: '-=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1730
DOUBLESTAR	: '**' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1732
STAREQUAL	: '*=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1734
DOUBLESLASH	: '//' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1736
SLASHEQUAL	: '/=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1738
VBAREQUAL	: '|=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1740
PERCENTEQUAL	: '%=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1742
AMPEREQUAL	: '&=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1744
CIRCUMFLEXEQUAL	: '^=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1746
LEFTSHIFTEQUAL	: '<<=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1748
RIGHTSHIFTEQUAL	: '>>=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1750
DOUBLESTAREQUAL	: '**=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1752
DOUBLESLASHEQUAL	: '//=' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1754
DOT : '.' ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1756
FLOAT  	:	POINTFLOAT | EXPONENTFLOAT
//	:	'.' DIGITS (Exponent)?
//    |   DIGITS ('.' (DIGITS (Exponent)?)? | Exponent)
    ;
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1760
POINTFLOAT
	:	DIGITS? FRACTION | DIGITS '.'
	;
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1763
FRACTION 
	:	'.' DIGITS
	;
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1766
EXPONENTFLOAT 
	:	(DIGITS | POINTFLOAT) Exponent
	;
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1769
fragment
Exponent
	:	('e' | 'E') ( '+' | '-' )? DIGITS
	;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1774
INT :   // Hex
        '0' ('x' | 'X') ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+
        ('l' | 'L')?
    |   // Octal
        '0' DIGITS*
        ('l' | 'L')?
    |   // Decimal
    	'1'..'9' DIGITS*
        ('l' | 'L')?
    ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1785
COMPLEX
    :   DIGITS ('j'|'J')
    |   FLOAT ('j'|'J')
    ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1790
fragment
DIGITS : ( '0' .. '9' )+ ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1793
NAME:	( 'a' .. 'z' | 'A' .. 'Z' | '_')
        ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
    ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1797
/** Match various string types.  Note that greedy=false implies '''
 *  should make us exit loop not continue.
 */
STRING
    :   ('u'|'U')?('r'|'R')?
        (   '\'\'\'' (options {greedy=false;}:TRIAPOS)* '\'\'\''
        |   '"""' (options {greedy=false;}:TRIQUOTE)* '"""'
        |   '"' (ESC|~('\\'|'\n'|'"'))* '"'
        |   '\'' (ESC|~('\\'|'\n'|'\''))* '\''
        )
    ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1809
/** the two '"'? cause a warning -- is there a way to avoid that? */
fragment
TRIQUOTE
    : '"'? '"'? (ESC|~('\\'|'"'))+
    ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1815
/** the two '\''? cause a warning -- is there a way to avoid that? */
fragment
TRIAPOS
    : '\''? '\''? (ESC|~('\\'|'\''))+
    ;
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1820
fragment
ESC
	:	'\\' .
	;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1825
/** Consume a newline and any whitespace at start of next line */
CONTINUED_LINE
	:	'\\' ('\r')? '\n' (' '|'\t')* {
		 //fModule.acceptLine(getColumn());
		 /*newline();*/ $channel=HIDDEN; }
	;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1832
WS	:	{startPos>0}?=> (' '|'\t')+ {$channel=HIDDEN;}
	; 
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1834
/** Grab everything before a real symbol.  Then if newline, kill it
 *  as this is a blank line.  If whitespace followed by comment, kill it
 *  as it's a comment on a line by itself.
 *
 *  Ignore leading whitespace when nested in [..], (..), {..}.
 */
LEADING_WS
@init {
    int spaces = 0;
}
    :   {startPos==0}?=>
    	(   {implicitLineJoiningLevel>0}? ( ' ' | '\t' )+ {$channel=HIDDEN;}
       	|	( 	' '  { spaces++; }
        	|	'\t' { spaces += 8; spaces -= (spaces \% 8); }
       		)+
        	{
            // make a string of n spaces where n is column number - 1
            char[] indentation = new char[spaces];
            for (int i=0; i<spaces; i++) {
                indentation[i] = ' ';
            }
            String s = new String(indentation);
            Token tok = new ClassicToken(LEADING_WS,new String(indentation));
            emit(tok);
        }
        	// kill trailing newline if present and then ignore
        	( ('\r')? '\n' {if (token!=null) token.setChannel(99); else $channel=HIDDEN;})*
           // {token.setChannel(99); }
        )

/*
        |   // if comment, then only thing on a line; kill so we
            // ignore totally also wack any following newlines as
            // they cannot be terminating a statement
            '#' (~'\n')* ('\n')+ 
            {if (token!=null) token.setChannel(99); else $channel=HIDDEN;}
        )?
        */
    ;

// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1874
/** Comments not on line by themselves are turned into newlines because
    sometimes they are newlines like

    b = a # end of line comment

    or

    a = [1, # weird
         2]

    This rule is invoked directly by nextToken when the comment is in
    first column or when comment is on end of nonwhitespace line.

    The problem is that then we have lots of newlines heading to
    the parser.  To fix that, column==1 implies we should kill whole line.

    Consume any newlines following this comment as they are not statement
    terminators.  Don't let NEWLINE token handle them.
 */

COMMENT
@init {
    $channel=HIDDEN;
}
    :	{startPos==0}?=> (' '|'\t')* '#' (~'\n')* '\n'+
    |	{startPos>0}?=> '#' (~'\n')* // let NEWLINE handle \n unless char pos==0 for '#'
    ;
    
//SPECIAL
//{
//    int startCol = getColumn();
//}
//    :  (~'\n')* // let NEWLINE handle \n unless column = 1 for '#'
//        { $setType(Token.SKIP);        	
//        	fModule.acceptModifier($getText); 
//        	}
//        ( {startCol==1}? ('\n' {
//        	fModule.acceptLine(getColumn());newline();
//}
//        )+ )?
//    ;    
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1915
DECORATOR_S:
	'@'
;
// $ANTLR src "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g" 1918
/** Treat a sequence of blank lines as a single blank line.  If
 *  nested within a (..), {..}, or [..], then ignore newlines.
 *  If the first newline starts in column one, they are to be ignored.
 */
NEWLINE
    :   (('\r')? '\n' )+
        {if ( startPos==0 || implicitLineJoiningLevel>0 )
            $channel=HIDDEN;
        }
    ;
