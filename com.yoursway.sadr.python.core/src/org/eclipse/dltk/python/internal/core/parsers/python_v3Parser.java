// $ANTLR 3.0.1 /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g 2008-05-25 21:22:40

package org.eclipse.dltk.python.internal.core.parsers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.declarations.Decorator;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.ArgumentList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;
import org.eclipse.dltk.ast.expressions.Literal;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonAssertStatement;
import org.eclipse.dltk.python.parser.ast.PythonCallArgumentsList;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonDelStatement;
import org.eclipse.dltk.python.parser.ast.PythonExceptStatement;
import org.eclipse.dltk.python.parser.ast.PythonForStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportFromStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportStatement;
import org.eclipse.dltk.python.parser.ast.PythonRaiseStatement;
import org.eclipse.dltk.python.parser.ast.PythonTryStatement;
import org.eclipse.dltk.python.parser.ast.PythonWhileStatement;
import org.eclipse.dltk.python.parser.ast.PythonWithStatement;
import org.eclipse.dltk.python.parser.ast.PythonYieldStatement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;
import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;
import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;
import org.eclipse.dltk.python.parser.ast.expressions.EmptyExpression;
import org.eclipse.dltk.python.parser.ast.expressions.NotStrictAssignment;
import org.eclipse.dltk.python.parser.ast.expressions.PrintExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonAllImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonDictExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonForListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonFunctionDecorator;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportAsExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonListForExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonSubscriptExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTestListExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonTupleExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;
import org.eclipse.dltk.python.parser.ast.statements.BreakStatement;
import org.eclipse.dltk.python.parser.ast.statements.ContinueStatement;
import org.eclipse.dltk.python.parser.ast.statements.EmptyStatement;
import org.eclipse.dltk.python.parser.ast.statements.ExecStatement;
import org.eclipse.dltk.python.parser.ast.statements.IfStatement;
import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;
import org.eclipse.dltk.python.parser.ast.statements.TryFinallyStatement;

public class python_v3Parser extends Parser {
    public static final String[] tokenNames = new String[] { "<invalid>", "<EOR>", "<DOWN>", "<UP>",
            "INDENT", "DEDENT", "NEWLINE", "DECORATOR_S", "LPAREN", "RPAREN", "NAME", "COLON", "COMMA",
            "STAR", "DOUBLESTAR", "ASSIGN", "SEMI", "PLUSEQUAL", "MINUSEQUAL", "STAREQUAL", "SLASHEQUAL",
            "PERCENTEQUAL", "AMPEREQUAL", "VBAREQUAL", "CIRCUMFLEXEQUAL", "LEFTSHIFTEQUAL",
            "RIGHTSHIFTEQUAL", "DOUBLESTAREQUAL", "DOUBLESLASHEQUAL", "RIGHTSHIFT", "DOT", "LESS", "GREATER",
            "EQUAL", "GREATEREQUAL", "LESSEQUAL", "ALT_NOTEQUAL", "NOTEQUAL", "VBAR", "CIRCUMFLEX", "AMPER",
            "LEFTSHIFT", "PLUS", "MINUS", "SLASH", "PERCENT", "DOUBLESLASH", "TILDE", "LBRACK", "RBRACK",
            "LCURLY", "RCURLY", "BACKQUOTE", "INT", "FLOAT", "COMPLEX", "STRING", "POINTFLOAT",
            "EXPONENTFLOAT", "DIGITS", "FRACTION", "Exponent", "TRIAPOS", "TRIQUOTE", "ESC",
            "CONTINUED_LINE", "WS", "LEADING_WS", "COMMENT", "'def'", "'print'", "'del'", "'pass'",
            "'break'", "'continue'", "'return'", "'yield'", "'raise'", "'import'", "'from'", "'as'",
            "'global'", "'exec'", "'in'", "'assert'", "'if'", "'elif'", "'else'", "'while'", "'for'",
            "'try'", "'except'", "'finally'", "'or'", "'and'", "'not'", "'is'", "'lambda'", "'with'",
            "'class'" };
    public static final int COMMA = 12;
    public static final int MINUS = 43;
    public static final int DEDENT = 5;
    public static final int PERCENT = 45;
    public static final int DECORATOR_S = 7;
    public static final int TRIQUOTE = 63;
    public static final int FLOAT = 54;
    public static final int DOUBLESTAR = 14;
    public static final int SLASHEQUAL = 20;
    public static final int FRACTION = 60;
    public static final int COMPLEX = 55;
    public static final int TILDE = 47;
    public static final int DOUBLESLASHEQUAL = 28;
    public static final int NOTEQUAL = 37;
    public static final int NEWLINE = 6;
    public static final int DOT = 30;
    public static final int CIRCUMFLEX = 39;
    public static final int RCURLY = 51;
    public static final int PLUSEQUAL = 17;
    public static final int RIGHTSHIFTEQUAL = 26;
    public static final int LESS = 31;
    public static final int LCURLY = 50;
    public static final int INT = 53;
    public static final int LEADING_WS = 67;
    public static final int ASSIGN = 15;
    public static final int RPAREN = 9;
    public static final int LPAREN = 8;
    public static final int GREATER = 32;
    public static final int VBAR = 38;
    public static final int PLUS = 42;
    public static final int BACKQUOTE = 52;
    public static final int CONTINUED_LINE = 65;
    public static final int Exponent = 61;
    public static final int DIGITS = 59;
    public static final int SLASH = 44;
    public static final int WS = 66;
    public static final int STRING = 56;
    public static final int COMMENT = 68;
    public static final int POINTFLOAT = 57;
    public static final int TRIAPOS = 62;
    public static final int AMPEREQUAL = 22;
    public static final int ESC = 64;
    public static final int LBRACK = 48;
    public static final int SEMI = 16;
    public static final int EXPONENTFLOAT = 58;
    public static final int EQUAL = 33;
    public static final int LESSEQUAL = 35;
    public static final int RIGHTSHIFT = 29;
    public static final int MINUSEQUAL = 18;
    public static final int PERCENTEQUAL = 21;
    public static final int LEFTSHIFTEQUAL = 25;
    public static final int EOF = -1;
    public static final int CIRCUMFLEXEQUAL = 24;
    public static final int INDENT = 4;
    public static final int RBRACK = 49;
    public static final int ALT_NOTEQUAL = 36;
    public static final int COLON = 11;
    public static final int GREATEREQUAL = 34;
    public static final int AMPER = 40;
    public static final int DOUBLESLASH = 46;
    public static final int STAREQUAL = 19;
    public static final int STAR = 13;
    public static final int VBAREQUAL = 23;
    public static final int NAME = 10;
    public static final int DOUBLESTAREQUAL = 27;
    public static final int LEFTSHIFT = 41;
    
    public python_v3Parser(TokenStream input) {
        super(input);
    }
    
    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }
    
    @Override
    public String getGrammarFileName() {
        return "/Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g";
    }
    
    public DLTKPythonErrorReporter reporter;
    
    public ModuleDeclaration decl;
    
    public int length;
    public DLTKTokenConverter converter;
    
    DLTKToken toDLTK(Token token) {
        return converter.convert(token);
    }
    
    @Override
    public void emitErrorMessage(String msg) {
        reporter.reportMessage(msg);
    }
    
    @Override
    public void reportError(RecognitionException e) {
        if (reporter != null)
            reporter.reportError(e);
    }
    
    public void setStartEndForEmbracedExpr(Expression exp, Token lb, Token rb) {
        exp.setStart(toDLTK(lb).getColumn());
        exp.setEnd(toDLTK(rb).getColumn() + 1);
    }
    
    // $ANTLR start file_input
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:141:1: file_input : ( NEWLINE | s= stmt )* EOF ;
    public final void file_input() {
        ArrayList s = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:141:11: ( ( NEWLINE | s= stmt )* EOF )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:142:6: ( NEWLINE | s= stmt )* EOF
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:142:6: ( NEWLINE | s= stmt )*
                loop1: do {
                    int alt1 = 3;
                    int LA1_0 = input.LA(1);
                    
                    if ((LA1_0 == NEWLINE))
                        alt1 = 1;
                    else if (((LA1_0 >= DECORATOR_S && LA1_0 <= LPAREN) || LA1_0 == NAME
                            || (LA1_0 >= PLUS && LA1_0 <= MINUS) || (LA1_0 >= TILDE && LA1_0 <= LBRACK)
                            || LA1_0 == LCURLY || (LA1_0 >= BACKQUOTE && LA1_0 <= STRING)
                            || (LA1_0 >= 69 && LA1_0 <= 79) || (LA1_0 >= 81 && LA1_0 <= 82)
                            || (LA1_0 >= 84 && LA1_0 <= 85) || (LA1_0 >= 88 && LA1_0 <= 90) || LA1_0 == 95 || (LA1_0 >= 97 && LA1_0 <= 99)))
                        alt1 = 2;
                    
                    switch (alt1) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:143:7: NEWLINE
                    {
                        match(input, NEWLINE, FOLLOW_NEWLINE_in_file_input101);
                        
                    }
                        break;
                    case 2:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:145:7: s= stmt
                    {
                        pushFollow(FOLLOW_stmt_in_file_input126);
                        s = stmt();
                        _fsp--;
                        
                        //statements.addAll( s );     
                        if (s != null) {
                            Iterator i = s.iterator();
                            while (i.hasNext()) {
                                Statement sst = (Statement) i.next();
                                if (sst != null)
                                    decl.addStatement(sst);
                            }
                        }
                        
                    }
                        break;
                    
                    default:
                        break loop1;
                    }
                } while (true);
                
                match(input, EOF, FOLLOW_EOF_in_file_input157);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return;
    }
    
    // $ANTLR end file_input
    
    // $ANTLR start decorator
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:170:1: decorator returns [ Decorator decorator = null ] : dec= DECORATOR_S dottedName= dot_name (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )? NEWLINE ;
    public final Decorator decorator() {
        Decorator decorator = null;
        
        Token dec = null;
        Token lp0 = null;
        Token rp0 = null;
        Token dottedName = null;
        
        PythonCallArgumentsList arguments = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:170:49: (dec= DECORATOR_S dottedName= dot_name (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )? NEWLINE )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:171:2: dec= DECORATOR_S dottedName= dot_name (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )? NEWLINE
            {
                dec = input.LT(1);
                match(input, DECORATOR_S, FOLLOW_DECORATOR_S_in_decorator185);
                pushFollow(FOLLOW_dot_name_in_decorator192);
                dottedName = dot_name();
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:173:2: (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )?
                int alt3 = 2;
                int LA3_0 = input.LA(1);
                
                if ((LA3_0 == LPAREN))
                    alt3 = 1;
                switch (alt3) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:174:4: lp0= LPAREN (arguments= arglist )? rp0= RPAREN
                {
                    lp0 = input.LT(1);
                    match(input, LPAREN, FOLLOW_LPAREN_in_decorator204);
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:175:4: (arguments= arglist )?
                    int alt2 = 2;
                    int LA2_0 = input.LA(1);
                    
                    if ((LA2_0 == LPAREN || LA2_0 == NAME || (LA2_0 >= STAR && LA2_0 <= DOUBLESTAR)
                            || (LA2_0 >= PLUS && LA2_0 <= MINUS) || (LA2_0 >= TILDE && LA2_0 <= LBRACK)
                            || LA2_0 == LCURLY || (LA2_0 >= BACKQUOTE && LA2_0 <= STRING) || LA2_0 == 95 || LA2_0 == 97))
                        alt2 = 1;
                    switch (alt2) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:175:5: arguments= arglist
                    {
                        pushFollow(FOLLOW_arglist_in_decorator214);
                        arguments = arglist();
                        _fsp--;
                        
                    }
                        break;
                    
                    }
                    
                    if (arguments == null)
                        arguments = new PythonCallArgumentsList();
                    rp0 = input.LT(1);
                    match(input, RPAREN, FOLLOW_RPAREN_in_decorator229);
                    
                    decorator = new PythonFunctionDecorator(toDLTK(dottedName), toDLTK(dec), toDLTK(rp0),
                            arguments);
                    
                }
                    break;
                
                }
                
                if (decorator == null)
                    decorator = new PythonFunctionDecorator(toDLTK(dottedName), toDLTK(dec));
                
                match(input, NEWLINE, FOLLOW_NEWLINE_in_decorator245);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return decorator;
    }
    
    // $ANTLR end decorator
    
    // $ANTLR start decoraror_list
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:191:1: decoraror_list returns [List decorators = new ArrayList() ] : (dec= decorator )+ ;
    public final List decoraror_list() {
        List decorators = new ArrayList();
        
        Decorator dec = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:191:60: ( (dec= decorator )+ )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:192:2: (dec= decorator )+
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:192:2: (dec= decorator )+
                int cnt4 = 0;
                loop4: do {
                    int alt4 = 2;
                    int LA4_0 = input.LA(1);
                    
                    if ((LA4_0 == DECORATOR_S))
                        alt4 = 1;
                    
                    switch (alt4) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:193:3: dec= decorator
                    {
                        pushFollow(FOLLOW_decorator_in_decoraror_list267);
                        dec = decorator();
                        _fsp--;
                        
                        if (dec != null)
                            decorators.add(dec);
                        
                    }
                        break;
                    
                    default:
                        if (cnt4 >= 1)
                            break loop4;
                        EarlyExitException eee = new EarlyExitException(4, input);
                        throw eee;
                    }
                    cnt4++;
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return decorators;
    }
    
    // $ANTLR end decoraror_list
    
    // $ANTLR start funcdef
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:203:1: funcdef returns [ MethodDeclaration methodDeclaration = null; ] : (decorators= decoraror_list )? w= 'def' tu= NAME parameters[ params ] e= COLON body= suite ;
    public final MethodDeclaration funcdef() {
        MethodDeclaration methodDeclaration = null;
        ;
        
        Token w = null;
        Token tu = null;
        Token e = null;
        List decorators = null;
        
        Block body = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:203:64: ( (decorators= decoraror_list )? w= 'def' tu= NAME parameters[ params ] e= COLON body= suite )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:204:2: (decorators= decoraror_list )? w= 'def' tu= NAME parameters[ params ] e= COLON body= suite
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:204:2: (decorators= decoraror_list )?
                int alt5 = 2;
                int LA5_0 = input.LA(1);
                
                if ((LA5_0 == DECORATOR_S))
                    alt5 = 1;
                switch (alt5) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:205:3: decorators= decoraror_list
                {
                    pushFollow(FOLLOW_decoraror_list_in_funcdef302);
                    decorators = decoraror_list();
                    _fsp--;
                    
                }
                    break;
                
                }
                
                w = input.LT(1);
                match(input, 69, FOLLOW_69_in_funcdef314);
                tu = input.LT(1);
                match(input, NAME, FOLLOW_NAME_in_funcdef322);
                
                methodDeclaration = new MethodDeclaration(toDLTK(w), toDLTK(tu));
                if (decorators != null)
                    methodDeclaration.setDecorators(decorators);
                
                ArgumentList params = new ArgumentList();
                
                //Block body;
                
                pushFollow(FOLLOW_parameters_in_funcdef328);
                parameters(params);
                _fsp--;
                
                methodDeclaration.acceptArguments(params);
                
                e = input.LT(1);
                match(input, COLON, FOLLOW_COLON_in_funcdef346);
                pushFollow(FOLLOW_suite_in_funcdef360);
                body = suite();
                _fsp--;
                
                methodDeclaration.acceptBody(body);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return methodDeclaration;
    }
    
    // $ANTLR end funcdef
    
    // $ANTLR start parameters
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:232:1: parameters[ ArgumentList params ] : LPAREN ( varargslist[ params ] )? RPAREN ;
    public final void parameters(ArgumentList params) {
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:232:34: ( LPAREN ( varargslist[ params ] )? RPAREN )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:233:2: LPAREN ( varargslist[ params ] )? RPAREN
            {
                match(input, LPAREN, FOLLOW_LPAREN_in_parameters382);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:234:2: ( varargslist[ params ] )?
                int alt6 = 2;
                int LA6_0 = input.LA(1);
                
                if ((LA6_0 == LPAREN || LA6_0 == NAME || (LA6_0 >= STAR && LA6_0 <= DOUBLESTAR)))
                    alt6 = 1;
                switch (alt6) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:234:3: varargslist[ params ]
                {
                    pushFollow(FOLLOW_varargslist_in_parameters387);
                    varargslist(params);
                    _fsp--;
                    
                }
                    break;
                
                }
                
                match(input, RPAREN, FOLLOW_RPAREN_in_parameters394);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return;
    }
    
    // $ANTLR end parameters
    
    // $ANTLR start varargslist
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:239:1: varargslist[ ArgumentList params ] : ( defparameter[params] ( options {greedy=true; } : COMMA defparameter[ params ] )* ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )? | STAR m= NAME ( COMMA DOUBLESTAR m1= NAME )? | DOUBLESTAR m2= NAME );
    public final void varargslist(ArgumentList params) {
        Token tu = null;
        Token t1 = null;
        Token t2 = null;
        Token m = null;
        Token m1 = null;
        Token m2 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:239:35: ( defparameter[params] ( options {greedy=true; } : COMMA defparameter[ params ] )* ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )? | STAR m= NAME ( COMMA DOUBLESTAR m1= NAME )? | DOUBLESTAR m2= NAME )
            int alt12 = 3;
            switch (input.LA(1)) {
            case LPAREN:
            case NAME: {
                alt12 = 1;
            }
                break;
            case STAR: {
                alt12 = 2;
            }
                break;
            case DOUBLESTAR: {
                alt12 = 3;
            }
                break;
            default:
                NoViableAltException nvae = new NoViableAltException(
                        "239:1: varargslist[ ArgumentList params ] : ( defparameter[params] ( options {greedy=true; } : COMMA defparameter[ params ] )* ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )? | STAR m= NAME ( COMMA DOUBLESTAR m1= NAME )? | DOUBLESTAR m2= NAME );",
                        12, 0, input);
                
                throw nvae;
            }
            
            switch (alt12) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:240:2: defparameter[params] ( options {greedy=true; } : COMMA defparameter[ params ] )* ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )?
            {
                pushFollow(FOLLOW_defparameter_in_varargslist405);
                defparameter(params);
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:241:2: ( options {greedy=true; } : COMMA defparameter[ params ] )*
                loop7: do {
                    int alt7 = 2;
                    int LA7_0 = input.LA(1);
                    
                    if ((LA7_0 == COMMA)) {
                        int LA7_1 = input.LA(2);
                        
                        if ((LA7_1 == LPAREN || LA7_1 == NAME))
                            alt7 = 1;
                        
                    }
                    
                    switch (alt7) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:242:28: COMMA defparameter[ params ]
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_varargslist422);
                        pushFollow(FOLLOW_defparameter_in_varargslist427);
                        defparameter(params);
                        _fsp--;
                        
                    }
                        break;
                    
                    default:
                        break loop7;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:245:9: ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )?
                int alt10 = 2;
                int LA10_0 = input.LA(1);
                
                if ((LA10_0 == COMMA))
                    alt10 = 1;
                switch (alt10) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:245:10: COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )?
                {
                    match(input, COMMA, FOLLOW_COMMA_in_varargslist443);
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:246:14: ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )?
                    int alt9 = 3;
                    int LA9_0 = input.LA(1);
                    
                    if ((LA9_0 == STAR))
                        alt9 = 1;
                    else if ((LA9_0 == DOUBLESTAR))
                        alt9 = 2;
                    switch (alt9) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:246:16: STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )?
                    {
                        match(input, STAR, FOLLOW_STAR_in_varargslist460);
                        tu = input.LT(1);
                        match(input, NAME, FOLLOW_NAME_in_varargslist466);
                        
                        params.add(new PythonArgument(toDLTK(tu), PythonArgument.STAR));
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:250:15: ( COMMA DOUBLESTAR t1= NAME )?
                        int alt8 = 2;
                        int LA8_0 = input.LA(1);
                        
                        if ((LA8_0 == COMMA))
                            alt8 = 1;
                        switch (alt8) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:250:16: COMMA DOUBLESTAR t1= NAME
                        {
                            match(input, COMMA, FOLLOW_COMMA_in_varargslist501);
                            match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_varargslist503);
                            t1 = input.LT(1);
                            match(input, NAME, FOLLOW_NAME_in_varargslist509);
                            
                            params.add(new PythonArgument(toDLTK(t1), PythonArgument.DOUBLESTAR));
                            
                        }
                            break;
                        
                        }
                        
                    }
                        break;
                    case 2:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:255:17: DOUBLESTAR t2= NAME
                    {
                        match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_varargslist551);
                        t2 = input.LT(1);
                        match(input, NAME, FOLLOW_NAME_in_varargslist557);
                        
                        params.add(new PythonArgument(toDLTK(t2), PythonArgument.DOUBLESTAR));
                        
                    }
                        break;
                    
                    }
                    
                }
                    break;
                
                }
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:261:4: STAR m= NAME ( COMMA DOUBLESTAR m1= NAME )?
            {
                match(input, STAR, FOLLOW_STAR_in_varargslist595);
                m = input.LT(1);
                match(input, NAME, FOLLOW_NAME_in_varargslist601);
                
                params.add(new PythonArgument(toDLTK(m), PythonArgument.STAR));
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:265:2: ( COMMA DOUBLESTAR m1= NAME )?
                int alt11 = 2;
                int LA11_0 = input.LA(1);
                
                if ((LA11_0 == COMMA))
                    alt11 = 1;
                switch (alt11) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:265:3: COMMA DOUBLESTAR m1= NAME
                {
                    match(input, COMMA, FOLLOW_COMMA_in_varargslist610);
                    match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_varargslist615);
                    m1 = input.LT(1);
                    match(input, NAME, FOLLOW_NAME_in_varargslist621);
                    
                    params.add(new PythonArgument(toDLTK(m1), PythonArgument.DOUBLESTAR));
                    
                }
                    break;
                
                }
                
            }
                break;
            case 3:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:271:4: DOUBLESTAR m2= NAME
            {
                match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_varargslist635);
                m2 = input.LT(1);
                match(input, NAME, FOLLOW_NAME_in_varargslist641);
                
                params.add(new PythonArgument(toDLTK(m2), PythonArgument.DOUBLESTAR));
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return;
    }
    
    // $ANTLR end varargslist
    
    // $ANTLR start defparameter
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:278:1: defparameter[ ArgumentList params ] : lastParam= fpdef[ params ] ( ASSIGN initExpr= test )? ;
    public final void defparameter(ArgumentList params) {
        PythonArgument lastParam = null;
        
        Expression initExpr = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:278:36: (lastParam= fpdef[ params ] ( ASSIGN initExpr= test )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:279:2: lastParam= fpdef[ params ] ( ASSIGN initExpr= test )?
            {
                pushFollow(FOLLOW_fpdef_in_defparameter660);
                lastParam = fpdef(params);
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:280:2: ( ASSIGN initExpr= test )?
                int alt13 = 2;
                int LA13_0 = input.LA(1);
                
                if ((LA13_0 == ASSIGN))
                    alt13 = 1;
                switch (alt13) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:280:3: ASSIGN initExpr= test
                {
                    match(input, ASSIGN, FOLLOW_ASSIGN_in_defparameter665);
                    pushFollow(FOLLOW_test_in_defparameter671);
                    initExpr = test();
                    _fsp--;
                    
                    if (lastParam != null)
                        lastParam.assign(initExpr);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return;
    }
    
    // $ANTLR end defparameter
    
    // $ANTLR start fpdef
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:291:1: fpdef[ ArgumentList params ] returns [ PythonArgument argument = null ] : (tu= NAME | LPAREN fplist[params] RPAREN );
    public final PythonArgument fpdef(ArgumentList params) {
        PythonArgument argument = null;
        
        Token tu = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:291:73: (tu= NAME | LPAREN fplist[params] RPAREN )
            int alt14 = 2;
            int LA14_0 = input.LA(1);
            
            if ((LA14_0 == NAME))
                alt14 = 1;
            else if ((LA14_0 == LPAREN))
                alt14 = 2;
            else {
                NoViableAltException nvae = new NoViableAltException(
                        "291:1: fpdef[ ArgumentList params ] returns [ PythonArgument argument = null ] : (tu= NAME | LPAREN fplist[params] RPAREN );",
                        14, 0, input);
                
                throw nvae;
            }
            switch (alt14) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:292:6: tu= NAME
            {
                tu = input.LT(1);
                match(input, NAME, FOLLOW_NAME_in_fpdef703);
                
                argument = new PythonArgument(toDLTK(tu));
                params.add(argument);
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:297:3: LPAREN fplist[params] RPAREN
            {
                match(input, LPAREN, FOLLOW_LPAREN_in_fpdef714);
                pushFollow(FOLLOW_fplist_in_fpdef716);
                fplist(params);
                _fsp--;
                
                match(input, RPAREN, FOLLOW_RPAREN_in_fpdef719);
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return argument;
    }
    
    // $ANTLR end fpdef
    
    // $ANTLR start fplist
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:301:1: fplist[ArgumentList params ] : fpdef[ params ] ( options {greedy=true; } : COMMA fpdef[ params ] )* ( COMMA )? ;
    public final void fplist(ArgumentList params) {
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:301:30: ( fpdef[ params ] ( options {greedy=true; } : COMMA fpdef[ params ] )* ( COMMA )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:302:2: fpdef[ params ] ( options {greedy=true; } : COMMA fpdef[ params ] )* ( COMMA )?
            {
                pushFollow(FOLLOW_fpdef_in_fplist734);
                fpdef(params);
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:303:2: ( options {greedy=true; } : COMMA fpdef[ params ] )*
                loop15: do {
                    int alt15 = 2;
                    int LA15_0 = input.LA(1);
                    
                    if ((LA15_0 == COMMA)) {
                        int LA15_1 = input.LA(2);
                        
                        if ((LA15_1 == LPAREN || LA15_1 == NAME))
                            alt15 = 1;
                        
                    }
                    
                    switch (alt15) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:304:3: COMMA fpdef[ params ]
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_fplist752);
                        pushFollow(FOLLOW_fpdef_in_fplist754);
                        fpdef(params);
                        _fsp--;
                        
                    }
                        break;
                    
                    default:
                        break loop15;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:306:2: ( COMMA )?
                int alt16 = 2;
                int LA16_0 = input.LA(1);
                
                if ((LA16_0 == COMMA))
                    alt16 = 1;
                switch (alt16) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:306:3: COMMA
                {
                    match(input, COMMA, FOLLOW_COMMA_in_fplist763);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return;
    }
    
    // $ANTLR end fplist
    
    // $ANTLR start simple_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:314:1: simple_stmt[ List stmts ] : small_stmt[ stmts ] ( options {greedy=true; } : SEMI small_stmt[ stmts ] )* ( SEMI )? NEWLINE ;
    public final void simple_stmt(List stmts) {
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:314:27: ( small_stmt[ stmts ] ( options {greedy=true; } : SEMI small_stmt[ stmts ] )* ( SEMI )? NEWLINE )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:315:2: small_stmt[ stmts ] ( options {greedy=true; } : SEMI small_stmt[ stmts ] )* ( SEMI )? NEWLINE
            {
                pushFollow(FOLLOW_small_stmt_in_simple_stmt781);
                small_stmt(stmts);
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:316:2: ( options {greedy=true; } : SEMI small_stmt[ stmts ] )*
                loop17: do {
                    int alt17 = 2;
                    int LA17_0 = input.LA(1);
                    
                    if ((LA17_0 == SEMI)) {
                        int LA17_1 = input.LA(2);
                        
                        if ((LA17_1 == LPAREN || LA17_1 == NAME || (LA17_1 >= PLUS && LA17_1 <= MINUS)
                                || (LA17_1 >= TILDE && LA17_1 <= LBRACK) || LA17_1 == LCURLY
                                || (LA17_1 >= BACKQUOTE && LA17_1 <= STRING)
                                || (LA17_1 >= 70 && LA17_1 <= 79) || (LA17_1 >= 81 && LA17_1 <= 82)
                                || LA17_1 == 84 || LA17_1 == 95 || LA17_1 == 97))
                            alt17 = 1;
                        
                    }
                    
                    switch (alt17) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:317:3: SEMI small_stmt[ stmts ]
                    {
                        match(input, SEMI, FOLLOW_SEMI_in_simple_stmt800);
                        pushFollow(FOLLOW_small_stmt_in_simple_stmt802);
                        small_stmt(stmts);
                        _fsp--;
                        
                    }
                        break;
                    
                    default:
                        break loop17;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:318:6: ( SEMI )?
                int alt18 = 2;
                int LA18_0 = input.LA(1);
                
                if ((LA18_0 == SEMI))
                    alt18 = 1;
                switch (alt18) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:318:7: SEMI
                {
                    match(input, SEMI, FOLLOW_SEMI_in_simple_stmt812);
                    
                }
                    break;
                
                }
                
                match(input, NEWLINE, FOLLOW_NEWLINE_in_simple_stmt817);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return;
    }
    
    // $ANTLR end simple_stmt
    
    // $ANTLR start stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:320:4: stmt returns [ ArrayList statements = new ArrayList( ) ] : ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt ) ;
    public final ArrayList stmt() {
        ArrayList statements = new ArrayList();
        
        Statement compoundStatement = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:320:60: ( ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:321:2: ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:321:2: ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt )
                int alt19 = 2;
                int LA19_0 = input.LA(1);
                
                if ((LA19_0 == LPAREN || LA19_0 == NAME || (LA19_0 >= PLUS && LA19_0 <= MINUS)
                        || (LA19_0 >= TILDE && LA19_0 <= LBRACK) || LA19_0 == LCURLY
                        || (LA19_0 >= BACKQUOTE && LA19_0 <= STRING) || (LA19_0 >= 70 && LA19_0 <= 79)
                        || (LA19_0 >= 81 && LA19_0 <= 82) || LA19_0 == 84 || LA19_0 == 95 || LA19_0 == 97))
                    alt19 = 1;
                else if ((LA19_0 == DECORATOR_S || LA19_0 == 69 || LA19_0 == 85
                        || (LA19_0 >= 88 && LA19_0 <= 90) || (LA19_0 >= 98 && LA19_0 <= 99)))
                    alt19 = 2;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "321:2: ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt )",
                            19, 0, input);
                    
                    throw nvae;
                }
                switch (alt19) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:322:3: simple_stmt[ simpleStatements ]
                {
                    
                    List simpleStatements = new ArrayList();
                    
                    pushFollow(FOLLOW_simple_stmt_in_stmt838);
                    simple_stmt(simpleStatements);
                    _fsp--;
                    
                    statements.addAll(simpleStatements);
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:327:3: compoundStatement= compound_stmt
                {
                    pushFollow(FOLLOW_compound_stmt_in_stmt858);
                    compoundStatement = compound_stmt();
                    _fsp--;
                    
                    statements.add(compoundStatement);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statements;
    }
    
    // $ANTLR end stmt
    
    // $ANTLR start small_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:334:1: small_stmt[ List stmts ] returns [ Statement rstatement = null ] : (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt ) ;
    public final Statement small_stmt(List stmts) {
        Statement rstatement = null;
        
        Expression statement1 = null;
        
        Statement statement2 = null;
        
        Statement statement3 = null;
        
        Statement statement4 = null;
        
        Statement statement5 = null;
        
        Statement statement6 = null;
        
        Statement statement7 = null;
        
        Statement statement8 = null;
        
        PythonAssertStatement statement9 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:334:66: ( (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:335:2: (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:335:2: (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt )
                int alt20 = 9;
                switch (input.LA(1)) {
                case LPAREN:
                case NAME:
                case PLUS:
                case MINUS:
                case TILDE:
                case LBRACK:
                case LCURLY:
                case BACKQUOTE:
                case INT:
                case FLOAT:
                case COMPLEX:
                case STRING:
                case 95:
                case 97: {
                    alt20 = 1;
                }
                    break;
                case 70: {
                    alt20 = 2;
                }
                    break;
                case 71: {
                    alt20 = 3;
                }
                    break;
                case 72: {
                    alt20 = 4;
                }
                    break;
                case 73:
                case 74:
                case 75:
                case 76:
                case 77: {
                    alt20 = 5;
                }
                    break;
                case 78:
                case 79: {
                    alt20 = 6;
                }
                    break;
                case 81: {
                    alt20 = 7;
                }
                    break;
                case 82: {
                    alt20 = 8;
                }
                    break;
                case 84: {
                    alt20 = 9;
                }
                    break;
                default:
                    NoViableAltException nvae = new NoViableAltException(
                            "335:2: (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt )",
                            20, 0, input);
                    
                    throw nvae;
                }
                
                switch (alt20) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:336:4: statement1= expr_stmt
                {
                    pushFollow(FOLLOW_expr_stmt_in_small_stmt892);
                    statement1 = expr_stmt();
                    _fsp--;
                    
                    rstatement = statement1;
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:337:4: statement2= print_stmt
                {
                    pushFollow(FOLLOW_print_stmt_in_small_stmt903);
                    statement2 = print_stmt();
                    _fsp--;
                    
                    rstatement = statement2;
                    
                }
                    break;
                case 3:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:338:4: statement3= del_stmt
                {
                    pushFollow(FOLLOW_del_stmt_in_small_stmt913);
                    statement3 = del_stmt();
                    _fsp--;
                    
                    rstatement = statement3;
                    
                }
                    break;
                case 4:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:339:4: statement4= pass_stmt
                {
                    pushFollow(FOLLOW_pass_stmt_in_small_stmt923);
                    statement4 = pass_stmt();
                    _fsp--;
                    
                    rstatement = statement4;
                    
                }
                    break;
                case 5:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:340:4: statement5= flow_stmt
                {
                    pushFollow(FOLLOW_flow_stmt_in_small_stmt933);
                    statement5 = flow_stmt();
                    _fsp--;
                    
                    rstatement = statement5;
                    
                }
                    break;
                case 6:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:341:4: statement6= import_stmt
                {
                    pushFollow(FOLLOW_import_stmt_in_small_stmt943);
                    statement6 = import_stmt();
                    _fsp--;
                    
                    rstatement = statement6;
                    
                }
                    break;
                case 7:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:342:4: statement7= global_stmt
                {
                    pushFollow(FOLLOW_global_stmt_in_small_stmt953);
                    statement7 = global_stmt();
                    _fsp--;
                    
                    rstatement = statement7;
                    
                }
                    break;
                case 8:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:343:4: statement8= exec_stmt
                {
                    pushFollow(FOLLOW_exec_stmt_in_small_stmt963);
                    statement8 = exec_stmt();
                    _fsp--;
                    
                    rstatement = statement8;
                    
                }
                    break;
                case 9:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:344:4: statement9= assert_stmt
                {
                    pushFollow(FOLLOW_assert_stmt_in_small_stmt973);
                    statement9 = assert_stmt();
                    _fsp--;
                    
                    rstatement = statement9;
                    
                }
                    break;
                
                }
                
                if (rstatement != null)
                    stmts.add(rstatement);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return rstatement;
    }
    
    // $ANTLR end small_stmt
    
    // $ANTLR start expr_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:352:1: expr_stmt returns [ Expression exp = null ] : exp0= testlist (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )? ;
    public final Expression expr_stmt() {
        Expression exp = null;
        
        Token a = null;
        Expression exp0 = null;
        
        int type = 0;
        
        Expression right = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:352:45: (exp0= testlist (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:353:2: exp0= testlist (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )?
            {
                pushFollow(FOLLOW_testlist_in_expr_stmt1001);
                exp0 = testlist();
                _fsp--;
                
                exp = exp0;
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:354:2: (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )?
                int alt22 = 3;
                int LA22_0 = input.LA(1);
                
                if (((LA22_0 >= PLUSEQUAL && LA22_0 <= DOUBLESLASHEQUAL)))
                    alt22 = 1;
                else if ((LA22_0 == ASSIGN))
                    alt22 = 2;
                switch (alt22) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:355:3: type= augassign right= testlist
                {
                    pushFollow(FOLLOW_augassign_in_expr_stmt1015);
                    type = augassign();
                    _fsp--;
                    
                    pushFollow(FOLLOW_testlist_in_expr_stmt1024);
                    right = testlist();
                    _fsp--;
                    
                    if (type != 0) {
                        NotStrictAssignment e = new NotStrictAssignment(exp, type, right);
                        exp = e;
                    } else
                        exp = new Assignment(exp, right);
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:367:3: (a= ASSIGN right= testlist )+
                {
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:367:3: (a= ASSIGN right= testlist )+
                    int cnt21 = 0;
                    loop21: do {
                        int alt21 = 2;
                        int LA21_0 = input.LA(1);
                        
                        if ((LA21_0 == ASSIGN))
                            alt21 = 1;
                        
                        switch (alt21) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:368:4: a= ASSIGN right= testlist
                        {
                            a = input.LT(1);
                            match(input, ASSIGN, FOLLOW_ASSIGN_in_expr_stmt1046);
                            pushFollow(FOLLOW_testlist_in_expr_stmt1055);
                            right = testlist();
                            _fsp--;
                            
                            if (type != 0) {
                                NotStrictAssignment e = new NotStrictAssignment(exp, type, right);
                                exp = e;
                            } else
                                exp = new Assignment(exp, right);
                            
                        }
                            break;
                        
                        default:
                            if (cnt21 >= 1)
                                break loop21;
                            EarlyExitException eee = new EarlyExitException(21, input);
                            throw eee;
                        }
                        cnt21++;
                    } while (true);
                    
                }
                    break;
                
                }
                
                if (exp == null)
                    exp = new EmptyExpression();
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end expr_stmt
    
    // $ANTLR start augassign
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:387:1: augassign returns [ int type = 0 ] : ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL );
    public final int augassign() {
        int type = 0;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:387:35: ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL )
            int alt23 = 12;
            switch (input.LA(1)) {
            case PLUSEQUAL: {
                alt23 = 1;
            }
                break;
            case MINUSEQUAL: {
                alt23 = 2;
            }
                break;
            case STAREQUAL: {
                alt23 = 3;
            }
                break;
            case SLASHEQUAL: {
                alt23 = 4;
            }
                break;
            case PERCENTEQUAL: {
                alt23 = 5;
            }
                break;
            case AMPEREQUAL: {
                alt23 = 6;
            }
                break;
            case VBAREQUAL: {
                alt23 = 7;
            }
                break;
            case CIRCUMFLEXEQUAL: {
                alt23 = 8;
            }
                break;
            case LEFTSHIFTEQUAL: {
                alt23 = 9;
            }
                break;
            case RIGHTSHIFTEQUAL: {
                alt23 = 10;
            }
                break;
            case DOUBLESTAREQUAL: {
                alt23 = 11;
            }
                break;
            case DOUBLESLASHEQUAL: {
                alt23 = 12;
            }
                break;
            default:
                NoViableAltException nvae = new NoViableAltException(
                        "387:1: augassign returns [ int type = 0 ] : ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL );",
                        23, 0, input);
                
                throw nvae;
            }
            
            switch (alt23) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:388:2: PLUSEQUAL
            {
                match(input, PLUSEQUAL, FOLLOW_PLUSEQUAL_in_augassign1088);
                
                type = Expression.E_PLUS_ASSIGN;
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:392:4: MINUSEQUAL
            {
                match(input, MINUSEQUAL, FOLLOW_MINUSEQUAL_in_augassign1097);
                
                type = Expression.E_MINUS_ASSIGN;
                
            }
                break;
            case 3:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:396:4: STAREQUAL
            {
                match(input, STAREQUAL, FOLLOW_STAREQUAL_in_augassign1107);
                
                type = Expression.E_MULT_ASSIGN;
                
            }
                break;
            case 4:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:400:4: SLASHEQUAL
            {
                match(input, SLASHEQUAL, FOLLOW_SLASHEQUAL_in_augassign1116);
                
                type = Expression.E_DIV_ASSIGN;
                
            }
                break;
            case 5:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:404:4: PERCENTEQUAL
            {
                match(input, PERCENTEQUAL, FOLLOW_PERCENTEQUAL_in_augassign1126);
                
                type = Expression.E_MOD_ASSIGN;
                
            }
                break;
            case 6:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:408:4: AMPEREQUAL
            {
                match(input, AMPEREQUAL, FOLLOW_AMPEREQUAL_in_augassign1135);
                
                type = Expression.E_BAND_ASSIGN;
                
            }
                break;
            case 7:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:412:4: VBAREQUAL
            {
                match(input, VBAREQUAL, FOLLOW_VBAREQUAL_in_augassign1144);
                
                type = Expression.E_BOR_ASSIGN;
                
            }
                break;
            case 8:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:416:4: CIRCUMFLEXEQUAL
            {
                match(input, CIRCUMFLEXEQUAL, FOLLOW_CIRCUMFLEXEQUAL_in_augassign1153);
                
                type = Expression.E_BXOR_ASSIGN;
                
            }
                break;
            case 9:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:420:4: LEFTSHIFTEQUAL
            {
                match(input, LEFTSHIFTEQUAL, FOLLOW_LEFTSHIFTEQUAL_in_augassign1162);
                
                type = Expression.E_SL_ASSIGN;
                
            }
                break;
            case 10:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:424:4: RIGHTSHIFTEQUAL
            {
                match(input, RIGHTSHIFTEQUAL, FOLLOW_RIGHTSHIFTEQUAL_in_augassign1171);
                
                type = Expression.E_SR_ASSIGN;
                
            }
                break;
            case 11:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:428:4: DOUBLESTAREQUAL
            {
                match(input, DOUBLESTAREQUAL, FOLLOW_DOUBLESTAREQUAL_in_augassign1180);
                
                type = Expression.E_DOUBLESTAR_ASSIGN;
                
            }
                break;
            case 12:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:432:4: DOUBLESLASHEQUAL
            {
                match(input, DOUBLESLASHEQUAL, FOLLOW_DOUBLESLASHEQUAL_in_augassign1189);
                
                type = Expression.E_DOUBLEDIV_ASSIGN;
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return type;
    }
    
    // $ANTLR end augassign
    
    // $ANTLR start print_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:438:1: print_stmt returns [ Statement statement = null ] : tu= 'print' (ex= testlist | RIGHTSHIFT ex= testlist )? ;
    public final Statement print_stmt() {
        Statement statement = null;
        
        Token tu = null;
        Expression ex = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:438:50: (tu= 'print' (ex= testlist | RIGHTSHIFT ex= testlist )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:440:9: tu= 'print' (ex= testlist | RIGHTSHIFT ex= testlist )?
            {
                tu = input.LT(1);
                match(input, 70, FOLLOW_70_in_print_stmt1223);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:441:2: (ex= testlist | RIGHTSHIFT ex= testlist )?
                int alt24 = 3;
                int LA24_0 = input.LA(1);
                
                if ((LA24_0 == LPAREN || LA24_0 == NAME || (LA24_0 >= PLUS && LA24_0 <= MINUS)
                        || (LA24_0 >= TILDE && LA24_0 <= LBRACK) || LA24_0 == LCURLY
                        || (LA24_0 >= BACKQUOTE && LA24_0 <= STRING) || LA24_0 == 95 || LA24_0 == 97))
                    alt24 = 1;
                else if ((LA24_0 == RIGHTSHIFT))
                    alt24 = 2;
                switch (alt24) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:442:3: ex= testlist
                {
                    pushFollow(FOLLOW_testlist_in_print_stmt1234);
                    ex = testlist();
                    _fsp--;
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:443:12: RIGHTSHIFT ex= testlist
                {
                    match(input, RIGHTSHIFT, FOLLOW_RIGHTSHIFT_in_print_stmt1247);
                    pushFollow(FOLLOW_testlist_in_print_stmt1263);
                    ex = testlist();
                    _fsp--;
                    
                }
                    break;
                
                }
                
                statement = new PrintExpression(toDLTK(tu), ex);
                if (ex != null)
                    statement.setEnd(ex.sourceEnd());
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end print_stmt
    
    // $ANTLR start del_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:455:1: del_stmt returns [ Statement statement = null ] : sa= 'del' tu= exprlist ;
    public final Statement del_stmt() {
        Statement statement = null;
        
        Token sa = null;
        PythonTestListExpression tu = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:455:48: (sa= 'del' tu= exprlist )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:456:2: sa= 'del' tu= exprlist
            {
                sa = input.LT(1);
                match(input, 71, FOLLOW_71_in_del_stmt1294);
                pushFollow(FOLLOW_exprlist_in_del_stmt1302);
                tu = exprlist();
                _fsp--;
                
                statement = new PythonDelStatement(toDLTK(sa), tu);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end del_stmt
    
    // $ANTLR start pass_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:464:1: pass_stmt returns [ Statement statement = null] : tu= 'pass' ;
    public final Statement pass_stmt() {
        Statement statement = null;
        
        Token tu = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:464:48: (tu= 'pass' )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:465:2: tu= 'pass'
            {
                tu = input.LT(1);
                match(input, 72, FOLLOW_72_in_pass_stmt1325);
                
                statement = new EmptyStatement(toDLTK(tu));
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end pass_stmt
    
    // $ANTLR start flow_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:472:1: flow_stmt returns [ Statement statement = null ] : (statement0= break_stmt | statement1= continue_stmt | statement2= return_stmt | statement3= raise_stmt | statement4= yield_stmt );
    public final Statement flow_stmt() {
        Statement statement = null;
        
        Statement statement0 = null;
        
        Statement statement1 = null;
        
        Statement statement2 = null;
        
        PythonRaiseStatement statement3 = null;
        
        PythonYieldStatement statement4 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:472:49: (statement0= break_stmt | statement1= continue_stmt | statement2= return_stmt | statement3= raise_stmt | statement4= yield_stmt )
            int alt25 = 5;
            switch (input.LA(1)) {
            case 73: {
                alt25 = 1;
            }
                break;
            case 74: {
                alt25 = 2;
            }
                break;
            case 75: {
                alt25 = 3;
            }
                break;
            case 77: {
                alt25 = 4;
            }
                break;
            case 76: {
                alt25 = 5;
            }
                break;
            default:
                NoViableAltException nvae = new NoViableAltException(
                        "472:1: flow_stmt returns [ Statement statement = null ] : (statement0= break_stmt | statement1= continue_stmt | statement2= return_stmt | statement3= raise_stmt | statement4= yield_stmt );",
                        25, 0, input);
                
                throw nvae;
            }
            
            switch (alt25) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:473:4: statement0= break_stmt
            {
                pushFollow(FOLLOW_break_stmt_in_flow_stmt1351);
                statement0 = break_stmt();
                _fsp--;
                
                statement = statement0;
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:474:4: statement1= continue_stmt
            {
                pushFollow(FOLLOW_continue_stmt_in_flow_stmt1362);
                statement1 = continue_stmt();
                _fsp--;
                
                statement = statement1;
                
            }
                break;
            case 3:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:475:4: statement2= return_stmt
            {
                pushFollow(FOLLOW_return_stmt_in_flow_stmt1372);
                statement2 = return_stmt();
                _fsp--;
                
                statement = statement2;
                
            }
                break;
            case 4:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:476:4: statement3= raise_stmt
            {
                pushFollow(FOLLOW_raise_stmt_in_flow_stmt1382);
                statement3 = raise_stmt();
                _fsp--;
                
                statement = statement3;
                
            }
                break;
            case 5:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:477:4: statement4= yield_stmt
            {
                pushFollow(FOLLOW_yield_stmt_in_flow_stmt1392);
                statement4 = yield_stmt();
                _fsp--;
                
                statement = statement4;
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end flow_stmt
    
    // $ANTLR start break_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:480:1: break_stmt returns [ Statement statement = null ] : ta= 'break' ;
    public final Statement break_stmt() {
        Statement statement = null;
        
        Token ta = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:480:50: (ta= 'break' )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:481:2: ta= 'break'
            {
                ta = input.LT(1);
                match(input, 73, FOLLOW_73_in_break_stmt1413);
                
                statement = new BreakStatement(toDLTK(ta), null, toDLTK(ta));
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end break_stmt
    
    // $ANTLR start continue_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:487:1: continue_stmt returns [ Statement statement = null ] : ta= 'continue' ;
    public final Statement continue_stmt() {
        Statement statement = null;
        
        Token ta = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:487:53: (ta= 'continue' )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:488:2: ta= 'continue'
            {
                ta = input.LT(1);
                match(input, 74, FOLLOW_74_in_continue_stmt1437);
                
                statement = new ContinueStatement(toDLTK(ta), null, toDLTK(ta));
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end continue_stmt
    
    // $ANTLR start return_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:494:1: return_stmt returns [ Statement statement = null ] : ra= 'return' (tu= testlist )? ;
    public final Statement return_stmt() {
        Statement statement = null;
        
        Token ra = null;
        Expression tu = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:494:51: (ra= 'return' (tu= testlist )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:495:2: ra= 'return' (tu= testlist )?
            {
                ra = input.LT(1);
                match(input, 75, FOLLOW_75_in_return_stmt1462);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:496:3: (tu= testlist )?
                int alt26 = 2;
                int LA26_0 = input.LA(1);
                
                if ((LA26_0 == LPAREN || LA26_0 == NAME || (LA26_0 >= PLUS && LA26_0 <= MINUS)
                        || (LA26_0 >= TILDE && LA26_0 <= LBRACK) || LA26_0 == LCURLY
                        || (LA26_0 >= BACKQUOTE && LA26_0 <= STRING) || LA26_0 == 95 || LA26_0 == 97))
                    alt26 = 1;
                switch (alt26) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:496:5: tu= testlist
                {
                    pushFollow(FOLLOW_testlist_in_return_stmt1473);
                    tu = testlist();
                    _fsp--;
                    
                }
                    break;
                
                }
                
                DLTKToken ret = toDLTK(ra);
                int end = ret.getColumn() + ret.getText().length();
                if (tu != null)
                    end = tu.sourceEnd();
                statement = new ReturnStatement(toDLTK(ra), tu, end);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end return_stmt
    
    // $ANTLR start yield_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:508:1: yield_stmt returns [ PythonYieldStatement statement = null ] : tu= 'yield' r= testlist ;
    public final PythonYieldStatement yield_stmt() {
        PythonYieldStatement statement = null;
        
        Token tu = null;
        Expression r = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:508:61: (tu= 'yield' r= testlist )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:509:2: tu= 'yield' r= testlist
            {
                tu = input.LT(1);
                match(input, 76, FOLLOW_76_in_yield_stmt1501);
                pushFollow(FOLLOW_testlist_in_yield_stmt1508);
                r = testlist();
                _fsp--;
                
                statement = new PythonYieldStatement(toDLTK(tu), r);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end yield_stmt
    
    // $ANTLR start raise_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:516:1: raise_stmt returns [ PythonRaiseStatement statement = null ] : tu= 'raise' (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )? ;
    public final PythonRaiseStatement raise_stmt() {
        PythonRaiseStatement statement = null;
        
        Token tu = null;
        Expression e1 = null;
        
        Expression e2 = null;
        
        Expression e3 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:516:61: (tu= 'raise' (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:517:2: tu= 'raise' (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )?
            {
                tu = input.LT(1);
                match(input, 77, FOLLOW_77_in_raise_stmt1530);
                
                statement = new PythonRaiseStatement(toDLTK(tu));
                int end = -1;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:522:2: (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )?
                int alt29 = 2;
                int LA29_0 = input.LA(1);
                
                if ((LA29_0 == LPAREN || LA29_0 == NAME || (LA29_0 >= PLUS && LA29_0 <= MINUS)
                        || (LA29_0 >= TILDE && LA29_0 <= LBRACK) || LA29_0 == LCURLY
                        || (LA29_0 >= BACKQUOTE && LA29_0 <= STRING) || LA29_0 == 95 || LA29_0 == 97))
                    alt29 = 1;
                switch (alt29) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:523:3: e1= test ( COMMA e2= test ( COMMA e3= test )? )?
                {
                    pushFollow(FOLLOW_test_in_raise_stmt1544);
                    e1 = test();
                    _fsp--;
                    
                    statement.acceptExpression1(e1);
                    if (e1 != null && e1.sourceEnd() > end)
                        end = e1.sourceEnd();
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:530:3: ( COMMA e2= test ( COMMA e3= test )? )?
                    int alt28 = 2;
                    int LA28_0 = input.LA(1);
                    
                    if ((LA28_0 == COMMA))
                        alt28 = 1;
                    switch (alt28) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:530:5: COMMA e2= test ( COMMA e3= test )?
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_raise_stmt1555);
                        pushFollow(FOLLOW_test_in_raise_stmt1564);
                        e2 = test();
                        _fsp--;
                        
                        statement.acceptExpression2(e2);
                        if (e2 != null && e2.sourceEnd() > end)
                            end = e2.sourceEnd();
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:538:4: ( COMMA e3= test )?
                        int alt27 = 2;
                        int LA27_0 = input.LA(1);
                        
                        if ((LA27_0 == COMMA))
                            alt27 = 1;
                        switch (alt27) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:538:6: COMMA e3= test
                        {
                            match(input, COMMA, FOLLOW_COMMA_in_raise_stmt1577);
                            pushFollow(FOLLOW_test_in_raise_stmt1587);
                            e3 = test();
                            _fsp--;
                            
                            statement.acceptExpression3(e3);
                            if (e3 != null && e3.sourceEnd() > end)
                                end = e3.sourceEnd();
                            
                        }
                            break;
                        
                        }
                        
                    }
                        break;
                    
                    }
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end raise_stmt
    
    // $ANTLR start import_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:552:1: import_stmt returns [ Statement statement = null ] : ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) ) ;
    public final Statement import_stmt() {
        Statement statement = null;
        
        Token tu = null;
        Token r = null;
        Expression expr0 = null;
        
        Token moduleName = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:552:52: ( ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:553:6: ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) )
            {
                
                Expression impExpr;
                String impName;
                String impName2;
                //Token moduleName;		
                
                PythonTestListExpression importNames = new PythonTestListExpression();
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:562:6: ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) )
                int alt33 = 2;
                int LA33_0 = input.LA(1);
                
                if ((LA33_0 == 78))
                    alt33 = 1;
                else if ((LA33_0 == 79))
                    alt33 = 2;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "562:6: ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) )",
                            33, 0, input);
                    
                    throw nvae;
                }
                switch (alt33) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:563:7: (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* )
                {
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:563:7: (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* )
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:564:8: tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )*
                    {
                        tu = input.LT(1);
                        match(input, 78, FOLLOW_78_in_import_stmt1704);
                        
                        statement = new PythonImportStatement(toDLTK(tu), importNames);
                        //Expression expr0 = null;
                        
                        pushFollow(FOLLOW_module_imp_in_import_stmt1726);
                        expr0 = module_imp();
                        _fsp--;
                        
                        importNames.setStart(expr0.sourceStart());
                        importNames.setEnd(expr0.sourceEnd());
                        importNames.addExpression(expr0);
                        if (expr0.sourceEnd() > statement.sourceEnd())
                            statement.setEnd(expr0.sourceEnd());
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:579:8: ( COMMA expr0= module_imp )*
                        loop30: do {
                            int alt30 = 2;
                            int LA30_0 = input.LA(1);
                            
                            if ((LA30_0 == COMMA))
                                alt30 = 1;
                            
                            switch (alt30) {
                            case 1:
                                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:579:10: COMMA expr0= module_imp
                            {
                                match(input, COMMA, FOLLOW_COMMA_in_import_stmt1755);
                                pushFollow(FOLLOW_module_imp_in_import_stmt1769);
                                expr0 = module_imp();
                                _fsp--;
                                
                                importNames.addExpression(expr0);
                                importNames.setEnd(expr0.sourceEnd());
                                if (expr0.sourceEnd() > statement.sourceEnd())
                                    statement.setEnd(expr0.sourceEnd());
                                
                            }
                                break;
                            
                            default:
                                break loop30;
                            }
                        } while (true);
                        
                    }
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:591:3: r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) )
                {
                    r = input.LT(1);
                    match(input, 79, FOLLOW_79_in_import_stmt1805);
                    pushFollow(FOLLOW_dot_name_in_import_stmt1813);
                    moduleName = dot_name();
                    _fsp--;
                    
                    match(input, 78, FOLLOW_78_in_import_stmt1820);
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:595:3: ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) )
                    int alt32 = 2;
                    int LA32_0 = input.LA(1);
                    
                    if ((LA32_0 == NAME))
                        alt32 = 1;
                    else if ((LA32_0 == STAR))
                        alt32 = 2;
                    else {
                        NoViableAltException nvae = new NoViableAltException(
                                "595:3: ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) )",
                                32, 0, input);
                        
                        throw nvae;
                    }
                    switch (alt32) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:596:11: (expr0= module_imp ( COMMA expr0= module_imp )* )
                    {
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:596:11: (expr0= module_imp ( COMMA expr0= module_imp )* )
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:597:6: expr0= module_imp ( COMMA expr0= module_imp )*
                        {
                            
                            //moduleName.setColumn(moduleName.getColumn()-1);
                            statement = new PythonImportFromStatement(toDLTK(r), new VariableReference(
                                    toDLTK(moduleName)), importNames);
                            //Expression expr0 = null;
                            
                            pushFollow(FOLLOW_module_imp_in_import_stmt1860);
                            expr0 = module_imp();
                            _fsp--;
                            
                            importNames.setStart(expr0.sourceStart());
                            importNames.setEnd(expr0.sourceEnd());
                            importNames.addExpression(expr0);
                            if (expr0.sourceEnd() > statement.sourceEnd())
                                statement.setEnd(expr0.sourceEnd());
                            
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:611:9: ( COMMA expr0= module_imp )*
                            loop31: do {
                                int alt31 = 2;
                                int LA31_0 = input.LA(1);
                                
                                if ((LA31_0 == COMMA))
                                    alt31 = 1;
                                
                                switch (alt31) {
                                case 1:
                                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:611:11: COMMA expr0= module_imp
                                {
                                    match(input, COMMA, FOLLOW_COMMA_in_import_stmt1883);
                                    pushFollow(FOLLOW_module_imp_in_import_stmt1898);
                                    expr0 = module_imp();
                                    _fsp--;
                                    
                                    importNames.addExpression(expr0);
                                    importNames.setEnd(expr0.sourceEnd());
                                    if (expr0.sourceEnd() > statement.sourceEnd())
                                        statement.setEnd(expr0.sourceEnd());
                                    
                                }
                                    break;
                                
                                default:
                                    break loop31;
                                }
                            } while (true);
                            
                        }
                        
                    }
                        break;
                    case 2:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:623:8: ( STAR )
                    {
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:623:8: ( STAR )
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:624:5: STAR
                        {
                            match(input, STAR, FOLLOW_STAR_in_import_stmt1955);
                            
                            //moduleName.setColumn(moduleName.getColumn()-1);
                            statement = new PythonImportFromStatement(toDLTK(r), new VariableReference(
                                    toDLTK(moduleName)), new PythonAllImportExpression());
                            
                        }
                        
                    }
                        break;
                    
                    }
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end import_stmt
    
    // $ANTLR start dotted_name
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:634:1: dotted_name returns [ Token token = null ] : n= NAME ( DOT n2= NAME )+ ;
    public final Token dotted_name() {
        Token token = null;
        
        Token n = null;
        Token n2 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:634:43: (n= NAME ( DOT n2= NAME )+ )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:635:2: n= NAME ( DOT n2= NAME )+
            {
                
                String value = "";
                
                n = input.LT(1);
                match(input, NAME, FOLLOW_NAME_in_dotted_name2005);
                
                value += n.getText();
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:642:2: ( DOT n2= NAME )+
                int cnt34 = 0;
                loop34: do {
                    int alt34 = 2;
                    int LA34_0 = input.LA(1);
                    
                    if ((LA34_0 == DOT))
                        alt34 = 1;
                    
                    switch (alt34) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:643:3: DOT n2= NAME
                    {
                        match(input, DOT, FOLLOW_DOT_in_dotted_name2015);
                        n2 = input.LT(1);
                        match(input, NAME, FOLLOW_NAME_in_dotted_name2022);
                        
                        value += "." + n2.getText();
                        
                    }
                        break;
                    
                    default:
                        if (cnt34 >= 1)
                            break loop34;
                        EarlyExitException eee = new EarlyExitException(34, input);
                        throw eee;
                    }
                    cnt34++;
                } while (true);
                
                token = new CommonToken(n);
                token.setText(value);
                //token.setColumn( n.getColumn() );
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return token;
    }
    
    // $ANTLR end dotted_name
    
    // $ANTLR start dot_name
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:656:1: dot_name returns [ Token token = null ] : ( (moduleName1= dotted_name ) | (moduleName2= NAME ) );
    public final Token dot_name() {
        Token token = null;
        
        Token moduleName2 = null;
        Token moduleName1 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:656:40: ( (moduleName1= dotted_name ) | (moduleName2= NAME ) )
            int alt35 = 2;
            int LA35_0 = input.LA(1);
            
            if ((LA35_0 == NAME)) {
                int LA35_1 = input.LA(2);
                
                if ((LA35_1 == DOT))
                    alt35 = 1;
                else if ((LA35_1 == NEWLINE || LA35_1 == LPAREN || LA35_1 == COMMA || LA35_1 == SEMI
                        || LA35_1 == 78 || LA35_1 == 80))
                    alt35 = 2;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "656:1: dot_name returns [ Token token = null ] : ( (moduleName1= dotted_name ) | (moduleName2= NAME ) );",
                            35, 1, input);
                    
                    throw nvae;
                }
            } else {
                NoViableAltException nvae = new NoViableAltException(
                        "656:1: dot_name returns [ Token token = null ] : ( (moduleName1= dotted_name ) | (moduleName2= NAME ) );",
                        35, 0, input);
                
                throw nvae;
            }
            switch (alt35) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:657:2: (moduleName1= dotted_name )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:657:2: (moduleName1= dotted_name )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:658:3: moduleName1= dotted_name
                {
                    pushFollow(FOLLOW_dotted_name_in_dot_name2057);
                    moduleName1 = dotted_name();
                    _fsp--;
                    
                    token = moduleName1;
                    
                }
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:664:2: (moduleName2= NAME )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:664:2: (moduleName2= NAME )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:665:3: moduleName2= NAME
                {
                    moduleName2 = input.LT(1);
                    match(input, NAME, FOLLOW_NAME_in_dot_name2077);
                    
                    token = moduleName2;
                    
                }
                
                if (token != null) {
                    //int column = token.getColumn();
                    //token.setColumn( column -1 );
                }
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return token;
    }
    
    // $ANTLR end dot_name
    
    // $ANTLR start module_imp
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:677:1: module_imp returns [ Expression expr = null ] : impName= dot_name ( 'as' as= NAME )? ;
    public final Expression module_imp() {
        Expression expr = null;
        
        Token as = null;
        Token impName = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:677:46: (impName= dot_name ( 'as' as= NAME )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:678:2: impName= dot_name ( 'as' as= NAME )?
            {
                pushFollow(FOLLOW_dot_name_in_module_imp2107);
                impName = dot_name();
                _fsp--;
                
                expr = new PythonImportExpression(toDLTK(impName));
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:682:2: ( 'as' as= NAME )?
                int alt36 = 2;
                int LA36_0 = input.LA(1);
                
                if ((LA36_0 == 80))
                    alt36 = 1;
                switch (alt36) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:683:3: 'as' as= NAME
                {
                    match(input, 80, FOLLOW_80_in_module_imp2119);
                    as = input.LT(1);
                    match(input, NAME, FOLLOW_NAME_in_module_imp2126);
                    
                    expr = new PythonImportAsExpression(toDLTK(impName), toDLTK(as));
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return expr;
    }
    
    // $ANTLR end module_imp
    
    // $ANTLR start global_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:692:1: global_stmt returns [ Statement statement = null ] : 'global' NAME ( COMMA NAME )* ;
    public final Statement global_stmt() {
        Statement statement = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:692:51: ( 'global' NAME ( COMMA NAME )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:693:3: 'global' NAME ( COMMA NAME )*
            {
                match(input, 81, FOLLOW_81_in_global_stmt2153);
                match(input, NAME, FOLLOW_NAME_in_global_stmt2155);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:693:17: ( COMMA NAME )*
                loop37: do {
                    int alt37 = 2;
                    int LA37_0 = input.LA(1);
                    
                    if ((LA37_0 == COMMA))
                        alt37 = 1;
                    
                    switch (alt37) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:693:18: COMMA NAME
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_global_stmt2158);
                        match(input, NAME, FOLLOW_NAME_in_global_stmt2160);
                        
                    }
                        break;
                    
                    default:
                        break loop37;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end global_stmt
    
    // $ANTLR start exec_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:697:1: exec_stmt returns [ Statement statement = null] : e= 'exec' ex= expr ( 'in' ex= test ( COMMA ex= test )? )? ;
    public final Statement exec_stmt() {
        Statement statement = null;
        
        Token e = null;
        Expression ex = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:697:49: (e= 'exec' ex= expr ( 'in' ex= test ( COMMA ex= test )? )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:698:2: e= 'exec' ex= expr ( 'in' ex= test ( COMMA ex= test )? )?
            {
                e = input.LT(1);
                match(input, 82, FOLLOW_82_in_exec_stmt2185);
                pushFollow(FOLLOW_expr_in_exec_stmt2191);
                ex = expr();
                _fsp--;
                
                statement = new ExecStatement(this.converter.convert(e), ex);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:699:2: ( 'in' ex= test ( COMMA ex= test )? )?
                int alt39 = 2;
                int LA39_0 = input.LA(1);
                
                if ((LA39_0 == 83))
                    alt39 = 1;
                switch (alt39) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:699:3: 'in' ex= test ( COMMA ex= test )?
                {
                    match(input, 83, FOLLOW_83_in_exec_stmt2198);
                    pushFollow(FOLLOW_test_in_exec_stmt2207);
                    ex = test();
                    _fsp--;
                    
                    ((ExecStatement) statement).acceptIn(ex);
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:701:3: ( COMMA ex= test )?
                    int alt38 = 2;
                    int LA38_0 = input.LA(1);
                    
                    if ((LA38_0 == COMMA))
                        alt38 = 1;
                    switch (alt38) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:701:4: COMMA ex= test
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_exec_stmt2215);
                        pushFollow(FOLLOW_test_in_exec_stmt2221);
                        ex = test();
                        _fsp--;
                        
                        ((ExecStatement) statement).acceptIn2(ex);
                        
                    }
                        break;
                    
                    }
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end exec_stmt
    
    // $ANTLR start assert_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:705:1: assert_stmt returns [ PythonAssertStatement statement = null ] : tu= 'assert' exp1= test ( COMMA exp2= test )? ;
    public final PythonAssertStatement assert_stmt() {
        PythonAssertStatement statement = null;
        
        Token tu = null;
        Expression exp1 = null;
        
        Expression exp2 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:705:63: (tu= 'assert' exp1= test ( COMMA exp2= test )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:706:2: tu= 'assert' exp1= test ( COMMA exp2= test )?
            {
                tu = input.LT(1);
                match(input, 84, FOLLOW_84_in_assert_stmt2248);
                pushFollow(FOLLOW_test_in_assert_stmt2255);
                exp1 = test();
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:708:2: ( COMMA exp2= test )?
                int alt40 = 2;
                int LA40_0 = input.LA(1);
                
                if ((LA40_0 == COMMA))
                    alt40 = 1;
                switch (alt40) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:708:4: COMMA exp2= test
                {
                    match(input, COMMA, FOLLOW_COMMA_in_assert_stmt2261);
                    pushFollow(FOLLOW_test_in_assert_stmt2267);
                    exp2 = test();
                    _fsp--;
                    
                }
                    break;
                
                }
                
                statement = new PythonAssertStatement(toDLTK(tu), exp1, exp2);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end assert_stmt
    
    // $ANTLR start compound_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:715:1: compound_stmt returns [ Statement statement = null ] : (statement0= if_stmt | statement1= while_stmt | statement2= for_stmt | statement3= try_stmt | statement4= funcdef | statement5= classdef | statement6= with_stmt );
    public final Statement compound_stmt() {
        Statement statement = null;
        
        IfStatement statement0 = null;
        
        PythonWhileStatement statement1 = null;
        
        PythonForStatement statement2 = null;
        
        PythonTryStatement statement3 = null;
        
        MethodDeclaration statement4 = null;
        
        PythonClassDeclaration statement5 = null;
        
        Statement statement6 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:715:53: (statement0= if_stmt | statement1= while_stmt | statement2= for_stmt | statement3= try_stmt | statement4= funcdef | statement5= classdef | statement6= with_stmt )
            int alt41 = 7;
            switch (input.LA(1)) {
            case 85: {
                alt41 = 1;
            }
                break;
            case 88: {
                alt41 = 2;
            }
                break;
            case 89: {
                alt41 = 3;
            }
                break;
            case 90: {
                alt41 = 4;
            }
                break;
            case DECORATOR_S:
            case 69: {
                alt41 = 5;
            }
                break;
            case 99: {
                alt41 = 6;
            }
                break;
            case 98: {
                alt41 = 7;
            }
                break;
            default:
                NoViableAltException nvae = new NoViableAltException(
                        "715:1: compound_stmt returns [ Statement statement = null ] : (statement0= if_stmt | statement1= while_stmt | statement2= for_stmt | statement3= try_stmt | statement4= funcdef | statement5= classdef | statement6= with_stmt );",
                        41, 0, input);
                
                throw nvae;
            }
            
            switch (alt41) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:716:2: statement0= if_stmt
            {
                pushFollow(FOLLOW_if_stmt_in_compound_stmt2295);
                statement0 = if_stmt();
                _fsp--;
                
                statement = statement0;
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:717:4: statement1= while_stmt
            {
                pushFollow(FOLLOW_while_stmt_in_compound_stmt2306);
                statement1 = while_stmt();
                _fsp--;
                
                statement = statement1;
                
            }
                break;
            case 3:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:718:4: statement2= for_stmt
            {
                pushFollow(FOLLOW_for_stmt_in_compound_stmt2316);
                statement2 = for_stmt();
                _fsp--;
                
                statement = statement2;
                
            }
                break;
            case 4:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:719:4: statement3= try_stmt
            {
                pushFollow(FOLLOW_try_stmt_in_compound_stmt2326);
                statement3 = try_stmt();
                _fsp--;
                
                statement = statement3;
                
            }
                break;
            case 5:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:720:4: statement4= funcdef
            {
                pushFollow(FOLLOW_funcdef_in_compound_stmt2336);
                statement4 = funcdef();
                _fsp--;
                
                statement = statement4;
                
            }
                break;
            case 6:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:721:4: statement5= classdef
            {
                pushFollow(FOLLOW_classdef_in_compound_stmt2346);
                statement5 = classdef();
                _fsp--;
                
                statement = statement5;
                
            }
                break;
            case 7:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:722:4: statement6= with_stmt
            {
                pushFollow(FOLLOW_with_stmt_in_compound_stmt2356);
                statement6 = with_stmt();
                _fsp--;
                
                statement = statement6;
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end compound_stmt
    
    // $ANTLR start if_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:725:1: if_stmt returns [ IfStatement statement = null ] : is= 'if' mn= test COLON body= suite (z= 'elif' mn= test COLON body= suite )* ( 'else' COLON body= suite )? ;
    public final IfStatement if_stmt() {
        IfStatement statement = null;
        
        Token is = null;
        Token z = null;
        Expression mn = null;
        
        Block body = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:725:49: (is= 'if' mn= test COLON body= suite (z= 'elif' mn= test COLON body= suite )* ( 'else' COLON body= suite )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:726:3: is= 'if' mn= test COLON body= suite (z= 'elif' mn= test COLON body= suite )* ( 'else' COLON body= suite )?
            {
                is = input.LT(1);
                match(input, 85, FOLLOW_85_in_if_stmt2379);
                pushFollow(FOLLOW_test_in_if_stmt2388);
                mn = test();
                _fsp--;
                
                match(input, COLON, FOLLOW_COLON_in_if_stmt2392);
                pushFollow(FOLLOW_suite_in_if_stmt2401);
                body = suite();
                _fsp--;
                
                IfStatement t, base;
                List ifs = new ArrayList();
                statement = new IfStatement(toDLTK(is), mn, body);
                statement.setEnd(body.sourceEnd());
                ifs.add(statement);
                base = statement;
                t = statement;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:740:3: (z= 'elif' mn= test COLON body= suite )*
                loop42: do {
                    int alt42 = 2;
                    int LA42_0 = input.LA(1);
                    
                    if ((LA42_0 == 86))
                        alt42 = 1;
                    
                    switch (alt42) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:741:4: z= 'elif' mn= test COLON body= suite
                    {
                        z = input.LT(1);
                        match(input, 86, FOLLOW_86_in_if_stmt2422);
                        pushFollow(FOLLOW_test_in_if_stmt2431);
                        mn = test();
                        _fsp--;
                        
                        match(input, COLON, FOLLOW_COLON_in_if_stmt2437);
                        pushFollow(FOLLOW_suite_in_if_stmt2445);
                        body = suite();
                        _fsp--;
                        
                        IfStatement elseIfStatement = new IfStatement(toDLTK(z), mn, body);
                        t.acceptElse(elseIfStatement);
                        t = elseIfStatement;
                        ifs.add(t);
                        for (Iterator i = ifs.iterator(); i.hasNext(); ((IfStatement) i.next()).setEnd(body
                                .sourceEnd()))
                            ;
                        base.setEnd(elseIfStatement.sourceEnd());
                        
                    }
                        break;
                    
                    default:
                        break loop42;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:754:3: ( 'else' COLON body= suite )?
                int alt43 = 2;
                int LA43_0 = input.LA(1);
                
                if ((LA43_0 == 87))
                    alt43 = 1;
                switch (alt43) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:755:4: 'else' COLON body= suite
                {
                    match(input, 87, FOLLOW_87_in_if_stmt2466);
                    match(input, COLON, FOLLOW_COLON_in_if_stmt2472);
                    pushFollow(FOLLOW_suite_in_if_stmt2482);
                    body = suite();
                    _fsp--;
                    
                    t.setElse(body);
                    for (Iterator i = ifs.iterator(); i.hasNext(); ((IfStatement) i.next()).setEnd(body
                            .sourceEnd()))
                        ;
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end if_stmt
    
    // $ANTLR start while_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:765:1: while_stmt returns [ PythonWhileStatement whileStatement = null ] : is= 'while' expression= test COLON body= suite ( 'else' COLON body= suite )? ;
    public final PythonWhileStatement while_stmt() {
        PythonWhileStatement whileStatement = null;
        
        Token is = null;
        Expression expression = null;
        
        Block body = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:765:66: (is= 'while' expression= test COLON body= suite ( 'else' COLON body= suite )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:766:3: is= 'while' expression= test COLON body= suite ( 'else' COLON body= suite )?
            {
                is = input.LT(1);
                match(input, 88, FOLLOW_88_in_while_stmt2518);
                pushFollow(FOLLOW_test_in_while_stmt2526);
                expression = test();
                _fsp--;
                
                match(input, COLON, FOLLOW_COLON_in_while_stmt2530);
                pushFollow(FOLLOW_suite_in_while_stmt2540);
                body = suite();
                _fsp--;
                
                whileStatement = new PythonWhileStatement(toDLTK(is), expression, body);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:773:3: ( 'else' COLON body= suite )?
                int alt44 = 2;
                int LA44_0 = input.LA(1);
                
                if ((LA44_0 == 87))
                    alt44 = 1;
                switch (alt44) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:774:4: 'else' COLON body= suite
                {
                    match(input, 87, FOLLOW_87_in_while_stmt2554);
                    match(input, COLON, FOLLOW_COLON_in_while_stmt2556);
                    pushFollow(FOLLOW_suite_in_while_stmt2566);
                    body = suite();
                    _fsp--;
                    
                    whileStatement.setElseStatement(body);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return whileStatement;
    }
    
    // $ANTLR end while_stmt
    
    // $ANTLR start for_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:782:1: for_stmt returns [ PythonForStatement statement = null] : is= 'for' mains= exprlist 'in' conds= testlist COLON body= suite ( 'else' COLON body= suite )? ;
    public final PythonForStatement for_stmt() {
        PythonForStatement statement = null;
        
        Token is = null;
        PythonTestListExpression mains = null;
        
        Expression conds = null;
        
        Block body = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:782:56: (is= 'for' mains= exprlist 'in' conds= testlist COLON body= suite ( 'else' COLON body= suite )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:783:3: is= 'for' mains= exprlist 'in' conds= testlist COLON body= suite ( 'else' COLON body= suite )?
            {
                is = input.LT(1);
                match(input, 89, FOLLOW_89_in_for_stmt2597);
                pushFollow(FOLLOW_exprlist_in_for_stmt2606);
                mains = exprlist();
                _fsp--;
                
                match(input, 83, FOLLOW_83_in_for_stmt2611);
                pushFollow(FOLLOW_testlist_in_for_stmt2619);
                conds = testlist();
                _fsp--;
                
                match(input, COLON, FOLLOW_COLON_in_for_stmt2623);
                pushFollow(FOLLOW_suite_in_for_stmt2632);
                body = suite();
                _fsp--;
                
                statement = new PythonForStatement(toDLTK(is), mains, conds, body);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:792:3: ( 'else' COLON body= suite )?
                int alt45 = 2;
                int LA45_0 = input.LA(1);
                
                if ((LA45_0 == 87))
                    alt45 = 1;
                switch (alt45) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:793:4: 'else' COLON body= suite
                {
                    match(input, 87, FOLLOW_87_in_for_stmt2647);
                    match(input, COLON, FOLLOW_COLON_in_for_stmt2653);
                    pushFollow(FOLLOW_suite_in_for_stmt2663);
                    body = suite();
                    _fsp--;
                    
                    statement.acceptElse(body);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end for_stmt
    
    // $ANTLR start try_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:804:1: try_stmt returns [ PythonTryStatement statement = null ] : is= 'try' COLON body= suite ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) ) ;
    public final PythonTryStatement try_stmt() {
        PythonTryStatement statement = null;
        
        Token is = null;
        Token ex_ = null;
        Token elseT = null;
        Token fin = null;
        Block body = null;
        
        Expression t1 = null;
        
        Expression t2 = null;
        
        Block su = null;
        
        Block elseBlock = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:804:58: (is= 'try' COLON body= suite ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:805:2: is= 'try' COLON body= suite ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) )
            {
                is = input.LT(1);
                match(input, 90, FOLLOW_90_in_try_stmt2695);
                match(input, COLON, FOLLOW_COLON_in_try_stmt2699);
                
                Token lastTok = is;
                
                List catches = new ArrayList();
                
                pushFollow(FOLLOW_suite_in_try_stmt2714);
                body = suite();
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:813:6: ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) )
                int alt50 = 2;
                int LA50_0 = input.LA(1);
                
                if ((LA50_0 == 91))
                    alt50 = 1;
                else if ((LA50_0 == 92))
                    alt50 = 2;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "813:6: ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) )",
                            50, 0, input);
                    
                    throw nvae;
                }
                switch (alt50) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:814:9: ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? )
                {
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:814:9: ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? )
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:815:3: (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )?
                    {
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:815:3: (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+
                        int cnt48 = 0;
                        loop48: do {
                            int alt48 = 2;
                            int LA48_0 = input.LA(1);
                            
                            if ((LA48_0 == 91))
                                alt48 = 1;
                            
                            switch (alt48) {
                            case 1:
                                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:816:4: ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite
                            {
                                ex_ = input.LT(1);
                                match(input, 91, FOLLOW_91_in_try_stmt2752);
                                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:817:4: (t1= test ( COMMA t2= test )? )?
                                int alt47 = 2;
                                int LA47_0 = input.LA(1);
                                
                                if ((LA47_0 == LPAREN || LA47_0 == NAME
                                        || (LA47_0 >= PLUS && LA47_0 <= MINUS)
                                        || (LA47_0 >= TILDE && LA47_0 <= LBRACK) || LA47_0 == LCURLY
                                        || (LA47_0 >= BACKQUOTE && LA47_0 <= STRING) || LA47_0 == 95 || LA47_0 == 97))
                                    alt47 = 1;
                                switch (alt47) {
                                case 1:
                                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:818:5: t1= test ( COMMA t2= test )?
                                {
                                    pushFollow(FOLLOW_test_in_try_stmt2767);
                                    t1 = test();
                                    _fsp--;
                                    
                                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:819:5: ( COMMA t2= test )?
                                    int alt46 = 2;
                                    int LA46_0 = input.LA(1);
                                    
                                    if ((LA46_0 == COMMA))
                                        alt46 = 1;
                                    switch (alt46) {
                                    case 1:
                                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:819:6: COMMA t2= test
                                    {
                                        match(input, COMMA, FOLLOW_COMMA_in_try_stmt2774);
                                        pushFollow(FOLLOW_test_in_try_stmt2780);
                                        t2 = test();
                                        _fsp--;
                                        
                                    }
                                        break;
                                    
                                    }
                                    
                                }
                                    break;
                                
                                }
                                
                                match(input, COLON, FOLLOW_COLON_in_try_stmt2793);
                                pushFollow(FOLLOW_suite_in_try_stmt2802);
                                su = suite();
                                _fsp--;
                                
                                lastTok = ex_;
                                catches.add(new PythonExceptStatement(toDLTK(ex_), t1, t2, su));
                                
                            }
                                break;
                            
                            default:
                                if (cnt48 >= 1)
                                    break loop48;
                                EarlyExitException eee = new EarlyExitException(48, input);
                                throw eee;
                            }
                            cnt48++;
                        } while (true);
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:828:3: ( (elseT= 'else' COLON elseBlock= suite ) )?
                        int alt49 = 2;
                        int LA49_0 = input.LA(1);
                        
                        if ((LA49_0 == 87))
                            alt49 = 1;
                        switch (alt49) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:829:4: (elseT= 'else' COLON elseBlock= suite )
                        {
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:829:4: (elseT= 'else' COLON elseBlock= suite )
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:829:6: elseT= 'else' COLON elseBlock= suite
                            {
                                elseT = input.LT(1);
                                match(input, 87, FOLLOW_87_in_try_stmt2827);
                                match(input, COLON, FOLLOW_COLON_in_try_stmt2833);
                                pushFollow(FOLLOW_suite_in_try_stmt2843);
                                elseBlock = suite();
                                _fsp--;
                                
                                lastTok = elseT;
                                
                            }
                            
                        }
                            break;
                        
                        }
                        
                    }
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:841:9: (fin= 'finally' COLON su= suite )
                {
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:841:9: (fin= 'finally' COLON su= suite )
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:842:10: fin= 'finally' COLON su= suite
                    {
                        fin = input.LT(1);
                        match(input, 92, FOLLOW_92_in_try_stmt2954);
                        
                        lastTok = fin;
                        
                        match(input, COLON, FOLLOW_COLON_in_try_stmt2977);
                        pushFollow(FOLLOW_suite_in_try_stmt2993);
                        su = suite();
                        _fsp--;
                        
                        catches.add(new TryFinallyStatement(toDLTK(fin), su));
                        
                    }
                    
                }
                    break;
                
                }
                
                statement = new PythonTryStatement(toDLTK(is), body, catches);
                statement.setElseStatement(elseBlock);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end try_stmt
    
    // $ANTLR start suite
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:859:1: suite returns [ Block statement = new Block() ] : ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT ) ;
    public final Block suite() {
        Block statement = new Block();
        
        Token ind = null;
        Token d = null;
        ArrayList k = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:859:48: ( ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:860:4: ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT )
            {
                
                ArrayList l = new ArrayList();
                int startPos = -1;
                int endPos = -1;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:865:4: ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT )
                int alt52 = 2;
                int LA52_0 = input.LA(1);
                
                if ((LA52_0 == LPAREN || LA52_0 == NAME || (LA52_0 >= PLUS && LA52_0 <= MINUS)
                        || (LA52_0 >= TILDE && LA52_0 <= LBRACK) || LA52_0 == LCURLY
                        || (LA52_0 >= BACKQUOTE && LA52_0 <= STRING) || (LA52_0 >= 70 && LA52_0 <= 79)
                        || (LA52_0 >= 81 && LA52_0 <= 82) || LA52_0 == 84 || LA52_0 == 95 || LA52_0 == 97))
                    alt52 = 1;
                else if ((LA52_0 == NEWLINE))
                    alt52 = 2;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "865:4: ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT )", 52, 0,
                            input);
                    
                    throw nvae;
                }
                switch (alt52) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:866:5: simple_stmt[ l ]
                {
                    pushFollow(FOLLOW_simple_stmt_in_suite3051);
                    simple_stmt(l);
                    _fsp--;
                    
                    Iterator i = l.iterator();
                    while (i.hasNext()) {
                        Statement sst = (Statement) i.next();
                        if (sst != null) {
                            if (sst.sourceStart() < startPos || startPos == -1)
                                startPos = sst.sourceStart();
                            if (sst.sourceEnd() > endPos || endPos == -1)
                                endPos = sst.sourceEnd();
                            statement.addStatement(sst);
                        }
                    }
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:883:5: NEWLINE ind= INDENT (k= stmt )+ d= DEDENT
                {
                    match(input, NEWLINE, FOLLOW_NEWLINE_in_suite3072);
                    ind = input.LT(1);
                    match(input, INDENT, FOLLOW_INDENT_in_suite3082);
                    
                    if (ind != null) {
                        
                        int s = converter.convert(ind).getColumn();
                        if (s < startPos && s != -1)
                            startPos = s;
                    }
                    //ArrayList k;
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:895:5: (k= stmt )+
                    int cnt51 = 0;
                    loop51: do {
                        int alt51 = 2;
                        int LA51_0 = input.LA(1);
                        
                        if (((LA51_0 >= DECORATOR_S && LA51_0 <= LPAREN) || LA51_0 == NAME
                                || (LA51_0 >= PLUS && LA51_0 <= MINUS)
                                || (LA51_0 >= TILDE && LA51_0 <= LBRACK) || LA51_0 == LCURLY
                                || (LA51_0 >= BACKQUOTE && LA51_0 <= STRING)
                                || (LA51_0 >= 69 && LA51_0 <= 79) || (LA51_0 >= 81 && LA51_0 <= 82)
                                || (LA51_0 >= 84 && LA51_0 <= 85) || (LA51_0 >= 88 && LA51_0 <= 90)
                                || LA51_0 == 95 || (LA51_0 >= 97 && LA51_0 <= 99)))
                            alt51 = 1;
                        
                        switch (alt51) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:896:6: k= stmt
                        {
                            pushFollow(FOLLOW_stmt_in_suite3106);
                            k = stmt();
                            _fsp--;
                            
                            //l.addAll( k );
                            if (k != null) {
                                Iterator i = k.iterator();
                                while (i.hasNext()) {
                                    Statement sst = (Statement) i.next();
                                    if (sst != null) {
                                        statement.addStatement(sst);
                                        if (sst.sourceStart() < startPos || startPos == -1)
                                            startPos = sst.sourceStart();
                                        if (sst.sourceEnd() > endPos || endPos == -1)
                                            endPos = sst.sourceEnd();
                                    }
                                }
                            }
                            
                        }
                            break;
                        
                        default:
                            if (cnt51 >= 1)
                                break loop51;
                            EarlyExitException eee = new EarlyExitException(51, input);
                            throw eee;
                        }
                        cnt51++;
                    } while (true);
                    
                    d = input.LT(1);
                    match(input, DEDENT, FOLLOW_DEDENT_in_suite3132);
                    
                    if (d != null) {
                        int e = converter.convert(d).getColumn() - 1;
                        if (e > endPos)
                            endPos = e;
                    }
                    
                }
                    break;
                
                }
                
                //endPos -= 1;
                //statement = new Block( startPos, endPos, l );
                statement.setStart(startPos);
                //if( endPos == -1 ) {
                //	endPos = length;
                //}
                statement.setEnd(endPos);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return statement;
    }
    
    // $ANTLR end suite
    
    // $ANTLR start or_test
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:943:1: or_test returns [ Expression exp = null ] : exp0= and_test (r= 'or' v= and_test )* ;
    public final Expression or_test() {
        Expression exp = null;
        
        Token r = null;
        Expression exp0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:943:43: (exp0= and_test (r= 'or' v= and_test )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:944:2: exp0= and_test (r= 'or' v= and_test )*
            {
                pushFollow(FOLLOW_and_test_in_or_test3182);
                exp0 = and_test();
                _fsp--;
                
                exp = exp0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:948:2: (r= 'or' v= and_test )*
                loop53: do {
                    int alt53 = 2;
                    int LA53_0 = input.LA(1);
                    
                    if ((LA53_0 == 93))
                        alt53 = 1;
                    
                    switch (alt53) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:949:3: r= 'or' v= and_test
                    {
                        r = input.LT(1);
                        match(input, 93, FOLLOW_93_in_or_test3196);
                        pushFollow(FOLLOW_and_test_in_or_test3205);
                        v = and_test();
                        _fsp--;
                        
                        exp = new BinaryExpression(exp, Expression.E_LOR, v);
                        
                    }
                        break;
                    
                    default:
                        break loop53;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end or_test
    
    // $ANTLR start test
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:957:1: test returns [ Expression exp = null ] : (exp0= or_test | exp0= lambdef );
    public final Expression test() {
        Expression exp = null;
        
        Expression exp0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:957:39: (exp0= or_test | exp0= lambdef )
            int alt54 = 2;
            int LA54_0 = input.LA(1);
            
            if ((LA54_0 == LPAREN || LA54_0 == NAME || (LA54_0 >= PLUS && LA54_0 <= MINUS)
                    || (LA54_0 >= TILDE && LA54_0 <= LBRACK) || LA54_0 == LCURLY
                    || (LA54_0 >= BACKQUOTE && LA54_0 <= STRING) || LA54_0 == 95))
                alt54 = 1;
            else if ((LA54_0 == 97))
                alt54 = 2;
            else {
                NoViableAltException nvae = new NoViableAltException(
                        "957:1: test returns [ Expression exp = null ] : (exp0= or_test | exp0= lambdef );",
                        54, 0, input);
                
                throw nvae;
            }
            switch (alt54) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:958:3: exp0= or_test
            {
                pushFollow(FOLLOW_or_test_in_test3234);
                exp0 = or_test();
                _fsp--;
                
                exp = exp0;
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:960:4: exp0= lambdef
            {
                pushFollow(FOLLOW_lambdef_in_test3249);
                exp0 = lambdef();
                _fsp--;
                
                exp = exp0;
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end test
    
    // $ANTLR start and_test
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:963:1: and_test returns [ Expression exp = null ] : exp0= not_test (m= 'and' v= not_test )* ;
    public final Expression and_test() {
        Expression exp = null;
        
        Token m = null;
        Expression exp0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:963:43: (exp0= not_test (m= 'and' v= not_test )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:964:2: exp0= not_test (m= 'and' v= not_test )*
            {
                pushFollow(FOLLOW_not_test_in_and_test3272);
                exp0 = not_test();
                _fsp--;
                
                exp = exp0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:968:2: (m= 'and' v= not_test )*
                loop55: do {
                    int alt55 = 2;
                    int LA55_0 = input.LA(1);
                    
                    if ((LA55_0 == 94))
                        alt55 = 1;
                    
                    switch (alt55) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:969:3: m= 'and' v= not_test
                    {
                        m = input.LT(1);
                        match(input, 94, FOLLOW_94_in_and_test3286);
                        pushFollow(FOLLOW_not_test_in_and_test3295);
                        v = not_test();
                        _fsp--;
                        
                        exp = new BinaryExpression(exp, Expression.E_LAND, v);
                        
                    }
                        break;
                    
                    default:
                        break loop55;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end and_test
    
    // $ANTLR start not_test
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:977:1: not_test returns [ Expression exp = null ] : ( (n= 'not' v= not_test ) | exp0= comparison );
    public final Expression not_test() {
        Expression exp = null;
        
        Token n = null;
        Expression v = null;
        
        Expression exp0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:977:43: ( (n= 'not' v= not_test ) | exp0= comparison )
            int alt56 = 2;
            int LA56_0 = input.LA(1);
            
            if ((LA56_0 == 95))
                alt56 = 1;
            else if ((LA56_0 == LPAREN || LA56_0 == NAME || (LA56_0 >= PLUS && LA56_0 <= MINUS)
                    || (LA56_0 >= TILDE && LA56_0 <= LBRACK) || LA56_0 == LCURLY || (LA56_0 >= BACKQUOTE && LA56_0 <= STRING)))
                alt56 = 2;
            else {
                NoViableAltException nvae = new NoViableAltException(
                        "977:1: not_test returns [ Expression exp = null ] : ( (n= 'not' v= not_test ) | exp0= comparison );",
                        56, 0, input);
                
                throw nvae;
            }
            switch (alt56) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:978:2: (n= 'not' v= not_test )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:978:2: (n= 'not' v= not_test )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:979:3: n= 'not' v= not_test
                {
                    n = input.LT(1);
                    match(input, 95, FOLLOW_95_in_not_test3328);
                    pushFollow(FOLLOW_not_test_in_not_test3337);
                    v = not_test();
                    _fsp--;
                    
                    exp = new UnaryExpression(toDLTK(n), Expression.E_LNOT, v);
                    
                }
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:985:4: exp0= comparison
            {
                pushFollow(FOLLOW_comparison_in_not_test3356);
                exp0 = comparison();
                _fsp--;
                
                exp = exp0;
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end not_test
    
    // $ANTLR start comparison
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:988:1: comparison returns [ Expression exp = null ] : exp0= expr (tu= comp_op v= expr )* ;
    public final Expression comparison() {
        Expression exp = null;
        
        Expression exp0 = null;
        
        int tu = 0;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:988:45: (exp0= expr (tu= comp_op v= expr )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:989:2: exp0= expr (tu= comp_op v= expr )*
            {
                pushFollow(FOLLOW_expr_in_comparison3379);
                exp0 = expr();
                _fsp--;
                
                exp = exp0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:993:2: (tu= comp_op v= expr )*
                loop57: do {
                    int alt57 = 2;
                    int LA57_0 = input.LA(1);
                    
                    if (((LA57_0 >= LESS && LA57_0 <= NOTEQUAL) || LA57_0 == 83 || (LA57_0 >= 95 && LA57_0 <= 96)))
                        alt57 = 1;
                    
                    switch (alt57) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:994:3: tu= comp_op v= expr
                    {
                        pushFollow(FOLLOW_comp_op_in_comparison3393);
                        tu = comp_op();
                        _fsp--;
                        
                        pushFollow(FOLLOW_expr_in_comparison3402);
                        v = expr();
                        _fsp--;
                        
                        exp = new BinaryExpression(exp, tu, v);
                        
                    }
                        break;
                    
                    default:
                        break loop57;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end comparison
    
    // $ANTLR start comp_op
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1002:1: comp_op returns [ int t = Expression.E_EMPTY ] : (t1= LESS | t2= GREATER | t3= EQUAL | t4= GREATEREQUAL | t5= LESSEQUAL | t6= ALT_NOTEQUAL | t7= NOTEQUAL | t8= 'in' | t9= 'not' 'in' | t10= 'is' | t11= 'is' 'not' );
    public final int comp_op() {
        int t = Expression.E_EMPTY;
        
        Token t1 = null;
        Token t2 = null;
        Token t3 = null;
        Token t4 = null;
        Token t5 = null;
        Token t6 = null;
        Token t7 = null;
        Token t8 = null;
        Token t9 = null;
        Token t10 = null;
        Token t11 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1002:48: (t1= LESS | t2= GREATER | t3= EQUAL | t4= GREATEREQUAL | t5= LESSEQUAL | t6= ALT_NOTEQUAL | t7= NOTEQUAL | t8= 'in' | t9= 'not' 'in' | t10= 'is' | t11= 'is' 'not' )
            int alt58 = 11;
            switch (input.LA(1)) {
            case LESS: {
                alt58 = 1;
            }
                break;
            case GREATER: {
                alt58 = 2;
            }
                break;
            case EQUAL: {
                alt58 = 3;
            }
                break;
            case GREATEREQUAL: {
                alt58 = 4;
            }
                break;
            case LESSEQUAL: {
                alt58 = 5;
            }
                break;
            case ALT_NOTEQUAL: {
                alt58 = 6;
            }
                break;
            case NOTEQUAL: {
                alt58 = 7;
            }
                break;
            case 83: {
                alt58 = 8;
            }
                break;
            case 95: {
                alt58 = 9;
            }
                break;
            case 96: {
                int LA58_10 = input.LA(2);
                
                if ((LA58_10 == 95))
                    alt58 = 11;
                else if ((LA58_10 == LPAREN || LA58_10 == NAME || (LA58_10 >= PLUS && LA58_10 <= MINUS)
                        || (LA58_10 >= TILDE && LA58_10 <= LBRACK) || LA58_10 == LCURLY || (LA58_10 >= BACKQUOTE && LA58_10 <= STRING)))
                    alt58 = 10;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "1002:1: comp_op returns [ int t = Expression.E_EMPTY ] : (t1= LESS | t2= GREATER | t3= EQUAL | t4= GREATEREQUAL | t5= LESSEQUAL | t6= ALT_NOTEQUAL | t7= NOTEQUAL | t8= 'in' | t9= 'not' 'in' | t10= 'is' | t11= 'is' 'not' );",
                            58, 10, input);
                    
                    throw nvae;
                }
            }
                break;
            default:
                NoViableAltException nvae = new NoViableAltException(
                        "1002:1: comp_op returns [ int t = Expression.E_EMPTY ] : (t1= LESS | t2= GREATER | t3= EQUAL | t4= GREATEREQUAL | t5= LESSEQUAL | t6= ALT_NOTEQUAL | t7= NOTEQUAL | t8= 'in' | t9= 'not' 'in' | t10= 'is' | t11= 'is' 'not' );",
                        58, 0, input);
                
                throw nvae;
            }
            
            switch (alt58) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1003:3: t1= LESS
            {
                t1 = input.LT(1);
                match(input, LESS, FOLLOW_LESS_in_comp_op3434);
                t = Expression.E_LT;
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1004:3: t2= GREATER
            {
                t2 = input.LT(1);
                match(input, GREATER, FOLLOW_GREATER_in_comp_op3446);
                t = Expression.E_GT;
                
            }
                break;
            case 3:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1005:3: t3= EQUAL
            {
                t3 = input.LT(1);
                match(input, EQUAL, FOLLOW_EQUAL_in_comp_op3457);
                t = Expression.E_EQUAL;
                
            }
                break;
            case 4:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1006:3: t4= GREATEREQUAL
            {
                t4 = input.LT(1);
                match(input, GREATEREQUAL, FOLLOW_GREATEREQUAL_in_comp_op3470);
                t = Expression.E_GE;
                
            }
                break;
            case 5:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1007:3: t5= LESSEQUAL
            {
                t5 = input.LT(1);
                match(input, LESSEQUAL, FOLLOW_LESSEQUAL_in_comp_op3480);
                t = Expression.E_LE;
                
            }
                break;
            case 6:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1008:3: t6= ALT_NOTEQUAL
            {
                t6 = input.LT(1);
                match(input, ALT_NOTEQUAL, FOLLOW_ALT_NOTEQUAL_in_comp_op3494);
                t = Expression.E_NOT_EQUAL2;
                
            }
                break;
            case 7:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1009:3: t7= NOTEQUAL
            {
                t7 = input.LT(1);
                match(input, NOTEQUAL, FOLLOW_NOTEQUAL_in_comp_op3506);
                t = Expression.E_NOT_EQUAL;
                
            }
                break;
            case 8:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1010:3: t8= 'in'
            {
                t8 = input.LT(1);
                match(input, 83, FOLLOW_83_in_comp_op3521);
                t = Expression.E_IN;
                
            }
                break;
            case 9:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1011:3: t9= 'not' 'in'
            {
                t9 = input.LT(1);
                match(input, 95, FOLLOW_95_in_comp_op3531);
                match(input, 83, FOLLOW_83_in_comp_op3533);
                t = Expression.E_NOTIN;
                
            }
                break;
            case 10:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1012:3: t10= 'is'
            {
                t10 = input.LT(1);
                match(input, 96, FOLLOW_96_in_comp_op3543);
                t = Expression.E_IS;
                
            }
                break;
            case 11:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1013:3: t11= 'is' 'not'
            {
                t11 = input.LT(1);
                match(input, 96, FOLLOW_96_in_comp_op3553);
                match(input, 95, FOLLOW_95_in_comp_op3555);
                t = Expression.E_ISNOT;
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return t;
    }
    
    // $ANTLR end comp_op
    
    // $ANTLR start expr
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1016:1: expr returns [ Expression e = null] : e0= xor_expr (tu= VBAR v= xor_expr )* ;
    public final Expression expr() {
        Expression e = null;
        
        Token tu = null;
        Expression e0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1016:36: (e0= xor_expr (tu= VBAR v= xor_expr )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1017:2: e0= xor_expr (tu= VBAR v= xor_expr )*
            {
                pushFollow(FOLLOW_xor_expr_in_expr3577);
                e0 = xor_expr();
                _fsp--;
                
                e = e0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1021:2: (tu= VBAR v= xor_expr )*
                loop59: do {
                    int alt59 = 2;
                    int LA59_0 = input.LA(1);
                    
                    if ((LA59_0 == VBAR))
                        alt59 = 1;
                    
                    switch (alt59) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1022:3: tu= VBAR v= xor_expr
                    {
                        tu = input.LT(1);
                        match(input, VBAR, FOLLOW_VBAR_in_expr3591);
                        pushFollow(FOLLOW_xor_expr_in_expr3601);
                        v = xor_expr();
                        _fsp--;
                        
                        e = new BinaryExpression(e, Expression.E_LOR, v);
                        
                    }
                        break;
                    
                    default:
                        break loop59;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end expr
    
    // $ANTLR start xor_expr
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1030:1: xor_expr returns [ Expression e = null ] : e0= and_expr (tu= CIRCUMFLEX v= and_expr )* ;
    public final Expression xor_expr() {
        Expression e = null;
        
        Token tu = null;
        Expression e0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1030:41: (e0= and_expr (tu= CIRCUMFLEX v= and_expr )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1031:2: e0= and_expr (tu= CIRCUMFLEX v= and_expr )*
            {
                pushFollow(FOLLOW_and_expr_in_xor_expr3634);
                e0 = and_expr();
                _fsp--;
                
                e = e0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1035:2: (tu= CIRCUMFLEX v= and_expr )*
                loop60: do {
                    int alt60 = 2;
                    int LA60_0 = input.LA(1);
                    
                    if ((LA60_0 == CIRCUMFLEX))
                        alt60 = 1;
                    
                    switch (alt60) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1036:3: tu= CIRCUMFLEX v= and_expr
                    {
                        tu = input.LT(1);
                        match(input, CIRCUMFLEX, FOLLOW_CIRCUMFLEX_in_xor_expr3648);
                        pushFollow(FOLLOW_and_expr_in_xor_expr3657);
                        v = and_expr();
                        _fsp--;
                        
                        e = new BinaryExpression(e, Expression.E_XOR, v);
                        
                    }
                        break;
                    
                    default:
                        break loop60;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end xor_expr
    
    // $ANTLR start and_expr
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1044:1: and_expr returns [ Expression e = null ] : e0= shift_expr (tu= AMPER v= shift_expr )* ;
    public final Expression and_expr() {
        Expression e = null;
        
        Token tu = null;
        Expression e0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1044:41: (e0= shift_expr (tu= AMPER v= shift_expr )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1045:2: e0= shift_expr (tu= AMPER v= shift_expr )*
            {
                pushFollow(FOLLOW_shift_expr_in_and_expr3688);
                e0 = shift_expr();
                _fsp--;
                
                e = e0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1049:2: (tu= AMPER v= shift_expr )*
                loop61: do {
                    int alt61 = 2;
                    int LA61_0 = input.LA(1);
                    
                    if ((LA61_0 == AMPER))
                        alt61 = 1;
                    
                    switch (alt61) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1050:3: tu= AMPER v= shift_expr
                    {
                        tu = input.LT(1);
                        match(input, AMPER, FOLLOW_AMPER_in_and_expr3703);
                        pushFollow(FOLLOW_shift_expr_in_and_expr3713);
                        v = shift_expr();
                        _fsp--;
                        
                        e = new BinaryExpression(e, Expression.E_LAND, v);
                        
                    }
                        break;
                    
                    default:
                        break loop61;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end and_expr
    
    // $ANTLR start shift_expr
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1057:1: shift_expr returns [ Expression e = null ] : e0= arith_expr ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )* ;
    public final Expression shift_expr() {
        Expression e = null;
        
        Token t1 = null;
        Token t2 = null;
        Expression e0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1057:43: (e0= arith_expr ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1058:2: e0= arith_expr ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )*
            {
                pushFollow(FOLLOW_arith_expr_in_shift_expr3743);
                e0 = arith_expr();
                _fsp--;
                
                e = e0;
                
                Token tk = null;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1065:2: ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )*
                loop63: do {
                    int alt63 = 2;
                    int LA63_0 = input.LA(1);
                    
                    if ((LA63_0 == RIGHTSHIFT || LA63_0 == LEFTSHIFT))
                        alt63 = 1;
                    
                    switch (alt63) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1066:3: (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr
                    {
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1066:3: (t1= LEFTSHIFT | t2= RIGHTSHIFT )
                        int alt62 = 2;
                        int LA62_0 = input.LA(1);
                        
                        if ((LA62_0 == LEFTSHIFT))
                            alt62 = 1;
                        else if ((LA62_0 == RIGHTSHIFT))
                            alt62 = 2;
                        else {
                            NoViableAltException nvae = new NoViableAltException(
                                    "1066:3: (t1= LEFTSHIFT | t2= RIGHTSHIFT )", 62, 0, input);
                            
                            throw nvae;
                        }
                        switch (alt62) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1067:4: t1= LEFTSHIFT
                        {
                            t1 = input.LT(1);
                            match(input, LEFTSHIFT, FOLLOW_LEFTSHIFT_in_shift_expr3767);
                            
                            tk = t1;
                            
                        }
                            break;
                        case 2:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1072:4: t2= RIGHTSHIFT
                        {
                            t2 = input.LT(1);
                            match(input, RIGHTSHIFT, FOLLOW_RIGHTSHIFT_in_shift_expr3788);
                            
                            tk = t2;
                            
                        }
                            break;
                        
                        }
                        
                        pushFollow(FOLLOW_arith_expr_in_shift_expr3810);
                        v = arith_expr();
                        _fsp--;
                        
                        if (tk == t1)
                            e = new BinaryExpression(e, Expression.E_LSHIFT, v);
                        else
                            e = new BinaryExpression(e, Expression.E_RSHIFT, v);
                        
                    }
                        break;
                    
                    default:
                        break loop63;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end shift_expr
    
    // $ANTLR start arith_expr
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1087:1: arith_expr returns [ Expression e = null ] : e0= term ( (t1= PLUS | t2= MINUS ) v= term )* ;
    public final Expression arith_expr() {
        Expression e = null;
        
        Token t1 = null;
        Token t2 = null;
        Expression e0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1087:44: (e0= term ( (t1= PLUS | t2= MINUS ) v= term )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1088:2: e0= term ( (t1= PLUS | t2= MINUS ) v= term )*
            {
                pushFollow(FOLLOW_term_in_arith_expr3846);
                e0 = term();
                _fsp--;
                
                e = e0;
                Token tk = null;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1093:2: ( (t1= PLUS | t2= MINUS ) v= term )*
                loop65: do {
                    int alt65 = 2;
                    int LA65_0 = input.LA(1);
                    
                    if (((LA65_0 >= PLUS && LA65_0 <= MINUS)))
                        alt65 = 1;
                    
                    switch (alt65) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1094:3: (t1= PLUS | t2= MINUS ) v= term
                    {
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1094:3: (t1= PLUS | t2= MINUS )
                        int alt64 = 2;
                        int LA64_0 = input.LA(1);
                        
                        if ((LA64_0 == PLUS))
                            alt64 = 1;
                        else if ((LA64_0 == MINUS))
                            alt64 = 2;
                        else {
                            NoViableAltException nvae = new NoViableAltException(
                                    "1094:3: (t1= PLUS | t2= MINUS )", 64, 0, input);
                            
                            throw nvae;
                        }
                        switch (alt64) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1095:4: t1= PLUS
                        {
                            t1 = input.LT(1);
                            match(input, PLUS, FOLLOW_PLUS_in_arith_expr3866);
                            
                            tk = t1;
                            
                        }
                            break;
                        case 2:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1100:4: t2= MINUS
                        {
                            t2 = input.LT(1);
                            match(input, MINUS, FOLLOW_MINUS_in_arith_expr3886);
                            
                            tk = t2;
                            
                        }
                            break;
                        
                        }
                        
                        pushFollow(FOLLOW_term_in_arith_expr3905);
                        v = term();
                        _fsp--;
                        
                        if (tk == t1)
                            e = new BinaryExpression(e, Expression.E_PLUS, v);
                        else
                            e = new BinaryExpression(e, Expression.E_MINUS, v);
                        
                    }
                        break;
                    
                    default:
                        break loop65;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end arith_expr
    
    // $ANTLR start term
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1115:1: term returns [ Expression e = null] : e0= factor ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )* ;
    public final Expression term() {
        Expression e = null;
        
        Token t1 = null;
        Token t2 = null;
        Token t3 = null;
        Token t4 = null;
        Expression e0 = null;
        
        Expression v = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1115:36: (e0= factor ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )* )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1116:2: e0= factor ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )*
            {
                pushFollow(FOLLOW_factor_in_term3935);
                e0 = factor();
                _fsp--;
                
                e = e0;
                int type = Expression.E_EMPTY;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1121:2: ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )*
                loop67: do {
                    int alt67 = 2;
                    int LA67_0 = input.LA(1);
                    
                    if ((LA67_0 == STAR || (LA67_0 >= SLASH && LA67_0 <= DOUBLESLASH)))
                        alt67 = 1;
                    
                    switch (alt67) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1122:3: (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor
                    {
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1122:3: (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH )
                        int alt66 = 4;
                        switch (input.LA(1)) {
                        case STAR: {
                            alt66 = 1;
                        }
                            break;
                        case SLASH: {
                            alt66 = 2;
                        }
                            break;
                        case PERCENT: {
                            alt66 = 3;
                        }
                            break;
                        case DOUBLESLASH: {
                            alt66 = 4;
                        }
                            break;
                        default:
                            NoViableAltException nvae = new NoViableAltException(
                                    "1122:3: (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH )", 66, 0,
                                    input);
                            
                            throw nvae;
                        }
                        
                        switch (alt66) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1123:5: t1= STAR
                        {
                            t1 = input.LT(1);
                            match(input, STAR, FOLLOW_STAR_in_term3956);
                            
                            type = Expression.E_MULT;
                            
                        }
                            break;
                        case 2:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1128:5: t2= SLASH
                        {
                            t2 = input.LT(1);
                            match(input, SLASH, FOLLOW_SLASH_in_term3980);
                            
                            type = Expression.E_DIV;
                            
                        }
                            break;
                        case 3:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1133:6: t3= PERCENT
                        {
                            t3 = input.LT(1);
                            match(input, PERCENT, FOLLOW_PERCENT_in_term4005);
                            
                            type = Expression.E_MOD;
                            
                        }
                            break;
                        case 4:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1138:7: t4= DOUBLESLASH
                        {
                            t4 = input.LT(1);
                            match(input, DOUBLESLASH, FOLLOW_DOUBLESLASH_in_term4033);
                            
                            type = Expression.E_DOUBLEDIV;
                            
                        }
                            break;
                        
                        }
                        
                        pushFollow(FOLLOW_factor_in_term4059);
                        v = factor();
                        _fsp--;
                        
                        e = new BinaryExpression(e, type, v);
                        
                    }
                        break;
                    
                    default:
                        break loop67;
                    }
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end term
    
    // $ANTLR start factor
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1150:1: factor returns [ Expression e = new EmptyExpression() ] : ( (t1= PLUS | t2= MINUS | t3= TILDE ) e0= factor | e0= power );
    public final Expression factor() {
        Expression e = new EmptyExpression();
        
        Token t1 = null;
        Token t2 = null;
        Token t3 = null;
        Expression e0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1150:57: ( (t1= PLUS | t2= MINUS | t3= TILDE ) e0= factor | e0= power )
            int alt69 = 2;
            int LA69_0 = input.LA(1);
            
            if (((LA69_0 >= PLUS && LA69_0 <= MINUS) || LA69_0 == TILDE))
                alt69 = 1;
            else if ((LA69_0 == LPAREN || LA69_0 == NAME || LA69_0 == LBRACK || LA69_0 == LCURLY || (LA69_0 >= BACKQUOTE && LA69_0 <= STRING)))
                alt69 = 2;
            else {
                NoViableAltException nvae = new NoViableAltException(
                        "1150:1: factor returns [ Expression e = new EmptyExpression() ] : ( (t1= PLUS | t2= MINUS | t3= TILDE ) e0= factor | e0= power );",
                        69, 0, input);
                
                throw nvae;
            }
            switch (alt69) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1151:4: (t1= PLUS | t2= MINUS | t3= TILDE ) e0= factor
            {
                
                Token tk = null;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1154:4: (t1= PLUS | t2= MINUS | t3= TILDE )
                int alt68 = 3;
                switch (input.LA(1)) {
                case PLUS: {
                    alt68 = 1;
                }
                    break;
                case MINUS: {
                    alt68 = 2;
                }
                    break;
                case TILDE: {
                    alt68 = 3;
                }
                    break;
                default:
                    NoViableAltException nvae = new NoViableAltException(
                            "1154:4: (t1= PLUS | t2= MINUS | t3= TILDE )", 68, 0, input);
                    
                    throw nvae;
                }
                
                switch (alt68) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1155:5: t1= PLUS
                {
                    t1 = input.LT(1);
                    match(input, PLUS, FOLLOW_PLUS_in_factor4105);
                    
                    tk = t1;
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1160:5: t2= MINUS
                {
                    t2 = input.LT(1);
                    match(input, MINUS, FOLLOW_MINUS_in_factor4128);
                    
                    tk = t2;
                    
                }
                    break;
                case 3:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1165:5: t3= TILDE
                {
                    t3 = input.LT(1);
                    match(input, TILDE, FOLLOW_TILDE_in_factor4150);
                    
                    tk = t3;
                    
                }
                    break;
                
                }
                
                pushFollow(FOLLOW_factor_in_factor4172);
                e0 = factor();
                _fsp--;
                
                if (tk == t1)
                    e = new UnaryExpression(toDLTK(tk), Expression.E_PLUS, e0);
                else if (tk == t2)
                    e = new UnaryExpression(toDLTK(tk), Expression.E_MINUS, e0);
                else if (tk == t3)
                    e = new UnaryExpression(toDLTK(tk), Expression.E_TILDE, e0);
                else
                    e = new UnaryExpression(toDLTK(tk), Expression.E_FACTOR, e0);
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1186:2: e0= power
            {
                pushFollow(FOLLOW_power_in_factor4189);
                e0 = power();
                _fsp--;
                
                e = e0;
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end factor
    
    // $ANTLR start power
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1191:1: power returns [ Expression exp = null; ] : (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? ) ;
    public final Expression power() {
        Expression exp = null;
        ;
        
        Expression exp0 = null;
        
        Expression ex = null;
        
        Expression expr0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1191:41: ( (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1192:3: (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1192:3: (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1193:3: exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )?
                {
                    pushFollow(FOLLOW_atom_in_power4216);
                    exp0 = atom();
                    _fsp--;
                    
                    //Expression ex = exp;
                    exp = exp0;
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1198:3: (ex= trailer[ exp ] )*
                    loop70: do {
                        int alt70 = 2;
                        int LA70_0 = input.LA(1);
                        
                        if ((LA70_0 == LPAREN || LA70_0 == DOT || LA70_0 == LBRACK))
                            alt70 = 1;
                        
                        switch (alt70) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1199:4: ex= trailer[ exp ]
                        {
                            pushFollow(FOLLOW_trailer_in_power4234);
                            ex = trailer(exp);
                            _fsp--;
                            
                            exp = ex;
                            
                        }
                            break;
                        
                        default:
                            break loop70;
                        }
                    } while (true);
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1204:5: ( options {greedy=true; } : DOUBLESTAR expr0= factor )?
                    int alt71 = 2;
                    int LA71_0 = input.LA(1);
                    
                    if ((LA71_0 == DOUBLESTAR))
                        alt71 = 1;
                    switch (alt71) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1205:29: DOUBLESTAR expr0= factor
                    {
                        match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_power4267);
                        pushFollow(FOLLOW_factor_in_power4285);
                        expr0 = factor();
                        _fsp--;
                        
                        //TODO: add factor expression.
                        //	  			PythonTestListExpression testListExpr = new PythonTestListExpression();
                        //	  			testListExpr.addExpression( exp );
                        //	  			testListExpr.addExpression( expr );
                        //	  			exp = testListExpr;
                        exp = new BinaryExpression(exp, Expression.E_POWER, expr0);
                        
                    }
                        break;
                    
                    }
                    
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end power
    
    // $ANTLR start trailer
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1220:1: trailer[ Expression expr ] returns [ Expression result = null ] : (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME ) ;
    public final Expression trailer(Expression expr) {
        Expression result = null;
        
        Token lp0 = null;
        Token rp0 = null;
        Token lb1 = null;
        Token rb1 = null;
        Token ta = null;
        PythonCallArgumentsList args = null;
        
        Expression k = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1220:64: ( (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1221:2: (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME )
            {
                
                //Expression k=null;
                // Create extended variable reference.
                //if( !( expr instanceof ExtendedVariableReference ) )
                //	expr = new ExtendedVariableReference( expr );
                //ExtendedVariableReference exVariableReference = ( ExtendedVariableReference )expr;
                result = expr;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1229:2: (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME )
                int alt72 = 3;
                switch (input.LA(1)) {
                case LPAREN: {
                    alt72 = 1;
                }
                    break;
                case LBRACK: {
                    alt72 = 2;
                }
                    break;
                case DOT: {
                    alt72 = 3;
                }
                    break;
                default:
                    NoViableAltException nvae = new NoViableAltException(
                            "1229:2: (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME )",
                            72, 0, input);
                    
                    throw nvae;
                }
                
                switch (alt72) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1230:3: lp0= LPAREN args= arglist rp0= RPAREN
                {
                    lp0 = input.LT(1);
                    match(input, LPAREN, FOLLOW_LPAREN_in_trailer4333);
                    args = new PythonCallArgumentsList();
                    pushFollow(FOLLOW_arglist_in_trailer4348);
                    args = arglist();
                    _fsp--;
                    
                    rp0 = input.LT(1);
                    match(input, RPAREN, FOLLOW_RPAREN_in_trailer4356);
                    
                    result = new PythonCallExpression(result, args);
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1238:3: lb1= LBRACK k= subscriptlist rb1= RBRACK
                {
                    lb1 = input.LT(1);
                    match(input, LBRACK, FOLLOW_LBRACK_in_trailer4374);
                    pushFollow(FOLLOW_subscriptlist_in_trailer4384);
                    k = subscriptlist();
                    _fsp--;
                    
                    rb1 = input.LT(1);
                    match(input, RBRACK, FOLLOW_RBRACK_in_trailer4393);
                    
                    result = new PythonArrayAccessExpression(result, k);
                    
                }
                    break;
                case 3:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1245:3: DOT ta= NAME
                {
                    match(input, DOT, FOLLOW_DOT_in_trailer4408);
                    ta = input.LT(1);
                    match(input, NAME, FOLLOW_NAME_in_trailer4416);
                    
                    result = new PythonVariableAccessExpression(result, new VariableReference(toDLTK(ta)));
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return result;
    }
    
    // $ANTLR end trailer
    
    // $ANTLR start atom
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1253:1: atom returns [ Expression exp = null ] : (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ );
    public final Expression atom() {
        Expression exp = null;
        
        Token lb = null;
        Token rb = null;
        Token a1 = null;
        Token n = null;
        Token i = null;
        Token f = null;
        Token c = null;
        Token s = null;
        Expression exp0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1253:39: (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ )
            int alt74 = 12;
            switch (input.LA(1)) {
            case LPAREN: {
                int LA74_1 = input.LA(2);
                
                if ((LA74_1 == RPAREN))
                    alt74 = 2;
                else if ((LA74_1 == LPAREN || LA74_1 == NAME || (LA74_1 >= PLUS && LA74_1 <= MINUS)
                        || (LA74_1 >= TILDE && LA74_1 <= LBRACK) || LA74_1 == LCURLY
                        || (LA74_1 >= BACKQUOTE && LA74_1 <= STRING) || LA74_1 == 95 || LA74_1 == 97))
                    alt74 = 1;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "1253:1: atom returns [ Expression exp = null ] : (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ );",
                            74, 1, input);
                    
                    throw nvae;
                }
            }
                break;
            case LBRACK: {
                int LA74_2 = input.LA(2);
                
                if ((LA74_2 == RBRACK))
                    alt74 = 4;
                else if ((LA74_2 == LPAREN || LA74_2 == NAME || (LA74_2 >= PLUS && LA74_2 <= MINUS)
                        || (LA74_2 >= TILDE && LA74_2 <= LBRACK) || LA74_2 == LCURLY
                        || (LA74_2 >= BACKQUOTE && LA74_2 <= STRING) || LA74_2 == 95 || LA74_2 == 97))
                    alt74 = 3;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "1253:1: atom returns [ Expression exp = null ] : (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ );",
                            74, 2, input);
                    
                    throw nvae;
                }
            }
                break;
            case LCURLY: {
                int LA74_3 = input.LA(2);
                
                if ((LA74_3 == RCURLY))
                    alt74 = 6;
                else if ((LA74_3 == LPAREN || LA74_3 == NAME || (LA74_3 >= PLUS && LA74_3 <= MINUS)
                        || (LA74_3 >= TILDE && LA74_3 <= LBRACK) || LA74_3 == LCURLY
                        || (LA74_3 >= BACKQUOTE && LA74_3 <= STRING) || LA74_3 == 95 || LA74_3 == 97))
                    alt74 = 5;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "1253:1: atom returns [ Expression exp = null ] : (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ );",
                            74, 3, input);
                    
                    throw nvae;
                }
            }
                break;
            case BACKQUOTE: {
                alt74 = 7;
            }
                break;
            case NAME: {
                alt74 = 8;
            }
                break;
            case INT: {
                alt74 = 9;
            }
                break;
            case FLOAT: {
                alt74 = 10;
            }
                break;
            case COMPLEX: {
                alt74 = 11;
            }
                break;
            case STRING: {
                alt74 = 12;
            }
                break;
            default:
                NoViableAltException nvae = new NoViableAltException(
                        "1253:1: atom returns [ Expression exp = null ] : (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ );",
                        74, 0, input);
                
                throw nvae;
            }
            
            switch (alt74) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1254:4: lb= LPAREN (exp0= tuplelist ) rb= RPAREN
            {
                lb = input.LT(1);
                match(input, LPAREN, FOLLOW_LPAREN_in_atom4446);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1254:16: (exp0= tuplelist )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1254:18: exp0= tuplelist
                {
                    pushFollow(FOLLOW_tuplelist_in_atom4454);
                    exp0 = tuplelist();
                    _fsp--;
                    
                    exp = exp0;
                    
                }
                
                rb = input.LT(1);
                match(input, RPAREN, FOLLOW_RPAREN_in_atom4464);
                setStartEndForEmbracedExpr(exp, lb, rb);
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1255:4: lb= LPAREN rb= RPAREN
            {
                lb = input.LT(1);
                match(input, LPAREN, FOLLOW_LPAREN_in_atom4475);
                exp = new PythonTupleExpression();
                rb = input.LT(1);
                match(input, RPAREN, FOLLOW_RPAREN_in_atom4483);
                setStartEndForEmbracedExpr(exp, lb, rb);
                
            }
                break;
            case 3:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1256:4: lb= LBRACK (exp0= listmaker ) rb= RBRACK
            {
                lb = input.LT(1);
                match(input, LBRACK, FOLLOW_LBRACK_in_atom4495);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1256:16: (exp0= listmaker )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1256:18: exp0= listmaker
                {
                    pushFollow(FOLLOW_listmaker_in_atom4503);
                    exp0 = listmaker();
                    _fsp--;
                    
                    exp = exp0;
                    
                }
                
                rb = input.LT(1);
                match(input, RBRACK, FOLLOW_RBRACK_in_atom4513);
                setStartEndForEmbracedExpr(exp, lb, rb);
                
            }
                break;
            case 4:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1257:4: lb= LBRACK rb= RBRACK
            {
                lb = input.LT(1);
                match(input, LBRACK, FOLLOW_LBRACK_in_atom4523);
                exp = new PythonListExpression();
                rb = input.LT(1);
                match(input, RBRACK, FOLLOW_RBRACK_in_atom4531);
                setStartEndForEmbracedExpr(exp, lb, rb);
                
            }
                break;
            case 5:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1258:4: a1= LCURLY (exp0= dictmaker ) rb= RCURLY
            {
                a1 = input.LT(1);
                match(input, LCURLY, FOLLOW_LCURLY_in_atom4542);
                lb = a1;
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1258:24: (exp0= dictmaker )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1258:26: exp0= dictmaker
                {
                    pushFollow(FOLLOW_dictmaker_in_atom4552);
                    exp0 = dictmaker();
                    _fsp--;
                    
                    exp = exp0;
                    
                }
                
                rb = input.LT(1);
                match(input, RCURLY, FOLLOW_RCURLY_in_atom4562);
                setStartEndForEmbracedExpr(exp, lb, rb);
                
            }
                break;
            case 6:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1259:4: lb= LCURLY rb= RCURLY
            {
                lb = input.LT(1);
                match(input, LCURLY, FOLLOW_LCURLY_in_atom4572);
                exp = new PythonDictExpression();
                rb = input.LT(1);
                match(input, RCURLY, FOLLOW_RCURLY_in_atom4580);
                setStartEndForEmbracedExpr(exp, lb, rb);
                
            }
                break;
            case 7:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1260:4: lb= BACKQUOTE exp0= testlist rb= BACKQUOTE
            {
                lb = input.LT(1);
                match(input, BACKQUOTE, FOLLOW_BACKQUOTE_in_atom4593);
                pushFollow(FOLLOW_testlist_in_atom4599);
                exp0 = testlist();
                _fsp--;
                
                exp = exp0;
                rb = input.LT(1);
                match(input, BACKQUOTE, FOLLOW_BACKQUOTE_in_atom4608);
                setStartEndForEmbracedExpr(exp, lb, rb);
                
            }
                break;
            case 8:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1261:4: n= NAME
            {
                n = input.LT(1);
                match(input, NAME, FOLLOW_NAME_in_atom4618);
                exp = new VariableReference(toDLTK(n));
                
            }
                break;
            case 9:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1262:4: i= INT
            {
                i = input.LT(1);
                match(input, INT, FOLLOW_INT_in_atom4630);
                exp = Literal.createNumericLiteral(toDLTK(i));
                
            }
                break;
            case 10:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1263:8: f= FLOAT
            {
                f = input.LT(1);
                match(input, FLOAT, FOLLOW_FLOAT_in_atom4647);
                exp = new FloatNumericLiteral(toDLTK(f));
                
            }
                break;
            case 11:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1264:8: c= COMPLEX
            {
                c = input.LT(1);
                match(input, COMPLEX, FOLLOW_COMPLEX_in_atom4665);
                exp = new ComplexNumericLiteral(toDLTK(c));
                
            }
                break;
            case 12:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1266:2: (s= STRING )+
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1266:2: (s= STRING )+
                int cnt73 = 0;
                loop73: do {
                    int alt73 = 2;
                    int LA73_0 = input.LA(1);
                    
                    if ((LA73_0 == STRING))
                        alt73 = 1;
                    
                    switch (alt73) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1267:3: s= STRING
                    {
                        s = input.LT(1);
                        match(input, STRING, FOLLOW_STRING_in_atom4682);
                        
                        if (exp != null && exp instanceof StringLiteral)
                            exp = new StringLiteral(exp.sourceStart(), toDLTK(s), ((StringLiteral) exp)
                                    .getValue()
                                    + s.getText());
                        else
                            exp = new StringLiteral(toDLTK(s));
                        
                    }
                        break;
                    
                    default:
                        if (cnt73 >= 1)
                            break loop73;
                        EarlyExitException eee = new EarlyExitException(73, input);
                        throw eee;
                    }
                    cnt73++;
                } while (true);
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end atom
    
    // $ANTLR start listmaker
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1277:1: listmaker returns [ PythonListExpression exp = new PythonListExpression() ] : firstExpr= test ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) ) ;
    public final PythonListExpression listmaker() {
        PythonListExpression exp = new PythonListExpression();
        
        Expression firstExpr = null;
        
        Expression expr0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1277:76: (firstExpr= test ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1278:2: firstExpr= test ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) )
            {
                pushFollow(FOLLOW_test_in_listmaker4712);
                firstExpr = test();
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1279:2: ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) )
                int alt77 = 2;
                int LA77_0 = input.LA(1);
                
                if ((LA77_0 == 89))
                    alt77 = 1;
                else if ((LA77_0 == COMMA || LA77_0 == RBRACK))
                    alt77 = 2;
                else {
                    NoViableAltException nvae = new NoViableAltException(
                            "1279:2: ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) )",
                            77, 0, input);
                    
                    throw nvae;
                }
                switch (alt77) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1280:3: ( list_for[ listExpr ] )
                {
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1280:3: ( list_for[ listExpr ] )
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1281:4: list_for[ listExpr ]
                    {
                        
                        PythonListForExpression listExpr = new PythonListForExpression(firstExpr);
                        
                        pushFollow(FOLLOW_list_for_in_listmaker4729);
                        list_for(listExpr);
                        _fsp--;
                        
                        exp.addExpression(listExpr);
                        
                    }
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1290:3: ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? )
                {
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1290:3: ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? )
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1291:4: ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )?
                    {
                        
                        exp.addExpression(firstExpr);
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1294:4: ( options {greedy=true; } : COMMA expr0= test )*
                        loop75: do {
                            int alt75 = 2;
                            int LA75_0 = input.LA(1);
                            
                            if ((LA75_0 == COMMA)) {
                                int LA75_1 = input.LA(2);
                                
                                if ((LA75_1 == LPAREN || LA75_1 == NAME
                                        || (LA75_1 >= PLUS && LA75_1 <= MINUS)
                                        || (LA75_1 >= TILDE && LA75_1 <= LBRACK) || LA75_1 == LCURLY
                                        || (LA75_1 >= BACKQUOTE && LA75_1 <= STRING) || LA75_1 == 95 || LA75_1 == 97))
                                    alt75 = 1;
                                
                            }
                            
                            switch (alt75) {
                            case 1:
                                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1296:5: COMMA expr0= test
                            {
                                match(input, COMMA, FOLLOW_COMMA_in_listmaker4777);
                                pushFollow(FOLLOW_test_in_listmaker4787);
                                expr0 = test();
                                _fsp--;
                                
                                exp.addExpression(expr0);
                                
                            }
                                break;
                            
                            default:
                                break loop75;
                            }
                        } while (true);
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1302:4: ( COMMA )?
                        int alt76 = 2;
                        int LA76_0 = input.LA(1);
                        
                        if ((LA76_0 == COMMA))
                            alt76 = 1;
                        switch (alt76) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1302:5: COMMA
                        {
                            match(input, COMMA, FOLLOW_COMMA_in_listmaker4806);
                            
                        }
                            break;
                        
                        }
                        
                    }
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return exp;
    }
    
    // $ANTLR end listmaker
    
    // $ANTLR start list_for
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1308:1: list_for[PythonListForExpression list ] : (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+ ;
    public final void list_for(PythonListForExpression list) {
        Token forToken = null;
        PythonTestListExpression expr1 = null;
        
        Expression expr2 = null;
        
        Expression expr0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1308:41: ( (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+ )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1309:2: (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1309:2: (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+
                int cnt79 = 0;
                loop79: do {
                    int alt79 = 2;
                    int LA79_0 = input.LA(1);
                    
                    if ((LA79_0 == 89))
                        alt79 = 1;
                    
                    switch (alt79) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1310:3: forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )*
                    {
                        forToken = input.LT(1);
                        match(input, 89, FOLLOW_89_in_list_for4837);
                        pushFollow(FOLLOW_exprlist_in_list_for4845);
                        expr1 = exprlist();
                        _fsp--;
                        
                        match(input, 83, FOLLOW_83_in_list_for4849);
                        pushFollow(FOLLOW_testlist_in_list_for4858);
                        expr2 = testlist();
                        _fsp--;
                        
                        PythonForListExpression forListExpression = new PythonForListExpression(expr1, expr2);
                        forListExpression.setStart(toDLTK(forToken).getColumn());
                        list.addExpression(forListExpression);
                        
                        PythonListExpression ifList = null;
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1322:3: (expr0= list_if )*
                        loop78: do {
                            int alt78 = 2;
                            int LA78_0 = input.LA(1);
                            
                            if ((LA78_0 == 85))
                                alt78 = 1;
                            
                            switch (alt78) {
                            case 1:
                                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1323:4: expr0= list_if
                            {
                                pushFollow(FOLLOW_list_if_in_list_for4881);
                                expr0 = list_if();
                                _fsp--;
                                
                                if (ifList == null)
                                    ifList = new PythonListExpression();
                                ifList.addExpression(expr0);
                                ifList.setStart(expr0.sourceStart());
                                ifList.setEnd(expr0.sourceEnd());
                                
                            }
                                break;
                            
                            default:
                                break loop78;
                            }
                        } while (true);
                        
                        if (ifList != null)
                            forListExpression.setIfListExpression(ifList);
                        
                    }
                        break;
                    
                    default:
                        if (cnt79 >= 1)
                            break loop79;
                        EarlyExitException eee = new EarlyExitException(79, input);
                        throw eee;
                    }
                    cnt79++;
                } while (true);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return;
    }
    
    // $ANTLR end list_for
    
    // $ANTLR start list_if
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1339:1: list_if returns [ Expression expr = null ] : 'if' expr0= test ;
    public final Expression list_if() {
        Expression expr = null;
        
        Expression expr0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1339:42: ( 'if' expr0= test )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1340:2: 'if' expr0= test
            {
                match(input, 85, FOLLOW_85_in_list_if4914);
                pushFollow(FOLLOW_test_in_list_if4921);
                expr0 = test();
                _fsp--;
                
                expr = expr0;
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return expr;
    }
    
    // $ANTLR end list_if
    
    // $ANTLR start lambdef
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1345:1: lambdef returns [ Expression e = null ] : lambda= 'lambda' ( varargslist[ params ] )? COLON statement= test ;
    public final Expression lambdef() {
        Expression e = null;
        
        Token lambda = null;
        Expression statement = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1345:40: (lambda= 'lambda' ( varargslist[ params ] )? COLON statement= test )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1346:2: lambda= 'lambda' ( varargslist[ params ] )? COLON statement= test
            {
                lambda = input.LT(1);
                match(input, 97, FOLLOW_97_in_lambdef4942);
                
                //ArrayList buf = this.fParameters;
                //this.fParameters = new ArrayList();
                ArgumentList params = new ArgumentList();
                //Expression statement;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1353:2: ( varargslist[ params ] )?
                int alt80 = 2;
                int LA80_0 = input.LA(1);
                
                if ((LA80_0 == LPAREN || LA80_0 == NAME || (LA80_0 >= STAR && LA80_0 <= DOUBLESTAR)))
                    alt80 = 1;
                switch (alt80) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1353:4: varargslist[ params ]
                {
                    pushFollow(FOLLOW_varargslist_in_lambdef4950);
                    varargslist(params);
                    _fsp--;
                    
                }
                    break;
                
                }
                
                match(input, COLON, FOLLOW_COLON_in_lambdef4957);
                pushFollow(FOLLOW_test_in_lambdef4965);
                statement = test();
                _fsp--;
                
                if (statement == null)
                    statement = new EmptyExpression();
                e = new PythonLambdaExpression(toDLTK(lambda), params, statement);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end lambdef
    
    // $ANTLR start subscriptlist
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1364:1: subscriptlist returns [ Expression e = null ] : e0= subscript ( options {greedy=true; } : COMMA k= subscript )* ( COMMA )? ;
    public final Expression subscriptlist() {
        Expression e = null;
        
        PythonSubscriptExpression e0 = null;
        
        PythonSubscriptExpression k = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1364:47: (e0= subscript ( options {greedy=true; } : COMMA k= subscript )* ( COMMA )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1365:2: e0= subscript ( options {greedy=true; } : COMMA k= subscript )* ( COMMA )?
            {
                pushFollow(FOLLOW_subscript_in_subscriptlist4989);
                e0 = subscript();
                _fsp--;
                
                e = e0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1369:2: ( options {greedy=true; } : COMMA k= subscript )*
                loop81: do {
                    int alt81 = 2;
                    int LA81_0 = input.LA(1);
                    
                    if ((LA81_0 == COMMA)) {
                        int LA81_1 = input.LA(2);
                        
                        if ((LA81_1 == LPAREN || (LA81_1 >= NAME && LA81_1 <= COLON) || LA81_1 == DOT
                                || (LA81_1 >= PLUS && LA81_1 <= MINUS)
                                || (LA81_1 >= TILDE && LA81_1 <= LBRACK) || LA81_1 == LCURLY
                                || (LA81_1 >= BACKQUOTE && LA81_1 <= STRING) || LA81_1 == 95 || LA81_1 == 97))
                            alt81 = 1;
                        
                    }
                    
                    switch (alt81) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1370:26: COMMA k= subscript
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_subscriptlist5008);
                        pushFollow(FOLLOW_subscript_in_subscriptlist5017);
                        k = subscript();
                        _fsp--;
                        
                        e = new BinaryExpression(e, Expression.E_COMMA, k);
                        
                    }
                        break;
                    
                    default:
                        break loop81;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1376:2: ( COMMA )?
                int alt82 = 2;
                int LA82_0 = input.LA(1);
                
                if ((LA82_0 == COMMA))
                    alt82 = 1;
                switch (alt82) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1376:3: COMMA
                {
                    match(input, COMMA, FOLLOW_COMMA_in_subscriptlist5032);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end subscriptlist
    
    // $ANTLR start subscript
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1379:1: subscript returns [ PythonSubscriptExpression expression = null ] : (w= DOT DOT DOT | (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? ) | ( COLON (tu1= test )? (tu= sliceop )? ) );
    public final PythonSubscriptExpression subscript() {
        PythonSubscriptExpression expression = null;
        
        Token w = null;
        Expression tu = null;
        
        Expression tu1 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1379:66: (w= DOT DOT DOT | (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? ) | ( COLON (tu1= test )? (tu= sliceop )? ) )
            int alt88 = 3;
            switch (input.LA(1)) {
            case DOT: {
                alt88 = 1;
            }
                break;
            case LPAREN:
            case NAME:
            case PLUS:
            case MINUS:
            case TILDE:
            case LBRACK:
            case LCURLY:
            case BACKQUOTE:
            case INT:
            case FLOAT:
            case COMPLEX:
            case STRING:
            case 95:
            case 97: {
                alt88 = 2;
            }
                break;
            case COLON: {
                alt88 = 3;
            }
                break;
            default:
                NoViableAltException nvae = new NoViableAltException(
                        "1379:1: subscript returns [ PythonSubscriptExpression expression = null ] : (w= DOT DOT DOT | (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? ) | ( COLON (tu1= test )? (tu= sliceop )? ) );",
                        88, 0, input);
                
                throw nvae;
            }
            
            switch (alt88) {
            case 1:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1380:2: w= DOT DOT DOT
            {
                w = input.LT(1);
                match(input, DOT, FOLLOW_DOT_in_subscript5056);
                match(input, DOT, FOLLOW_DOT_in_subscript5058);
                match(input, DOT, FOLLOW_DOT_in_subscript5060);
                
                expression = new PythonSubscriptExpression(toDLTK(w));
                
            }
                break;
            case 2:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1386:2: (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1386:2: (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1387:3: tu= test ( COLON (tu1= test )? (tu= sliceop )? )?
                {
                    
                    expression = new PythonSubscriptExpression();
                    
                    pushFollow(FOLLOW_test_in_subscript5085);
                    tu = test();
                    _fsp--;
                    
                    expression.setTest(tu);
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1394:3: ( COLON (tu1= test )? (tu= sliceop )? )?
                    int alt85 = 2;
                    int LA85_0 = input.LA(1);
                    
                    if ((LA85_0 == COLON))
                        alt85 = 1;
                    switch (alt85) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1395:4: COLON (tu1= test )? (tu= sliceop )?
                    {
                        match(input, COLON, FOLLOW_COLON_in_subscript5100);
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1396:4: (tu1= test )?
                        int alt83 = 2;
                        int LA83_0 = input.LA(1);
                        
                        if ((LA83_0 == LPAREN || LA83_0 == NAME || (LA83_0 >= PLUS && LA83_0 <= MINUS)
                                || (LA83_0 >= TILDE && LA83_0 <= LBRACK) || LA83_0 == LCURLY
                                || (LA83_0 >= BACKQUOTE && LA83_0 <= STRING) || LA83_0 == 95 || LA83_0 == 97))
                            alt83 = 1;
                        switch (alt83) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1397:5: tu1= test
                        {
                            pushFollow(FOLLOW_test_in_subscript5116);
                            tu1 = test();
                            _fsp--;
                            
                            expression.setCondition(tu1);
                            
                        }
                            break;
                        
                        }
                        
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1402:4: (tu= sliceop )?
                        int alt84 = 2;
                        int LA84_0 = input.LA(1);
                        
                        if ((LA84_0 == COLON))
                            alt84 = 1;
                        switch (alt84) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1403:5: tu= sliceop
                        {
                            pushFollow(FOLLOW_sliceop_in_subscript5146);
                            tu = sliceop();
                            _fsp--;
                            
                            expression.setSlice(tu);
                            
                        }
                            break;
                        
                        }
                        
                    }
                        break;
                    
                    }
                    
                }
                
            }
                break;
            case 3:
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1411:2: ( COLON (tu1= test )? (tu= sliceop )? )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1411:2: ( COLON (tu1= test )? (tu= sliceop )? )
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1412:3: COLON (tu1= test )? (tu= sliceop )?
                {
                    
                    expression = new PythonSubscriptExpression();
                    
                    match(input, COLON, FOLLOW_COLON_in_subscript5188);
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1416:8: (tu1= test )?
                    int alt86 = 2;
                    int LA86_0 = input.LA(1);
                    
                    if ((LA86_0 == LPAREN || LA86_0 == NAME || (LA86_0 >= PLUS && LA86_0 <= MINUS)
                            || (LA86_0 >= TILDE && LA86_0 <= LBRACK) || LA86_0 == LCURLY
                            || (LA86_0 >= BACKQUOTE && LA86_0 <= STRING) || LA86_0 == 95 || LA86_0 == 97))
                        alt86 = 1;
                    switch (alt86) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1417:9: tu1= test
                    {
                        pushFollow(FOLLOW_test_in_subscript5212);
                        tu1 = test();
                        _fsp--;
                        
                        expression.setCondition(tu1);
                        
                    }
                        break;
                    
                    }
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1422:8: (tu= sliceop )?
                    int alt87 = 2;
                    int LA87_0 = input.LA(1);
                    
                    if ((LA87_0 == COLON))
                        alt87 = 1;
                    switch (alt87) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1423:9: tu= sliceop
                    {
                        pushFollow(FOLLOW_sliceop_in_subscript5258);
                        tu = sliceop();
                        _fsp--;
                        
                        expression.setSlice(tu);
                        
                    }
                        break;
                    
                    }
                    
                }
                
            }
                break;
            
            }
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return expression;
    }
    
    // $ANTLR end subscript
    
    // $ANTLR start sliceop
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1433:1: sliceop returns [ Expression e = null ] : COLON (e0= test )? ;
    public final Expression sliceop() {
        Expression e = null;
        
        Expression e0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1433:40: ( COLON (e0= test )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1434:2: COLON (e0= test )?
            {
                match(input, COLON, FOLLOW_COLON_in_sliceop5318);
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1435:2: (e0= test )?
                int alt89 = 2;
                int LA89_0 = input.LA(1);
                
                if ((LA89_0 == LPAREN || LA89_0 == NAME || (LA89_0 >= PLUS && LA89_0 <= MINUS)
                        || (LA89_0 >= TILDE && LA89_0 <= LBRACK) || LA89_0 == LCURLY
                        || (LA89_0 >= BACKQUOTE && LA89_0 <= STRING) || LA89_0 == 95 || LA89_0 == 97))
                    alt89 = 1;
                switch (alt89) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1435:4: e0= test
                {
                    pushFollow(FOLLOW_test_in_sliceop5327);
                    e0 = test();
                    _fsp--;
                    
                    e = e0;
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end sliceop
    
    // $ANTLR start exprlist
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1439:1: exprlist returns [ PythonTestListExpression p = new PythonTestListExpression( ); ] : e= expr ( options {k=2; greedy=true; } : COMMA e= expr )* ( COMMA )? ;
    public final PythonTestListExpression exprlist() {
        PythonTestListExpression p = new PythonTestListExpression();
        ;
        
        Expression e = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1439:84: (e= expr ( options {k=2; greedy=true; } : COMMA e= expr )* ( COMMA )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1440:2: e= expr ( options {k=2; greedy=true; } : COMMA e= expr )* ( COMMA )?
            {
                pushFollow(FOLLOW_expr_in_exprlist5354);
                e = expr();
                _fsp--;
                
                p.addExpression(e);
                p.setStart(e.sourceStart());
                p.setEnd(e.sourceEnd());
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1446:2: ( options {k=2; greedy=true; } : COMMA e= expr )*
                loop90: do {
                    int alt90 = 2;
                    int LA90_0 = input.LA(1);
                    
                    if ((LA90_0 == COMMA)) {
                        int LA90_1 = input.LA(2);
                        
                        if ((LA90_1 == LPAREN || LA90_1 == NAME || (LA90_1 >= PLUS && LA90_1 <= MINUS)
                                || (LA90_1 >= TILDE && LA90_1 <= LBRACK) || LA90_1 == LCURLY || (LA90_1 >= BACKQUOTE && LA90_1 <= STRING)))
                            alt90 = 1;
                        
                    }
                    
                    switch (alt90) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1447:3: COMMA e= expr
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_exprlist5376);
                        pushFollow(FOLLOW_expr_in_exprlist5385);
                        e = expr();
                        _fsp--;
                        
                        p.addExpression(e);
                        if (e.sourceEnd() > p.sourceEnd())
                            p.setEnd(e.sourceEnd());
                        
                    }
                        break;
                    
                    default:
                        break loop90;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1456:2: ( COMMA )?
                int alt91 = 2;
                int LA91_0 = input.LA(1);
                
                if ((LA91_0 == COMMA))
                    alt91 = 1;
                switch (alt91) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1456:3: COMMA
                {
                    match(input, COMMA, FOLLOW_COMMA_in_exprlist5399);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return p;
    }
    
    // $ANTLR end exprlist
    
    // $ANTLR start testlist
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1460:1: testlist returns [ Expression p = new EmptyExpression() ] : e0= test ( options {k=2; greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? ;
    public final Expression testlist() {
        Expression p = new EmptyExpression();
        
        Expression e0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1460:58: (e0= test ( options {k=2; greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1461:2: e0= test ( options {k=2; greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )?
            {
                
                PythonTestListExpression listExpression = new PythonTestListExpression();
                int end = -1;
                
                pushFollow(FOLLOW_test_in_testlist5426);
                e0 = test();
                _fsp--;
                
                p = e0;
                listExpression.addExpression(e0);
                if (p != null) {
                    listExpression.setStart(p.sourceStart());
                    end = p.sourceEnd();
                    listExpression.setEnd(end);
                }
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1475:6: ( options {k=2; greedy=true; } : COMMA e0= test )*
                loop92: do {
                    int alt92 = 2;
                    int LA92_0 = input.LA(1);
                    
                    if ((LA92_0 == COMMA)) {
                        int LA92_1 = input.LA(2);
                        
                        if ((LA92_1 == BACKQUOTE))
                            alt92 = 1;
                        else if ((LA92_1 == LPAREN || LA92_1 == NAME || (LA92_1 >= PLUS && LA92_1 <= MINUS)
                                || (LA92_1 >= TILDE && LA92_1 <= LBRACK) || LA92_1 == LCURLY
                                || (LA92_1 >= INT && LA92_1 <= STRING) || LA92_1 == 95 || LA92_1 == 97))
                            alt92 = 1;
                        
                    }
                    
                    switch (alt92) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1476:7: COMMA e0= test
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_testlist5455);
                        pushFollow(FOLLOW_test_in_testlist5467);
                        e0 = test();
                        _fsp--;
                        
                        if (e0 != null && e0.sourceEnd() > end)
                            end = e0.sourceEnd();
                        listExpression.addExpression(e0);
                        p = listExpression;
                        
                    }
                        break;
                    
                    default:
                        break loop92;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1486:9: ( options {greedy=true; } : COMMA )?
                int alt93 = 2;
                int LA93_0 = input.LA(1);
                
                if ((LA93_0 == COMMA))
                    alt93 = 1;
                switch (alt93) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1486:33: COMMA
                {
                    match(input, COMMA, FOLLOW_COMMA_in_testlist5502);
                    
                }
                    break;
                
                }
                
                if (end != -1 && p != null)
                    p.setEnd(end);
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return p;
    }
    
    // $ANTLR end testlist
    
    // $ANTLR start tuplelist
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1493:1: tuplelist returns [ Expression p = null ] : e0= test ( options {greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? ;
    public final Expression tuplelist() {
        Expression p = null;
        
        Expression e0 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1493:42: (e0= test ( options {greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1494:2: e0= test ( options {greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )?
            {
                pushFollow(FOLLOW_test_in_tuplelist5536);
                e0 = test();
                _fsp--;
                
                p = e0;
                if (p == null)
                    p = new EmptyExpression();
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1502:6: ( options {greedy=true; } : COMMA e0= test )*
                loop94: do {
                    int alt94 = 2;
                    int LA94_0 = input.LA(1);
                    
                    if ((LA94_0 == COMMA)) {
                        int LA94_1 = input.LA(2);
                        
                        if ((LA94_1 == LPAREN || LA94_1 == NAME || (LA94_1 >= PLUS && LA94_1 <= MINUS)
                                || (LA94_1 >= TILDE && LA94_1 <= LBRACK) || LA94_1 == LCURLY
                                || (LA94_1 >= BACKQUOTE && LA94_1 <= STRING) || LA94_1 == 95 || LA94_1 == 97))
                            alt94 = 1;
                        
                    }
                    
                    switch (alt94) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1503:7: COMMA e0= test
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_tuplelist5561);
                        pushFollow(FOLLOW_test_in_tuplelist5573);
                        e0 = test();
                        _fsp--;
                        
                        if (!(p instanceof PythonTupleExpression)) {
                            PythonTupleExpression tuple = new PythonTupleExpression();
                            tuple.addExpression(p);
                            p = tuple;
                        }
                        PythonTupleExpression tup = (PythonTupleExpression) p;
                        tup.addExpression(e0);
                        
                    }
                        break;
                    
                    default:
                        break loop94;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1515:9: ( options {greedy=true; } : COMMA )?
                int alt95 = 2;
                int LA95_0 = input.LA(1);
                
                if ((LA95_0 == COMMA))
                    alt95 = 1;
                switch (alt95) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1515:33: COMMA
                {
                    match(input, COMMA, FOLLOW_COMMA_in_tuplelist5609);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return p;
    }
    
    // $ANTLR end tuplelist
    
    // $ANTLR start with_stmt
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1517:1: with_stmt returns [Statement st = null] : w_token= 'with' exp_what= test ( 'as' exp_as= testlist )? COLON block= suite ;
    public final Statement with_stmt() {
        Statement st = null;
        
        Token w_token = null;
        Expression exp_what = null;
        
        Expression exp_as = null;
        
        Block block = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1517:40: (w_token= 'with' exp_what= test ( 'as' exp_as= testlist )? COLON block= suite )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1518:2: w_token= 'with' exp_what= test ( 'as' exp_as= testlist )? COLON block= suite
            {
                w_token = input.LT(1);
                match(input, 98, FOLLOW_98_in_with_stmt5641);
                pushFollow(FOLLOW_test_in_with_stmt5649);
                exp_what = test();
                _fsp--;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1520:2: ( 'as' exp_as= testlist )?
                int alt96 = 2;
                int LA96_0 = input.LA(1);
                
                if ((LA96_0 == 80))
                    alt96 = 1;
                switch (alt96) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1521:4: 'as' exp_as= testlist
                {
                    match(input, 80, FOLLOW_80_in_with_stmt5657);
                    pushFollow(FOLLOW_testlist_in_with_stmt5663);
                    exp_as = testlist();
                    _fsp--;
                    
                }
                    break;
                
                }
                
                match(input, COLON, FOLLOW_COLON_in_with_stmt5669);
                pushFollow(FOLLOW_suite_in_with_stmt5675);
                block = suite();
                _fsp--;
                
                DLTKToken token = toDLTK(w_token);
                st = new PythonWithStatement(token, exp_what, exp_as, block, token.getColumn(), block
                        .sourceEnd());
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return st;
    }
    
    // $ANTLR end with_stmt
    
    // $ANTLR start dictmaker
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1529:1: dictmaker returns [ PythonDictExpression d = new PythonDictExpression() ] : t1= test COLON t2= test ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )* ( COMMA )? ;
    public final PythonDictExpression dictmaker() {
        PythonDictExpression d = new PythonDictExpression();
        
        Expression t1 = null;
        
        Expression t2 = null;
        
        Expression t3 = null;
        
        Expression t4 = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1529:75: (t1= test COLON t2= test ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )* ( COMMA )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1530:2: t1= test COLON t2= test ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )* ( COMMA )?
            {
                pushFollow(FOLLOW_test_in_dictmaker5697);
                t1 = test();
                _fsp--;
                
                match(input, COLON, FOLLOW_COLON_in_dictmaker5700);
                pushFollow(FOLLOW_test_in_dictmaker5707);
                t2 = test();
                _fsp--;
                
                d.putExpression(t1, t2);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1536:9: ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )*
                loop97: do {
                    int alt97 = 2;
                    int LA97_0 = input.LA(1);
                    
                    if ((LA97_0 == COMMA)) {
                        int LA97_1 = input.LA(2);
                        
                        if ((LA97_1 == LPAREN || LA97_1 == NAME || (LA97_1 >= PLUS && LA97_1 <= MINUS)
                                || (LA97_1 >= TILDE && LA97_1 <= LBRACK) || LA97_1 == LCURLY
                                || (LA97_1 >= BACKQUOTE && LA97_1 <= STRING) || LA97_1 == 95 || LA97_1 == 97))
                            alt97 = 1;
                        
                    }
                    
                    switch (alt97) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1537:37: COMMA t3= test COLON t4= test
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_dictmaker5743);
                        pushFollow(FOLLOW_test_in_dictmaker5758);
                        t3 = test();
                        _fsp--;
                        
                        match(input, COLON, FOLLOW_COLON_in_dictmaker5769);
                        pushFollow(FOLLOW_test_in_dictmaker5784);
                        t4 = test();
                        _fsp--;
                        
                        d.putExpression(t3, t4);
                        
                    }
                        break;
                    
                    default:
                        break loop97;
                    }
                } while (true);
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1545:9: ( COMMA )?
                int alt98 = 2;
                int LA98_0 = input.LA(1);
                
                if ((LA98_0 == COMMA))
                    alt98 = 1;
                switch (alt98) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1545:10: COMMA
                {
                    match(input, COMMA, FOLLOW_COMMA_in_dictmaker5819);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return d;
    }
    
    // $ANTLR end dictmaker
    
    // $ANTLR start classdef
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1549:1: classdef returns [ PythonClassDeclaration classDeclaration = null ] : c= 'class' tu= NAME (r= LPAREN (te= testlist )? m= RPAREN )? co= COLON sa= suite ;
    public final PythonClassDeclaration classdef() {
        PythonClassDeclaration classDeclaration = null;
        
        Token c = null;
        Token tu = null;
        Token r = null;
        Token m = null;
        Token co = null;
        Expression te = null;
        
        Block sa = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1549:68: (c= 'class' tu= NAME (r= LPAREN (te= testlist )? m= RPAREN )? co= COLON sa= suite )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1550:2: c= 'class' tu= NAME (r= LPAREN (te= testlist )? m= RPAREN )? co= COLON sa= suite
            {
                c = input.LT(1);
                match(input, 99, FOLLOW_99_in_classdef5850);
                tu = input.LT(1);
                match(input, NAME, FOLLOW_NAME_in_classdef5857);
                
                classDeclaration = new PythonClassDeclaration(toDLTK(tu), toDLTK(c));
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1555:2: (r= LPAREN (te= testlist )? m= RPAREN )?
                int alt100 = 2;
                int LA100_0 = input.LA(1);
                
                if ((LA100_0 == LPAREN))
                    alt100 = 1;
                switch (alt100) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1556:3: r= LPAREN (te= testlist )? m= RPAREN
                {
                    r = input.LT(1);
                    match(input, LPAREN, FOLLOW_LPAREN_in_classdef5876);
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1557:4: (te= testlist )?
                    int alt99 = 2;
                    int LA99_0 = input.LA(1);
                    
                    if ((LA99_0 == LPAREN || LA99_0 == NAME || (LA99_0 >= PLUS && LA99_0 <= MINUS)
                            || (LA99_0 >= TILDE && LA99_0 <= LBRACK) || LA99_0 == LCURLY
                            || (LA99_0 >= BACKQUOTE && LA99_0 <= STRING) || LA99_0 == 95 || LA99_0 == 97))
                        alt99 = 1;
                    switch (alt99) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1557:5: te= testlist
                    {
                        pushFollow(FOLLOW_testlist_in_classdef5886);
                        te = testlist();
                        _fsp--;
                        
                    }
                        break;
                    
                    }
                    
                    m = input.LT(1);
                    match(input, RPAREN, FOLLOW_RPAREN_in_classdef5897);
                    
                    if (null != te)
                        if (te instanceof ExpressionList)
                            classDeclaration.setParents(toDLTK(r), (ExpressionList) te, toDLTK(m));
                        else {
                            ExpressionList exprList = new ExpressionList();
                            exprList.setStart(te.sourceStart());
                            exprList.setEnd(te.sourceEnd());
                            exprList.addExpression(te);
                            classDeclaration.setParents(toDLTK(r), exprList, toDLTK(m));
                        }
                    
                }
                    break;
                
                }
                
                co = input.LT(1);
                match(input, COLON, FOLLOW_COLON_in_classdef5913);
                pushFollow(FOLLOW_suite_in_classdef5920);
                sa = suite();
                _fsp--;
                
                classDeclaration.setBody(toDLTK(co), sa, sa.sourceEnd());
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return classDeclaration;
    }
    
    // $ANTLR end classdef
    
    // $ANTLR start arglist
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1583:1: arglist returns [PythonCallArgumentsList arguments = new PythonCallArgumentsList();] : (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test ) ;
    public final PythonCallArgumentsList arglist() {
        PythonCallArgumentsList arguments = new PythonCallArgumentsList();
        ;
        
        Expression k = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1583:85: ( (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test ) )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1584:2: (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )
            {
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1584:2: (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )
                int alt106 = 3;
                switch (input.LA(1)) {
                case LPAREN:
                case NAME:
                case PLUS:
                case MINUS:
                case TILDE:
                case LBRACK:
                case LCURLY:
                case BACKQUOTE:
                case INT:
                case FLOAT:
                case COMPLEX:
                case STRING:
                case 95:
                case 97: {
                    alt106 = 1;
                }
                    break;
                case STAR: {
                    alt106 = 2;
                }
                    break;
                case DOUBLESTAR: {
                    alt106 = 3;
                }
                    break;
                default:
                    NoViableAltException nvae = new NoViableAltException(
                            "1584:2: (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )",
                            106, 0, input);
                    
                    throw nvae;
                }
                
                switch (alt106) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1585:3: k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )?
                {
                    pushFollow(FOLLOW_argument_in_arglist5949);
                    k = argument();
                    _fsp--;
                    
                    arguments.addArgument(k);
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1589:11: ( options {greedy=true; } : COMMA k= argument )*
                    loop101: do {
                        int alt101 = 2;
                        int LA101_0 = input.LA(1);
                        
                        if ((LA101_0 == COMMA)) {
                            int LA101_1 = input.LA(2);
                            
                            if ((LA101_1 == LPAREN || LA101_1 == NAME
                                    || (LA101_1 >= PLUS && LA101_1 <= MINUS)
                                    || (LA101_1 >= TILDE && LA101_1 <= LBRACK) || LA101_1 == LCURLY
                                    || (LA101_1 >= BACKQUOTE && LA101_1 <= STRING) || LA101_1 == 95 || LA101_1 == 97))
                                alt101 = 1;
                            
                        }
                        
                        switch (alt101) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1590:35: COMMA k= argument
                        {
                            match(input, COMMA, FOLLOW_COMMA_in_arglist5989);
                            pushFollow(FOLLOW_argument_in_arglist6007);
                            k = argument();
                            _fsp--;
                            
                            arguments.addArgument(k);
                            
                        }
                            break;
                        
                        default:
                            break loop101;
                        }
                    } while (true);
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1596:3: ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )?
                    int alt104 = 2;
                    int LA104_0 = input.LA(1);
                    
                    if ((LA104_0 == COMMA))
                        alt104 = 1;
                    switch (alt104) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1597:4: COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )?
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_arglist6045);
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1598:4: ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )?
                        int alt103 = 3;
                        int LA103_0 = input.LA(1);
                        
                        if ((LA103_0 == STAR))
                            alt103 = 1;
                        else if ((LA103_0 == DOUBLESTAR))
                            alt103 = 2;
                        switch (alt103) {
                        case 1:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1599:5: STAR k= test ( COMMA DOUBLESTAR k= test )?
                        {
                            match(input, STAR, FOLLOW_STAR_in_arglist6057);
                            pushFollow(FOLLOW_test_in_arglist6068);
                            k = test();
                            _fsp--;
                            
                            arguments.addArgument(k, PythonArgument.STAR);
                            
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1604:16: ( COMMA DOUBLESTAR k= test )?
                            int alt102 = 2;
                            int LA102_0 = input.LA(1);
                            
                            if ((LA102_0 == COMMA))
                                alt102 = 1;
                            switch (alt102) {
                            case 1:
                                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1605:17: COMMA DOUBLESTAR k= test
                            {
                                match(input, COMMA, FOLLOW_COMMA_in_arglist6124);
                                match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_arglist6143);
                                pushFollow(FOLLOW_test_in_arglist6166);
                                k = test();
                                _fsp--;
                                
                                arguments.addArgument(k, PythonArgument.DOUBLESTAR);
                                
                            }
                                break;
                            
                            }
                            
                        }
                            break;
                        case 2:
                            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1613:14: DOUBLESTAR k= test
                        {
                            match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_arglist6235);
                            pushFollow(FOLLOW_test_in_arglist6255);
                            k = test();
                            _fsp--;
                            
                            arguments.addArgument(k, PythonArgument.DOUBLESTAR);
                            
                        }
                            break;
                        
                        }
                        
                    }
                        break;
                    
                    }
                    
                }
                    break;
                case 2:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1621:3: STAR k= test ( COMMA DOUBLESTAR k= test )?
                {
                    match(input, STAR, FOLLOW_STAR_in_arglist6294);
                    pushFollow(FOLLOW_test_in_arglist6303);
                    k = test();
                    _fsp--;
                    
                    arguments.addArgument(k, PythonArgument.STAR);
                    
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1626:3: ( COMMA DOUBLESTAR k= test )?
                    int alt105 = 2;
                    int LA105_0 = input.LA(1);
                    
                    if ((LA105_0 == COMMA))
                        alt105 = 1;
                    switch (alt105) {
                    case 1:
                        // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1627:4: COMMA DOUBLESTAR k= test
                    {
                        match(input, COMMA, FOLLOW_COMMA_in_arglist6319);
                        match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_arglist6325);
                        pushFollow(FOLLOW_test_in_arglist6335);
                        k = test();
                        _fsp--;
                        
                        arguments.addArgument(k, PythonArgument.DOUBLESTAR);
                        
                    }
                        break;
                    
                    }
                    
                }
                    break;
                case 3:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1635:3: DOUBLESTAR k= test
                {
                    match(input, DOUBLESTAR, FOLLOW_DOUBLESTAR_in_arglist6355);
                    pushFollow(FOLLOW_test_in_arglist6364);
                    k = test();
                    _fsp--;
                    
                    arguments.addArgument(k, PythonArgument.DOUBLESTAR);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return arguments;
    }
    
    // $ANTLR end arglist
    
    // $ANTLR start argument
    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1643:1: argument returns [ Expression e = null ] : e0= test ( ASSIGN k= test )? ;
    public final Expression argument() {
        Expression e = null;
        
        Expression e0 = null;
        
        Expression k = null;
        
        try {
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1643:41: (e0= test ( ASSIGN k= test )? )
            // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1644:2: e0= test ( ASSIGN k= test )?
            {
                pushFollow(FOLLOW_test_in_argument6402);
                e0 = test();
                _fsp--;
                
                e = e0;
                
                // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1648:2: ( ASSIGN k= test )?
                int alt107 = 2;
                int LA107_0 = input.LA(1);
                
                if ((LA107_0 == ASSIGN))
                    alt107 = 1;
                switch (alt107) {
                case 1:
                    // /Users/buriy/Documents/Eclipse/dltk-yoursway/python/plugins/org.eclipse.dltk.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1649:3: ASSIGN k= test
                {
                    match(input, ASSIGN, FOLLOW_ASSIGN_in_argument6412);
                    pushFollow(FOLLOW_test_in_argument6421);
                    k = test();
                    _fsp--;
                    
                    e = new Assignment(e, k);
                    
                }
                    break;
                
                }
                
            }
            
        }

        catch (RecognitionException re) {
            if (reporter != null)
                reporter.reportError(re);
            recover(input, re);
        } catch (Throwable extre) {
            //System.err.println(t);
            if (reporter != null)
                reporter.reportThrowable(extre);
        } finally {
        }
        return e;
    }
    
    // $ANTLR end argument
    
    public static final BitSet FOLLOW_NEWLINE_in_file_input101 = new BitSet(new long[] { 0x01F58C00000005C0L,
            0x0000000E8736FFE0L });
    public static final BitSet FOLLOW_stmt_in_file_input126 = new BitSet(new long[] { 0x01F58C00000005C0L,
            0x0000000E8736FFE0L });
    public static final BitSet FOLLOW_EOF_in_file_input157 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DECORATOR_S_in_decorator185 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_dot_name_in_decorator192 = new BitSet(
            new long[] { 0x0000000000000140L });
    public static final BitSet FOLLOW_LPAREN_in_decorator204 = new BitSet(new long[] { 0x01F58C0000006700L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_arglist_in_decorator214 = new BitSet(new long[] { 0x0000000000000200L });
    public static final BitSet FOLLOW_RPAREN_in_decorator229 = new BitSet(new long[] { 0x0000000000000040L });
    public static final BitSet FOLLOW_NEWLINE_in_decorator245 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_decorator_in_decoraror_list267 = new BitSet(
            new long[] { 0x0000000000000082L });
    public static final BitSet FOLLOW_decoraror_list_in_funcdef302 = new BitSet(new long[] {
            0x0000000000000000L, 0x0000000000000020L });
    public static final BitSet FOLLOW_69_in_funcdef314 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_funcdef322 = new BitSet(new long[] { 0x0000000000000100L });
    public static final BitSet FOLLOW_parameters_in_funcdef328 = new BitSet(
            new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_funcdef346 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_funcdef360 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_parameters382 = new BitSet(new long[] { 0x0000000000006700L });
    public static final BitSet FOLLOW_varargslist_in_parameters387 = new BitSet(
            new long[] { 0x0000000000000200L });
    public static final BitSet FOLLOW_RPAREN_in_parameters394 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_defparameter_in_varargslist405 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_varargslist422 = new BitSet(new long[] { 0x0000000000000500L });
    public static final BitSet FOLLOW_defparameter_in_varargslist427 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_varargslist443 = new BitSet(new long[] { 0x0000000000006002L });
    public static final BitSet FOLLOW_STAR_in_varargslist460 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_varargslist466 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_varargslist501 = new BitSet(new long[] { 0x0000000000004000L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist503 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_varargslist509 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist551 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_varargslist557 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_STAR_in_varargslist595 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_varargslist601 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_varargslist610 = new BitSet(new long[] { 0x0000000000004000L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist615 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_varargslist621 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist635 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_varargslist641 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_fpdef_in_defparameter660 = new BitSet(
            new long[] { 0x0000000000008002L });
    public static final BitSet FOLLOW_ASSIGN_in_defparameter665 = new BitSet(new long[] {
            0x01F58C0000000500L, 0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_defparameter671 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NAME_in_fpdef703 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_fpdef714 = new BitSet(new long[] { 0x0000000000000500L });
    public static final BitSet FOLLOW_fplist_in_fpdef716 = new BitSet(new long[] { 0x0000000000000200L });
    public static final BitSet FOLLOW_RPAREN_in_fpdef719 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_fpdef_in_fplist734 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_fplist752 = new BitSet(new long[] { 0x0000000000000500L });
    public static final BitSet FOLLOW_fpdef_in_fplist754 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_fplist763 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_small_stmt_in_simple_stmt781 = new BitSet(
            new long[] { 0x0000000000010040L });
    public static final BitSet FOLLOW_SEMI_in_simple_stmt800 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_small_stmt_in_simple_stmt802 = new BitSet(
            new long[] { 0x0000000000010040L });
    public static final BitSet FOLLOW_SEMI_in_simple_stmt812 = new BitSet(new long[] { 0x0000000000000040L });
    public static final BitSet FOLLOW_NEWLINE_in_simple_stmt817 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_simple_stmt_in_stmt838 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_compound_stmt_in_stmt858 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_expr_stmt_in_small_stmt892 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_print_stmt_in_small_stmt903 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_del_stmt_in_small_stmt913 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_pass_stmt_in_small_stmt923 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_flow_stmt_in_small_stmt933 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_import_stmt_in_small_stmt943 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_global_stmt_in_small_stmt953 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_exec_stmt_in_small_stmt963 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_assert_stmt_in_small_stmt973 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_testlist_in_expr_stmt1001 = new BitSet(
            new long[] { 0x000000001FFE8002L });
    public static final BitSet FOLLOW_augassign_in_expr_stmt1015 = new BitSet(new long[] {
            0x01F58C0000000500L, 0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_expr_stmt1024 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1046 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_expr_stmt1055 = new BitSet(
            new long[] { 0x0000000000008002L });
    public static final BitSet FOLLOW_PLUSEQUAL_in_augassign1088 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_MINUSEQUAL_in_augassign1097 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_STAREQUAL_in_augassign1107 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_SLASHEQUAL_in_augassign1116 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_PERCENTEQUAL_in_augassign1126 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_AMPEREQUAL_in_augassign1135 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_VBAREQUAL_in_augassign1144 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_CIRCUMFLEXEQUAL_in_augassign1153 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LEFTSHIFTEQUAL_in_augassign1162 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_RIGHTSHIFTEQUAL_in_augassign1171 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLESTAREQUAL_in_augassign1180 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLESLASHEQUAL_in_augassign1189 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_70_in_print_stmt1223 = new BitSet(new long[] { 0x01F58C0020000502L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_print_stmt1234 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_RIGHTSHIFT_in_print_stmt1247 = new BitSet(new long[] {
            0x01F58C0000000500L, 0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_print_stmt1263 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_71_in_del_stmt1294 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_exprlist_in_del_stmt1302 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_72_in_pass_stmt1325 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_break_stmt_in_flow_stmt1351 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_continue_stmt_in_flow_stmt1362 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_return_stmt_in_flow_stmt1372 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_raise_stmt_in_flow_stmt1382 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_yield_stmt_in_flow_stmt1392 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_73_in_break_stmt1413 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_74_in_continue_stmt1437 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_75_in_return_stmt1462 = new BitSet(new long[] { 0x01F58C0000000502L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_return_stmt1473 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_76_in_yield_stmt1501 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_yield_stmt1508 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_77_in_raise_stmt1530 = new BitSet(new long[] { 0x01F58C0000000502L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_raise_stmt1544 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_raise_stmt1555 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_raise_stmt1564 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_raise_stmt1577 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_raise_stmt1587 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_78_in_import_stmt1704 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_module_imp_in_import_stmt1726 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_import_stmt1755 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_module_imp_in_import_stmt1769 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_79_in_import_stmt1805 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_dot_name_in_import_stmt1813 = new BitSet(new long[] {
            0x0000000000000000L, 0x0000000000004000L });
    public static final BitSet FOLLOW_78_in_import_stmt1820 = new BitSet(new long[] { 0x0000000000002400L });
    public static final BitSet FOLLOW_module_imp_in_import_stmt1860 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_import_stmt1883 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_module_imp_in_import_stmt1898 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_STAR_in_import_stmt1955 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NAME_in_dotted_name2005 = new BitSet(new long[] { 0x0000000040000000L });
    public static final BitSet FOLLOW_DOT_in_dotted_name2015 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_dotted_name2022 = new BitSet(new long[] { 0x0000000040000002L });
    public static final BitSet FOLLOW_dotted_name_in_dot_name2057 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NAME_in_dot_name2077 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_dot_name_in_module_imp2107 = new BitSet(new long[] {
            0x0000000000000002L, 0x0000000000010000L });
    public static final BitSet FOLLOW_80_in_module_imp2119 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_module_imp2126 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_81_in_global_stmt2153 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_global_stmt2155 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_global_stmt2158 = new BitSet(
            new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_global_stmt2160 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_82_in_exec_stmt2185 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_expr_in_exec_stmt2191 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000000080000L });
    public static final BitSet FOLLOW_83_in_exec_stmt2198 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_exec_stmt2207 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_exec_stmt2215 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_exec_stmt2221 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_84_in_assert_stmt2248 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_assert_stmt2255 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_assert_stmt2261 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_assert_stmt2267 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_if_stmt_in_compound_stmt2295 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_while_stmt_in_compound_stmt2306 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_for_stmt_in_compound_stmt2316 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_try_stmt_in_compound_stmt2326 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_funcdef_in_compound_stmt2336 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_classdef_in_compound_stmt2346 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_with_stmt_in_compound_stmt2356 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_85_in_if_stmt2379 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_if_stmt2388 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_if_stmt2392 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_if_stmt2401 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000000C00000L });
    public static final BitSet FOLLOW_86_in_if_stmt2422 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_if_stmt2431 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_if_stmt2437 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_if_stmt2445 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000000C00000L });
    public static final BitSet FOLLOW_87_in_if_stmt2466 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_if_stmt2472 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_if_stmt2482 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_88_in_while_stmt2518 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_while_stmt2526 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_while_stmt2530 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_while_stmt2540 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000000800000L });
    public static final BitSet FOLLOW_87_in_while_stmt2554 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_while_stmt2556 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_while_stmt2566 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_89_in_for_stmt2597 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_exprlist_in_for_stmt2606 = new BitSet(new long[] { 0x0000000000000000L,
            0x0000000000080000L });
    public static final BitSet FOLLOW_83_in_for_stmt2611 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_for_stmt2619 = new BitSet(
            new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_for_stmt2623 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_for_stmt2632 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000000800000L });
    public static final BitSet FOLLOW_87_in_for_stmt2647 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_for_stmt2653 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_for_stmt2663 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_90_in_try_stmt2695 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_try_stmt2699 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_try_stmt2714 = new BitSet(new long[] { 0x0000000000000000L,
            0x0000000018000000L });
    public static final BitSet FOLLOW_91_in_try_stmt2752 = new BitSet(new long[] { 0x01F58C0000000D00L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_try_stmt2767 = new BitSet(new long[] { 0x0000000000001800L });
    public static final BitSet FOLLOW_COMMA_in_try_stmt2774 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_try_stmt2780 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_try_stmt2793 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_try_stmt2802 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000008800000L });
    public static final BitSet FOLLOW_87_in_try_stmt2827 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_try_stmt2833 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_try_stmt2843 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_92_in_try_stmt2954 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_try_stmt2977 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_try_stmt2993 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_simple_stmt_in_suite3051 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NEWLINE_in_suite3072 = new BitSet(new long[] { 0x0000000000000010L });
    public static final BitSet FOLLOW_INDENT_in_suite3082 = new BitSet(new long[] { 0x01F58C0000000580L,
            0x0000000E8736FFE0L });
    public static final BitSet FOLLOW_stmt_in_suite3106 = new BitSet(new long[] { 0x01F58C00000005A0L,
            0x0000000E8736FFE0L });
    public static final BitSet FOLLOW_DEDENT_in_suite3132 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_and_test_in_or_test3182 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000020000000L });
    public static final BitSet FOLLOW_93_in_or_test3196 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000080000000L });
    public static final BitSet FOLLOW_and_test_in_or_test3205 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000020000000L });
    public static final BitSet FOLLOW_or_test_in_test3234 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_lambdef_in_test3249 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_not_test_in_and_test3272 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000040000000L });
    public static final BitSet FOLLOW_94_in_and_test3286 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000080000000L });
    public static final BitSet FOLLOW_not_test_in_and_test3295 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000040000000L });
    public static final BitSet FOLLOW_95_in_not_test3328 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000080000000L });
    public static final BitSet FOLLOW_not_test_in_not_test3337 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_comparison_in_not_test3356 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_expr_in_comparison3379 = new BitSet(new long[] { 0x0000003F80000002L,
            0x0000000180080000L });
    public static final BitSet FOLLOW_comp_op_in_comparison3393 = new BitSet(
            new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_expr_in_comparison3402 = new BitSet(new long[] { 0x0000003F80000002L,
            0x0000000180080000L });
    public static final BitSet FOLLOW_LESS_in_comp_op3434 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_GREATER_in_comp_op3446 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_EQUAL_in_comp_op3457 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_GREATEREQUAL_in_comp_op3470 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LESSEQUAL_in_comp_op3480 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_ALT_NOTEQUAL_in_comp_op3494 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NOTEQUAL_in_comp_op3506 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_83_in_comp_op3521 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_95_in_comp_op3531 = new BitSet(new long[] { 0x0000000000000000L,
            0x0000000000080000L });
    public static final BitSet FOLLOW_83_in_comp_op3533 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_96_in_comp_op3543 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_96_in_comp_op3553 = new BitSet(new long[] { 0x0000000000000000L,
            0x0000000080000000L });
    public static final BitSet FOLLOW_95_in_comp_op3555 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_xor_expr_in_expr3577 = new BitSet(new long[] { 0x0000004000000002L });
    public static final BitSet FOLLOW_VBAR_in_expr3591 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_xor_expr_in_expr3601 = new BitSet(new long[] { 0x0000004000000002L });
    public static final BitSet FOLLOW_and_expr_in_xor_expr3634 = new BitSet(
            new long[] { 0x0000008000000002L });
    public static final BitSet FOLLOW_CIRCUMFLEX_in_xor_expr3648 = new BitSet(
            new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_and_expr_in_xor_expr3657 = new BitSet(
            new long[] { 0x0000008000000002L });
    public static final BitSet FOLLOW_shift_expr_in_and_expr3688 = new BitSet(
            new long[] { 0x0000010000000002L });
    public static final BitSet FOLLOW_AMPER_in_and_expr3703 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_shift_expr_in_and_expr3713 = new BitSet(
            new long[] { 0x0000010000000002L });
    public static final BitSet FOLLOW_arith_expr_in_shift_expr3743 = new BitSet(
            new long[] { 0x0000020020000002L });
    public static final BitSet FOLLOW_LEFTSHIFT_in_shift_expr3767 = new BitSet(
            new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_RIGHTSHIFT_in_shift_expr3788 = new BitSet(
            new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_arith_expr_in_shift_expr3810 = new BitSet(
            new long[] { 0x0000020020000002L });
    public static final BitSet FOLLOW_term_in_arith_expr3846 = new BitSet(new long[] { 0x00000C0000000002L });
    public static final BitSet FOLLOW_PLUS_in_arith_expr3866 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_MINUS_in_arith_expr3886 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_term_in_arith_expr3905 = new BitSet(new long[] { 0x00000C0000000002L });
    public static final BitSet FOLLOW_factor_in_term3935 = new BitSet(new long[] { 0x0000700000002002L });
    public static final BitSet FOLLOW_STAR_in_term3956 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_SLASH_in_term3980 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_PERCENT_in_term4005 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_DOUBLESLASH_in_term4033 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_factor_in_term4059 = new BitSet(new long[] { 0x0000700000002002L });
    public static final BitSet FOLLOW_PLUS_in_factor4105 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_MINUS_in_factor4128 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_TILDE_in_factor4150 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_factor_in_factor4172 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_power_in_factor4189 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_atom_in_power4216 = new BitSet(new long[] { 0x0001000040004102L });
    public static final BitSet FOLLOW_trailer_in_power4234 = new BitSet(new long[] { 0x0001000040004102L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_power4267 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_factor_in_power4285 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_trailer4333 = new BitSet(new long[] { 0x01F58C0000006500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_arglist_in_trailer4348 = new BitSet(new long[] { 0x0000000000000200L });
    public static final BitSet FOLLOW_RPAREN_in_trailer4356 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LBRACK_in_trailer4374 = new BitSet(new long[] { 0x01F58C0040000D00L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_subscriptlist_in_trailer4384 = new BitSet(
            new long[] { 0x0002000000000000L });
    public static final BitSet FOLLOW_RBRACK_in_trailer4393 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOT_in_trailer4408 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_trailer4416 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_atom4446 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_tuplelist_in_atom4454 = new BitSet(new long[] { 0x0000000000000200L });
    public static final BitSet FOLLOW_RPAREN_in_atom4464 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LPAREN_in_atom4475 = new BitSet(new long[] { 0x0000000000000200L });
    public static final BitSet FOLLOW_RPAREN_in_atom4483 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LBRACK_in_atom4495 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_listmaker_in_atom4503 = new BitSet(new long[] { 0x0002000000000000L });
    public static final BitSet FOLLOW_RBRACK_in_atom4513 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LBRACK_in_atom4523 = new BitSet(new long[] { 0x0002000000000000L });
    public static final BitSet FOLLOW_RBRACK_in_atom4531 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LCURLY_in_atom4542 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_dictmaker_in_atom4552 = new BitSet(new long[] { 0x0008000000000000L });
    public static final BitSet FOLLOW_RCURLY_in_atom4562 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_LCURLY_in_atom4572 = new BitSet(new long[] { 0x0008000000000000L });
    public static final BitSet FOLLOW_RCURLY_in_atom4580 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_BACKQUOTE_in_atom4593 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_atom4599 = new BitSet(new long[] { 0x0010000000000000L });
    public static final BitSet FOLLOW_BACKQUOTE_in_atom4608 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_NAME_in_atom4618 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_INT_in_atom4630 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_FLOAT_in_atom4647 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_COMPLEX_in_atom4665 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_STRING_in_atom4682 = new BitSet(new long[] { 0x0100000000000002L });
    public static final BitSet FOLLOW_test_in_listmaker4712 = new BitSet(new long[] { 0x0000000000001002L,
            0x0000000002000000L });
    public static final BitSet FOLLOW_list_for_in_listmaker4729 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_COMMA_in_listmaker4777 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_listmaker4787 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_listmaker4806 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_89_in_list_for4837 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_exprlist_in_list_for4845 = new BitSet(new long[] { 0x0000000000000000L,
            0x0000000000080000L });
    public static final BitSet FOLLOW_83_in_list_for4849 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_list_for4858 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000002200000L });
    public static final BitSet FOLLOW_list_if_in_list_for4881 = new BitSet(new long[] { 0x0000000000000002L,
            0x0000000002200000L });
    public static final BitSet FOLLOW_85_in_list_if4914 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_list_if4921 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_97_in_lambdef4942 = new BitSet(new long[] { 0x0000000000006D00L });
    public static final BitSet FOLLOW_varargslist_in_lambdef4950 = new BitSet(
            new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_lambdef4957 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_lambdef4965 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_subscript_in_subscriptlist4989 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_subscriptlist5008 = new BitSet(new long[] {
            0x01F58C0040000D00L, 0x0000000280000000L });
    public static final BitSet FOLLOW_subscript_in_subscriptlist5017 = new BitSet(
            new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_subscriptlist5032 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOT_in_subscript5056 = new BitSet(new long[] { 0x0000000040000000L });
    public static final BitSet FOLLOW_DOT_in_subscript5058 = new BitSet(new long[] { 0x0000000040000000L });
    public static final BitSet FOLLOW_DOT_in_subscript5060 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_test_in_subscript5085 = new BitSet(new long[] { 0x0000000000000802L });
    public static final BitSet FOLLOW_COLON_in_subscript5100 = new BitSet(new long[] { 0x01F58C0000000D02L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_subscript5116 = new BitSet(new long[] { 0x0000000000000802L });
    public static final BitSet FOLLOW_sliceop_in_subscript5146 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_COLON_in_subscript5188 = new BitSet(new long[] { 0x01F58C0000000D02L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_subscript5212 = new BitSet(new long[] { 0x0000000000000802L });
    public static final BitSet FOLLOW_sliceop_in_subscript5258 = new BitSet(
            new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_COLON_in_sliceop5318 = new BitSet(new long[] { 0x01F58C0000000502L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_sliceop5327 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_expr_in_exprlist5354 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_exprlist5376 = new BitSet(new long[] { 0x01F58C0000000500L });
    public static final BitSet FOLLOW_expr_in_exprlist5385 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_exprlist5399 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_test_in_testlist5426 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_testlist5455 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_testlist5467 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_testlist5502 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_test_in_tuplelist5536 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_tuplelist5561 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_tuplelist5573 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_tuplelist5609 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_98_in_with_stmt5641 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_with_stmt5649 = new BitSet(new long[] { 0x0000000000000800L,
            0x0000000000010000L });
    public static final BitSet FOLLOW_80_in_with_stmt5657 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_with_stmt5663 = new BitSet(
            new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_with_stmt5669 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_with_stmt5675 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_test_in_dictmaker5697 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_dictmaker5700 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_dictmaker5707 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_dictmaker5743 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_dictmaker5758 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_dictmaker5769 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_dictmaker5784 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_dictmaker5819 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_99_in_classdef5850 = new BitSet(new long[] { 0x0000000000000400L });
    public static final BitSet FOLLOW_NAME_in_classdef5857 = new BitSet(new long[] { 0x0000000000000900L });
    public static final BitSet FOLLOW_LPAREN_in_classdef5876 = new BitSet(new long[] { 0x01F58C0000000700L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_testlist_in_classdef5886 = new BitSet(
            new long[] { 0x0000000000000200L });
    public static final BitSet FOLLOW_RPAREN_in_classdef5897 = new BitSet(new long[] { 0x0000000000000800L });
    public static final BitSet FOLLOW_COLON_in_classdef5913 = new BitSet(new long[] { 0x01F58C0000000540L,
            0x000000028016FFC0L });
    public static final BitSet FOLLOW_suite_in_classdef5920 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_argument_in_arglist5949 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_arglist5989 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_argument_in_arglist6007 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_arglist6045 = new BitSet(new long[] { 0x0000000000006002L });
    public static final BitSet FOLLOW_STAR_in_arglist6057 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_arglist6068 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_arglist6124 = new BitSet(new long[] { 0x0000000000004000L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6143 = new BitSet(new long[] {
            0x01F58C0000000500L, 0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_arglist6166 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6235 = new BitSet(new long[] {
            0x01F58C0000000500L, 0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_arglist6255 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_STAR_in_arglist6294 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_arglist6303 = new BitSet(new long[] { 0x0000000000001002L });
    public static final BitSet FOLLOW_COMMA_in_arglist6319 = new BitSet(new long[] { 0x0000000000004000L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6325 = new BitSet(new long[] {
            0x01F58C0000000500L, 0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_arglist6335 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6355 = new BitSet(new long[] {
            0x01F58C0000000500L, 0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_arglist6364 = new BitSet(new long[] { 0x0000000000000002L });
    public static final BitSet FOLLOW_test_in_argument6402 = new BitSet(new long[] { 0x0000000000008002L });
    public static final BitSet FOLLOW_ASSIGN_in_argument6412 = new BitSet(new long[] { 0x01F58C0000000500L,
            0x0000000280000000L });
    public static final BitSet FOLLOW_test_in_argument6421 = new BitSet(new long[] { 0x0000000000000002L });
    
}