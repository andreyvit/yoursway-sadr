// $ANTLR 3.1.2 /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g 2009-04-02 21:32:47

package org.eclipse.dltk.python.internal.core.parsers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.declarations.Decorator;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.ArgumentList;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.ast.expressions.Literal;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonCallArgumentsList;
import org.eclipse.dltk.python.parser.ast.PythonAssertStatement;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonDelStatement;
import org.eclipse.dltk.python.parser.ast.PythonExceptStatement;
import org.eclipse.dltk.python.parser.ast.PythonForStatement;
import org.eclipse.dltk.python.parser.ast.PythonGlobalStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportFromStatement;
import org.eclipse.dltk.python.parser.ast.PythonImportStatement;
import org.eclipse.dltk.python.parser.ast.PythonRaiseStatement;
import org.eclipse.dltk.python.parser.ast.PythonTryStatement;
import org.eclipse.dltk.python.parser.ast.PythonWhileStatement;
import org.eclipse.dltk.python.parser.ast.PythonYieldStatement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;
import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;
import org.eclipse.dltk.python.parser.ast.expressions.CallHolder;
import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;
import org.eclipse.dltk.python.parser.ast.expressions.EmptyExpression;
import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReference;
import org.eclipse.dltk.python.parser.ast.expressions.IndexHolder;
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
import org.eclipse.dltk.python.parser.ast.PythonWithStatement;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class python_v3Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INDENT", "DEDENT", "NEWLINE", "DECORATOR_S", "LPAREN", "RPAREN", "NAME", "COLON", "COMMA", "STAR", "DOUBLESTAR", "ASSIGN", "SEMI", "PLUSEQUAL", "MINUSEQUAL", "STAREQUAL", "SLASHEQUAL", "PERCENTEQUAL", "AMPEREQUAL", "VBAREQUAL", "CIRCUMFLEXEQUAL", "LEFTSHIFTEQUAL", "RIGHTSHIFTEQUAL", "DOUBLESTAREQUAL", "DOUBLESLASHEQUAL", "RIGHTSHIFT", "DOT", "LESS", "GREATER", "EQUAL", "GREATEREQUAL", "LESSEQUAL", "ALT_NOTEQUAL", "NOTEQUAL", "VBAR", "CIRCUMFLEX", "AMPER", "LEFTSHIFT", "PLUS", "MINUS", "SLASH", "PERCENT", "DOUBLESLASH", "TILDE", "LBRACK", "RBRACK", "LCURLY", "RCURLY", "BACKQUOTE", "INT", "FLOAT", "COMPLEX", "STRING", "POINTFLOAT", "EXPONENTFLOAT", "DIGITS", "FRACTION", "Exponent", "TRIAPOS", "TRIQUOTE", "ESC", "CONTINUED_LINE", "WS", "LEADING_WS", "COMMENT", "'def'", "'print'", "'del'", "'pass'", "'break'", "'continue'", "'return'", "'yield'", "'raise'", "'import'", "'from'", "'as'", "'global'", "'exec'", "'in'", "'assert'", "'if'", "'elif'", "'else'", "'while'", "'for'", "'try'", "'except'", "'finally'", "'or'", "'and'", "'not'", "'is'", "'lambda'", "'with'", "'class'"
    };
    public static final int COMMA=12;
    public static final int MINUS=43;
    public static final int DEDENT=5;
    public static final int DECORATOR_S=7;
    public static final int TRIQUOTE=63;
    public static final int T__73=73;
    public static final int FRACTION=60;
    public static final int COMPLEX=55;
    public static final int T__72=72;
    public static final int DOUBLESLASHEQUAL=28;
    public static final int TILDE=47;
    public static final int T__70=70;
    public static final int NEWLINE=6;
    public static final int DOT=30;
    public static final int PLUSEQUAL=17;
    public static final int RIGHTSHIFTEQUAL=26;
    public static final int LCURLY=50;
    public static final int T__96=96;
    public static final int RPAREN=9;
    public static final int PLUS=42;
    public static final int T__89=89;
    public static final int WS=66;
    public static final int T__79=79;
    public static final int STRING=56;
    public static final int T__92=92;
    public static final int POINTFLOAT=57;
    public static final int T__88=88;
    public static final int LBRACK=48;
    public static final int SEMI=16;
    public static final int T__90=90;
    public static final int EXPONENTFLOAT=58;
    public static final int EQUAL=33;
    public static final int LESSEQUAL=35;
    public static final int T__91=91;
    public static final int T__85=85;
    public static final int ALT_NOTEQUAL=36;
    public static final int COLON=11;
    public static final int AMPER=40;
    public static final int NAME=10;
    public static final int T__93=93;
    public static final int DOUBLESTAREQUAL=27;
    public static final int T__86=86;
    public static final int PERCENT=45;
    public static final int T__94=94;
    public static final int T__80=80;
    public static final int T__95=95;
    public static final int T__69=69;
    public static final int DOUBLESTAR=14;
    public static final int FLOAT=54;
    public static final int SLASHEQUAL=20;
    public static final int NOTEQUAL=37;
    public static final int T__87=87;
    public static final int CIRCUMFLEX=39;
    public static final int T__74=74;
    public static final int RCURLY=51;
    public static final int LESS=31;
    public static final int INT=53;
    public static final int LEADING_WS=67;
    public static final int ASSIGN=15;
    public static final int LPAREN=8;
    public static final int GREATER=32;
    public static final int VBAR=38;
    public static final int BACKQUOTE=52;
    public static final int CONTINUED_LINE=65;
    public static final int T__98=98;
    public static final int Exponent=61;
    public static final int DIGITS=59;
    public static final int T__78=78;
    public static final int SLASH=44;
    public static final int COMMENT=68;
    public static final int T__99=99;
    public static final int TRIAPOS=62;
    public static final int AMPEREQUAL=22;
    public static final int T__77=77;
    public static final int ESC=64;
    public static final int T__84=84;
    public static final int T__97=97;
    public static final int T__75=75;
    public static final int RIGHTSHIFT=29;
    public static final int MINUSEQUAL=18;
    public static final int PERCENTEQUAL=21;
    public static final int LEFTSHIFTEQUAL=25;
    public static final int EOF=-1;
    public static final int CIRCUMFLEXEQUAL=24;
    public static final int INDENT=4;
    public static final int T__76=76;
    public static final int RBRACK=49;
    public static final int T__82=82;
    public static final int GREATEREQUAL=34;
    public static final int T__81=81;
    public static final int DOUBLESLASH=46;
    public static final int STAR=13;
    public static final int STAREQUAL=19;
    public static final int VBAREQUAL=23;
    public static final int T__83=83;
    public static final int T__71=71;
    public static final int LEFTSHIFT=41;

    // delegates
    // delegators


        public python_v3Parser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public python_v3Parser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return python_v3Parser.tokenNames; }
    public String getGrammarFileName() { return "/Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g"; }


    public DLTKPythonErrorReporter reporter;
    	
    public ModuleDeclaration decl;
    	
    public int length;
    public DLTKTokenConverter converter;
    DLTKToken toDLTK(Token token) {
    	return converter.convert(token);
    }
    public void emitErrorMessage(String msg) {
    	reporter.reportMessage(msg);
    }
    public void reportError(RecognitionException e) {
    	if( reporter != null ) {
    		reporter.reportError(e);
    	}
    }
    public void setStartEndForEmbracedExpr(Expression exp, Token lb, Token rb)
    {
    	exp.setStart(toDLTK(lb).getColumn());
    	exp.setEnd(toDLTK(rb).getColumn()+1);
    }



    // $ANTLR start "file_input"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:142:1: file_input : ( NEWLINE | s= stmt )* EOF ;
    public final void file_input() throws RecognitionException {
        ArrayList s = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:142:11: ( ( NEWLINE | s= stmt )* EOF )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:143:6: ( NEWLINE | s= stmt )* EOF
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:143:6: ( NEWLINE | s= stmt )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==NEWLINE) ) {
                    alt1=1;
                }
                else if ( ((LA1_0>=DECORATOR_S && LA1_0<=LPAREN)||LA1_0==NAME||(LA1_0>=PLUS && LA1_0<=MINUS)||(LA1_0>=TILDE && LA1_0<=LBRACK)||LA1_0==LCURLY||(LA1_0>=BACKQUOTE && LA1_0<=STRING)||(LA1_0>=69 && LA1_0<=79)||(LA1_0>=81 && LA1_0<=82)||(LA1_0>=84 && LA1_0<=85)||(LA1_0>=88 && LA1_0<=90)||LA1_0==95||(LA1_0>=97 && LA1_0<=99)) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:144:7: NEWLINE
            	    {
            	    match(input,NEWLINE,FOLLOW_NEWLINE_in_file_input101); 

            	    }
            	    break;
            	case 2 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:146:7: s= stmt
            	    {
            	    pushFollow(FOLLOW_stmt_in_file_input126);
            	    s=stmt();

            	    state._fsp--;

            	     
            	        				//statements.addAll( s );     
            	        				if( s != null ) {
            	        					Iterator i = s.iterator();
            	        					while( i.hasNext() ) {
            	        						Statement sst = (Statement)i.next();
            	        						if( sst != null ) {
            	    		    					decl.addStatement( sst );
            	        						}
            	        					}
            	        				}
            	        			

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_file_input157); 

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "file_input"


    // $ANTLR start "decorator"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:171:1: decorator returns [ Decorator decorator = null ] : dec= DECORATOR_S dottedName= dot_name (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )? NEWLINE ;
    public final Decorator decorator() throws RecognitionException {
        Decorator decorator =  null;

        Token dec=null;
        Token lp0=null;
        Token rp0=null;
        Token dottedName = null;

        PythonCallArgumentsList arguments = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:171:49: (dec= DECORATOR_S dottedName= dot_name (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )? NEWLINE )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:172:2: dec= DECORATOR_S dottedName= dot_name (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )? NEWLINE
            {
            dec=(Token)match(input,DECORATOR_S,FOLLOW_DECORATOR_S_in_decorator185); 
            pushFollow(FOLLOW_dot_name_in_decorator192);
            dottedName=dot_name();

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:174:2: (lp0= LPAREN (arguments= arglist )? rp0= RPAREN )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==LPAREN) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:175:4: lp0= LPAREN (arguments= arglist )? rp0= RPAREN
                    {
                    lp0=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_decorator204); 
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:176:4: (arguments= arglist )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==LPAREN||LA2_0==NAME||(LA2_0>=STAR && LA2_0<=DOUBLESTAR)||(LA2_0>=PLUS && LA2_0<=MINUS)||(LA2_0>=TILDE && LA2_0<=LBRACK)||LA2_0==LCURLY||(LA2_0>=BACKQUOTE && LA2_0<=STRING)||LA2_0==95||LA2_0==97) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:176:5: arguments= arglist
                            {
                            pushFollow(FOLLOW_arglist_in_decorator214);
                            arguments=arglist();

                            state._fsp--;


                            }
                            break;

                    }

                     if (arguments == null) arguments = new PythonCallArgumentsList(); 
                    rp0=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_decorator229); 

                    				decorator = new PythonFunctionDecorator( toDLTK( dottedName), toDLTK(dec), toDLTK(rp0), arguments );
                    			

                    }
                    break;

            }


            		if( decorator == null ) {
            			decorator = new PythonFunctionDecorator( toDLTK( dottedName ), toDLTK(dec) );
            		}
            	
            match(input,NEWLINE,FOLLOW_NEWLINE_in_decorator245); 

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return decorator;
    }
    // $ANTLR end "decorator"


    // $ANTLR start "decoraror_list"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:192:1: decoraror_list returns [List decorators = new ArrayList() ] : (dec= decorator )+ ;
    public final List decoraror_list() throws RecognitionException {
        List decorators =  new ArrayList();

        Decorator dec = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:192:60: ( (dec= decorator )+ )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:193:2: (dec= decorator )+
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:193:2: (dec= decorator )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==DECORATOR_S) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:194:3: dec= decorator
            	    {
            	    pushFollow(FOLLOW_decorator_in_decoraror_list267);
            	    dec=decorator();

            	    state._fsp--;


            	    			if( dec != null ) {
            	    				decorators.add( dec );
            	    			}
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return decorators;
    }
    // $ANTLR end "decoraror_list"


    // $ANTLR start "funcdef"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:204:1: funcdef returns [ MethodDeclaration methodDeclaration = null; ] : (decorators= decoraror_list )? w= 'def' tu= NAME parameters[ params ] e= COLON body= suite ;
    public final MethodDeclaration funcdef() throws RecognitionException {
        MethodDeclaration methodDeclaration =  null;;

        Token w=null;
        Token tu=null;
        Token e=null;
        List decorators = null;

        Block body = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:204:64: ( (decorators= decoraror_list )? w= 'def' tu= NAME parameters[ params ] e= COLON body= suite )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:205:2: (decorators= decoraror_list )? w= 'def' tu= NAME parameters[ params ] e= COLON body= suite
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:205:2: (decorators= decoraror_list )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==DECORATOR_S) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:206:3: decorators= decoraror_list
                    {
                    pushFollow(FOLLOW_decoraror_list_in_funcdef302);
                    decorators=decoraror_list();

                    state._fsp--;


                    }
                    break;

            }

            w=(Token)match(input,69,FOLLOW_69_in_funcdef314); 
            tu=(Token)match(input,NAME,FOLLOW_NAME_in_funcdef322); 

            		methodDeclaration = new MethodDeclaration( toDLTK( w ), toDLTK( tu ) );
            		if( decorators != null ) {
            			methodDeclaration.setDecorators( decorators );
            		}
            		//this.fParameters.clear();
            		
            		ArgumentList params = new ArgumentList();
            		
            		//Block body;
               	
            pushFollow(FOLLOW_parameters_in_funcdef328);
            parameters(params);

            state._fsp--;


            		methodDeclaration.acceptArguments( params ); 
            	
            e=(Token)match(input,COLON,FOLLOW_COLON_in_funcdef346); 
            pushFollow(FOLLOW_suite_in_funcdef360);
            body=suite();

            state._fsp--;


            		methodDeclaration.acceptBody( body );
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return methodDeclaration;
    }
    // $ANTLR end "funcdef"


    // $ANTLR start "parameters"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:233:1: parameters[ ArgumentList params ] : LPAREN ( varargslist[ params ] )? RPAREN ;
    public final void parameters(ArgumentList params) throws RecognitionException {
        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:233:34: ( LPAREN ( varargslist[ params ] )? RPAREN )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:234:2: LPAREN ( varargslist[ params ] )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_parameters382); 
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:235:2: ( varargslist[ params ] )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LPAREN||LA6_0==NAME||(LA6_0>=STAR && LA6_0<=DOUBLESTAR)) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:235:3: varargslist[ params ]
                    {
                    pushFollow(FOLLOW_varargslist_in_parameters387);
                    varargslist(params);

                    state._fsp--;


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_parameters394); 

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "parameters"


    // $ANTLR start "varargslist"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:240:1: varargslist[ ArgumentList params ] : ( defparameter[params] ( options {greedy=true; } : COMMA defparameter[ params ] )* ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )? | STAR m= NAME ( COMMA DOUBLESTAR m1= NAME )? | DOUBLESTAR m2= NAME );
    public final void varargslist(ArgumentList params) throws RecognitionException {
        Token tu=null;
        Token t1=null;
        Token t2=null;
        Token m=null;
        Token m1=null;
        Token m2=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:240:35: ( defparameter[params] ( options {greedy=true; } : COMMA defparameter[ params ] )* ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )? | STAR m= NAME ( COMMA DOUBLESTAR m1= NAME )? | DOUBLESTAR m2= NAME )
            int alt12=3;
            switch ( input.LA(1) ) {
            case LPAREN:
            case NAME:
                {
                alt12=1;
                }
                break;
            case STAR:
                {
                alt12=2;
                }
                break;
            case DOUBLESTAR:
                {
                alt12=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:241:2: defparameter[params] ( options {greedy=true; } : COMMA defparameter[ params ] )* ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )?
                    {
                    pushFollow(FOLLOW_defparameter_in_varargslist405);
                    defparameter(params);

                    state._fsp--;

                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:242:2: ( options {greedy=true; } : COMMA defparameter[ params ] )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            int LA7_1 = input.LA(2);

                            if ( (LA7_1==LPAREN||LA7_1==NAME) ) {
                                alt7=1;
                            }


                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:243:28: COMMA defparameter[ params ]
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_varargslist422); 
                    	    pushFollow(FOLLOW_defparameter_in_varargslist427);
                    	    defparameter(params);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:246:9: ( COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )? )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==COMMA) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:246:10: COMMA ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )?
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_varargslist443); 
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:247:14: ( STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )? | DOUBLESTAR t2= NAME )?
                            int alt9=3;
                            int LA9_0 = input.LA(1);

                            if ( (LA9_0==STAR) ) {
                                alt9=1;
                            }
                            else if ( (LA9_0==DOUBLESTAR) ) {
                                alt9=2;
                            }
                            switch (alt9) {
                                case 1 :
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:247:16: STAR tu= NAME ( COMMA DOUBLESTAR t1= NAME )?
                                    {
                                    match(input,STAR,FOLLOW_STAR_in_varargslist460); 
                                    tu=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist466); 
                                     
                                    				params.add( new PythonArgument( toDLTK( tu ), PythonArgument.STAR ) ); 
                                    			
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:251:15: ( COMMA DOUBLESTAR t1= NAME )?
                                    int alt8=2;
                                    int LA8_0 = input.LA(1);

                                    if ( (LA8_0==COMMA) ) {
                                        alt8=1;
                                    }
                                    switch (alt8) {
                                        case 1 :
                                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:251:16: COMMA DOUBLESTAR t1= NAME
                                            {
                                            match(input,COMMA,FOLLOW_COMMA_in_varargslist501); 
                                            match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist503); 
                                            t1=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist509); 
                                            	
                                            					params.add( new PythonArgument( toDLTK( t1 ), PythonArgument.DOUBLESTAR ) );
                                            				

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 2 :
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:256:17: DOUBLESTAR t2= NAME
                                    {
                                    match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist551); 
                                    t2=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist557); 

                                                				params.add( new PythonArgument( toDLTK( t2 ), PythonArgument.DOUBLESTAR ) );
                                                			

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:262:4: STAR m= NAME ( COMMA DOUBLESTAR m1= NAME )?
                    {
                    match(input,STAR,FOLLOW_STAR_in_varargslist595); 
                    m=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist601); 
                     
                    			params.add( new PythonArgument( toDLTK( m ), PythonArgument.STAR ) );
                    		
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:266:2: ( COMMA DOUBLESTAR m1= NAME )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==COMMA) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:266:3: COMMA DOUBLESTAR m1= NAME
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_varargslist610); 
                            match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist615); 
                            m1=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist621); 

                            				params.add( new PythonArgument( toDLTK( m1 ), PythonArgument.DOUBLESTAR ) );
                            			

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:272:4: DOUBLESTAR m2= NAME
                    {
                    match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist635); 
                    m2=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist641); 

                    			params.add( new PythonArgument( toDLTK( m2 ), PythonArgument.DOUBLESTAR ) );
                    		

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "varargslist"


    // $ANTLR start "defparameter"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:279:1: defparameter[ ArgumentList params ] : lastParam= fpdef[ params ] ( ASSIGN initExpr= test )? ;
    public final void defparameter(ArgumentList params) throws RecognitionException {
        PythonArgument lastParam = null;

        Expression initExpr = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:279:36: (lastParam= fpdef[ params ] ( ASSIGN initExpr= test )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:280:2: lastParam= fpdef[ params ] ( ASSIGN initExpr= test )?
            {
            pushFollow(FOLLOW_fpdef_in_defparameter660);
            lastParam=fpdef(params);

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:281:2: ( ASSIGN initExpr= test )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==ASSIGN) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:281:3: ASSIGN initExpr= test
                    {
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_defparameter665); 
                    pushFollow(FOLLOW_test_in_defparameter671);
                    initExpr=test();

                    state._fsp--;

                    					
                    			if( lastParam != null ) {
                    				lastParam.assign( initExpr );
                    			}				
                    		

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "defparameter"


    // $ANTLR start "fpdef"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:292:1: fpdef[ ArgumentList params ] returns [ PythonArgument argument = null ] : (tu= NAME | LPAREN fplist[params] RPAREN );
    public final PythonArgument fpdef(ArgumentList params) throws RecognitionException {
        PythonArgument argument =  null;

        Token tu=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:292:73: (tu= NAME | LPAREN fplist[params] RPAREN )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==NAME) ) {
                alt14=1;
            }
            else if ( (LA14_0==LPAREN) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:293:6: tu= NAME
                    {
                    tu=(Token)match(input,NAME,FOLLOW_NAME_in_fpdef703); 

                    			argument = new PythonArgument( toDLTK( tu ) );
                    			params.add( argument );
                    		

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:298:3: LPAREN fplist[params] RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_fpdef714); 
                    pushFollow(FOLLOW_fplist_in_fpdef716);
                    fplist(params);

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_fpdef719); 

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return argument;
    }
    // $ANTLR end "fpdef"


    // $ANTLR start "fplist"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:302:1: fplist[ArgumentList params ] : fpdef[ params ] ( options {greedy=true; } : COMMA fpdef[ params ] )* ( COMMA )? ;
    public final void fplist(ArgumentList params) throws RecognitionException {
        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:302:30: ( fpdef[ params ] ( options {greedy=true; } : COMMA fpdef[ params ] )* ( COMMA )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:303:2: fpdef[ params ] ( options {greedy=true; } : COMMA fpdef[ params ] )* ( COMMA )?
            {
            pushFollow(FOLLOW_fpdef_in_fplist734);
            fpdef(params);

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:304:2: ( options {greedy=true; } : COMMA fpdef[ params ] )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==COMMA) ) {
                    int LA15_1 = input.LA(2);

                    if ( (LA15_1==LPAREN||LA15_1==NAME) ) {
                        alt15=1;
                    }


                }


                switch (alt15) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:305:3: COMMA fpdef[ params ]
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_fplist752); 
            	    pushFollow(FOLLOW_fpdef_in_fplist754);
            	    fpdef(params);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:307:2: ( COMMA )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==COMMA) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:307:3: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_fplist763); 

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "fplist"


    // $ANTLR start "simple_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:315:1: simple_stmt[ List stmts ] : small_stmt[ stmts ] ( options {greedy=true; } : SEMI small_stmt[ stmts ] )* ( SEMI )? NEWLINE ;
    public final void simple_stmt(List stmts) throws RecognitionException {
        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:315:27: ( small_stmt[ stmts ] ( options {greedy=true; } : SEMI small_stmt[ stmts ] )* ( SEMI )? NEWLINE )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:316:2: small_stmt[ stmts ] ( options {greedy=true; } : SEMI small_stmt[ stmts ] )* ( SEMI )? NEWLINE
            {
            pushFollow(FOLLOW_small_stmt_in_simple_stmt781);
            small_stmt(stmts);

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:317:2: ( options {greedy=true; } : SEMI small_stmt[ stmts ] )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==SEMI) ) {
                    int LA17_1 = input.LA(2);

                    if ( (LA17_1==LPAREN||LA17_1==NAME||(LA17_1>=PLUS && LA17_1<=MINUS)||(LA17_1>=TILDE && LA17_1<=LBRACK)||LA17_1==LCURLY||(LA17_1>=BACKQUOTE && LA17_1<=STRING)||(LA17_1>=70 && LA17_1<=79)||(LA17_1>=81 && LA17_1<=82)||LA17_1==84||LA17_1==95||LA17_1==97) ) {
                        alt17=1;
                    }


                }


                switch (alt17) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:318:3: SEMI small_stmt[ stmts ]
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_simple_stmt800); 
            	    pushFollow(FOLLOW_small_stmt_in_simple_stmt802);
            	    small_stmt(stmts);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:319:6: ( SEMI )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==SEMI) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:319:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_simple_stmt812); 

                    }
                    break;

            }

            match(input,NEWLINE,FOLLOW_NEWLINE_in_simple_stmt817); 

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "simple_stmt"


    // $ANTLR start "stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:321:4: stmt returns [ ArrayList statements = new ArrayList( ) ] : ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt ) ;
    public final ArrayList stmt() throws RecognitionException {
        ArrayList statements =  new ArrayList( );

        Statement compoundStatement = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:321:60: ( ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:322:2: ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt )
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:322:2: ( simple_stmt[ simpleStatements ] | compoundStatement= compound_stmt )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==LPAREN||LA19_0==NAME||(LA19_0>=PLUS && LA19_0<=MINUS)||(LA19_0>=TILDE && LA19_0<=LBRACK)||LA19_0==LCURLY||(LA19_0>=BACKQUOTE && LA19_0<=STRING)||(LA19_0>=70 && LA19_0<=79)||(LA19_0>=81 && LA19_0<=82)||LA19_0==84||LA19_0==95||LA19_0==97) ) {
                alt19=1;
            }
            else if ( (LA19_0==DECORATOR_S||LA19_0==69||LA19_0==85||(LA19_0>=88 && LA19_0<=90)||(LA19_0>=98 && LA19_0<=99)) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:323:3: simple_stmt[ simpleStatements ]
                    {

                    		List simpleStatements = new ArrayList();
                    		
                    pushFollow(FOLLOW_simple_stmt_in_stmt838);
                    simple_stmt(simpleStatements);

                    state._fsp--;

                     statements.addAll( simpleStatements ); 

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:328:3: compoundStatement= compound_stmt
                    {
                    pushFollow(FOLLOW_compound_stmt_in_stmt858);
                    compoundStatement=compound_stmt();

                    state._fsp--;

                     statements.add( compoundStatement ); 

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statements;
    }
    // $ANTLR end "stmt"


    // $ANTLR start "small_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:335:1: small_stmt[ List stmts ] returns [ Statement rstatement = null ] : (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt ) ;
    public final Statement small_stmt(List stmts) throws RecognitionException {
        Statement rstatement =  null;

        Expression statement1 = null;

        Statement statement2 = null;

        Statement statement3 = null;

        Statement statement4 = null;

        Statement statement5 = null;

        Statement statement6 = null;

        PythonGlobalStatement statement7 = null;

        Statement statement8 = null;

        PythonAssertStatement statement9 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:335:66: ( (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:336:2: (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt )
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:336:2: (statement1= expr_stmt | statement2= print_stmt | statement3= del_stmt | statement4= pass_stmt | statement5= flow_stmt | statement6= import_stmt | statement7= global_stmt | statement8= exec_stmt | statement9= assert_stmt )
            int alt20=9;
            switch ( input.LA(1) ) {
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
            case 97:
                {
                alt20=1;
                }
                break;
            case 70:
                {
                alt20=2;
                }
                break;
            case 71:
                {
                alt20=3;
                }
                break;
            case 72:
                {
                alt20=4;
                }
                break;
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
                {
                alt20=5;
                }
                break;
            case 78:
            case 79:
                {
                alt20=6;
                }
                break;
            case 81:
                {
                alt20=7;
                }
                break;
            case 82:
                {
                alt20=8;
                }
                break;
            case 84:
                {
                alt20=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:337:4: statement1= expr_stmt
                    {
                    pushFollow(FOLLOW_expr_stmt_in_small_stmt892);
                    statement1=expr_stmt();

                    state._fsp--;

                     rstatement = statement1; 

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:338:4: statement2= print_stmt
                    {
                    pushFollow(FOLLOW_print_stmt_in_small_stmt903);
                    statement2=print_stmt();

                    state._fsp--;

                     rstatement = statement2; 

                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:339:4: statement3= del_stmt
                    {
                    pushFollow(FOLLOW_del_stmt_in_small_stmt913);
                    statement3=del_stmt();

                    state._fsp--;

                     rstatement = statement3; 

                    }
                    break;
                case 4 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:340:4: statement4= pass_stmt
                    {
                    pushFollow(FOLLOW_pass_stmt_in_small_stmt923);
                    statement4=pass_stmt();

                    state._fsp--;

                     rstatement = statement4; 

                    }
                    break;
                case 5 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:341:4: statement5= flow_stmt
                    {
                    pushFollow(FOLLOW_flow_stmt_in_small_stmt933);
                    statement5=flow_stmt();

                    state._fsp--;

                     rstatement = statement5; 

                    }
                    break;
                case 6 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:342:4: statement6= import_stmt
                    {
                    pushFollow(FOLLOW_import_stmt_in_small_stmt943);
                    statement6=import_stmt();

                    state._fsp--;

                     rstatement = statement6; 

                    }
                    break;
                case 7 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:343:4: statement7= global_stmt
                    {
                    pushFollow(FOLLOW_global_stmt_in_small_stmt953);
                    statement7=global_stmt();

                    state._fsp--;

                     rstatement = statement7; 

                    }
                    break;
                case 8 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:344:4: statement8= exec_stmt
                    {
                    pushFollow(FOLLOW_exec_stmt_in_small_stmt963);
                    statement8=exec_stmt();

                    state._fsp--;

                     rstatement = statement8; 

                    }
                    break;
                case 9 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:345:4: statement9= assert_stmt
                    {
                    pushFollow(FOLLOW_assert_stmt_in_small_stmt973);
                    statement9=assert_stmt();

                    state._fsp--;

                     rstatement = statement9; 

                    }
                    break;

            }


            		if( rstatement != null )
            			stmts.add( rstatement );
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return rstatement;
    }
    // $ANTLR end "small_stmt"


    // $ANTLR start "expr_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:353:1: expr_stmt returns [ Expression exp = null ] : exp0= testlist (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )? ;
    public final Expression expr_stmt() throws RecognitionException {
        Expression exp =  null;

        Token a=null;
        Expression exp0 = null;

        int type = 0;

        Expression right = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:353:45: (exp0= testlist (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:354:2: exp0= testlist (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )?
            {
            pushFollow(FOLLOW_testlist_in_expr_stmt1001);
            exp0=testlist();

            state._fsp--;

             exp = exp0; 
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:355:2: (type= augassign right= testlist | (a= ASSIGN right= testlist )+ )?
            int alt22=3;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=PLUSEQUAL && LA22_0<=DOUBLESLASHEQUAL)) ) {
                alt22=1;
            }
            else if ( (LA22_0==ASSIGN) ) {
                alt22=2;
            }
            switch (alt22) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:356:3: type= augassign right= testlist
                    {
                    pushFollow(FOLLOW_augassign_in_expr_stmt1015);
                    type=augassign();

                    state._fsp--;

                    pushFollow(FOLLOW_testlist_in_expr_stmt1024);
                    right=testlist();

                    state._fsp--;


                    				if( type != 0 ) {
                    		 			NotStrictAssignment e = new NotStrictAssignment( exp, type, right );
                    			    		exp = e;
                    				}
                    				else {
                    					exp = new Assignment( exp, right );
                    				}
                    		 	

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:368:3: (a= ASSIGN right= testlist )+
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:368:3: (a= ASSIGN right= testlist )+
                    int cnt21=0;
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==ASSIGN) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:369:4: a= ASSIGN right= testlist
                    	    {
                    	    a=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_expr_stmt1046); 
                    	    pushFollow(FOLLOW_testlist_in_expr_stmt1055);
                    	    right=testlist();

                    	    state._fsp--;


                    	    				if( type != 0 ) {
                    	    		 			NotStrictAssignment e = new NotStrictAssignment( exp, type, right );
                    	    			    		exp = e;
                    	    				}
                    	    				else {
                    	    					exp = new Assignment( exp, right );
                    	    				}
                    	    			

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt21 >= 1 ) break loop21;
                                EarlyExitException eee =
                                    new EarlyExitException(21, input);
                                throw eee;
                        }
                        cnt21++;
                    } while (true);


                    }
                    break;

            }


            			if( exp == null )
            				exp = new EmptyExpression();
            		

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "expr_stmt"


    // $ANTLR start "augassign"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:388:1: augassign returns [ int type = 0 ] : ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL );
    public final int augassign() throws RecognitionException {
        int type =  0;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:388:35: ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL )
            int alt23=12;
            switch ( input.LA(1) ) {
            case PLUSEQUAL:
                {
                alt23=1;
                }
                break;
            case MINUSEQUAL:
                {
                alt23=2;
                }
                break;
            case STAREQUAL:
                {
                alt23=3;
                }
                break;
            case SLASHEQUAL:
                {
                alt23=4;
                }
                break;
            case PERCENTEQUAL:
                {
                alt23=5;
                }
                break;
            case AMPEREQUAL:
                {
                alt23=6;
                }
                break;
            case VBAREQUAL:
                {
                alt23=7;
                }
                break;
            case CIRCUMFLEXEQUAL:
                {
                alt23=8;
                }
                break;
            case LEFTSHIFTEQUAL:
                {
                alt23=9;
                }
                break;
            case RIGHTSHIFTEQUAL:
                {
                alt23=10;
                }
                break;
            case DOUBLESTAREQUAL:
                {
                alt23=11;
                }
                break;
            case DOUBLESLASHEQUAL:
                {
                alt23=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:389:2: PLUSEQUAL
                    {
                    match(input,PLUSEQUAL,FOLLOW_PLUSEQUAL_in_augassign1088); 

                    			type = Expression.E_PLUS_ASSIGN;
                    		

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:393:4: MINUSEQUAL
                    {
                    match(input,MINUSEQUAL,FOLLOW_MINUSEQUAL_in_augassign1097); 
                    					
                    			type = Expression.E_MINUS_ASSIGN;		
                    		

                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:397:4: STAREQUAL
                    {
                    match(input,STAREQUAL,FOLLOW_STAREQUAL_in_augassign1107); 

                    			type = Expression.E_MULT_ASSIGN;
                    		

                    }
                    break;
                case 4 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:401:4: SLASHEQUAL
                    {
                    match(input,SLASHEQUAL,FOLLOW_SLASHEQUAL_in_augassign1116); 

                    			type = Expression.E_DIV_ASSIGN;
                    		

                    }
                    break;
                case 5 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:405:4: PERCENTEQUAL
                    {
                    match(input,PERCENTEQUAL,FOLLOW_PERCENTEQUAL_in_augassign1126); 

                    			type = Expression.E_MOD_ASSIGN;
                    		

                    }
                    break;
                case 6 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:409:4: AMPEREQUAL
                    {
                    match(input,AMPEREQUAL,FOLLOW_AMPEREQUAL_in_augassign1135); 

                    			type = Expression.E_BAND_ASSIGN;
                    		

                    }
                    break;
                case 7 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:413:4: VBAREQUAL
                    {
                    match(input,VBAREQUAL,FOLLOW_VBAREQUAL_in_augassign1144); 

                    			type = Expression.E_BOR_ASSIGN;
                    		

                    }
                    break;
                case 8 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:417:4: CIRCUMFLEXEQUAL
                    {
                    match(input,CIRCUMFLEXEQUAL,FOLLOW_CIRCUMFLEXEQUAL_in_augassign1153); 

                    			type = Expression.E_BXOR_ASSIGN;
                    		

                    }
                    break;
                case 9 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:421:4: LEFTSHIFTEQUAL
                    {
                    match(input,LEFTSHIFTEQUAL,FOLLOW_LEFTSHIFTEQUAL_in_augassign1162); 

                    			type = Expression.E_SL_ASSIGN;
                    		

                    }
                    break;
                case 10 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:425:4: RIGHTSHIFTEQUAL
                    {
                    match(input,RIGHTSHIFTEQUAL,FOLLOW_RIGHTSHIFTEQUAL_in_augassign1171); 

                    			type = Expression.E_SR_ASSIGN;
                    		

                    }
                    break;
                case 11 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:429:4: DOUBLESTAREQUAL
                    {
                    match(input,DOUBLESTAREQUAL,FOLLOW_DOUBLESTAREQUAL_in_augassign1180); 

                    			type = Expression.E_DOUBLESTAR_ASSIGN;
                    		

                    }
                    break;
                case 12 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:433:4: DOUBLESLASHEQUAL
                    {
                    match(input,DOUBLESLASHEQUAL,FOLLOW_DOUBLESLASHEQUAL_in_augassign1189); 

                    			type = Expression.E_DOUBLEDIV_ASSIGN;
                    		

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return type;
    }
    // $ANTLR end "augassign"


    // $ANTLR start "print_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:439:1: print_stmt returns [ Statement statement = null ] : tu= 'print' (ex= testlist | RIGHTSHIFT ex= testlist )? ;
    public final Statement print_stmt() throws RecognitionException {
        Statement statement =  null;

        Token tu=null;
        Expression ex = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:439:50: (tu= 'print' (ex= testlist | RIGHTSHIFT ex= testlist )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:441:9: tu= 'print' (ex= testlist | RIGHTSHIFT ex= testlist )?
            {
            tu=(Token)match(input,70,FOLLOW_70_in_print_stmt1223); 
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:442:2: (ex= testlist | RIGHTSHIFT ex= testlist )?
            int alt24=3;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LPAREN||LA24_0==NAME||(LA24_0>=PLUS && LA24_0<=MINUS)||(LA24_0>=TILDE && LA24_0<=LBRACK)||LA24_0==LCURLY||(LA24_0>=BACKQUOTE && LA24_0<=STRING)||LA24_0==95||LA24_0==97) ) {
                alt24=1;
            }
            else if ( (LA24_0==RIGHTSHIFT) ) {
                alt24=2;
            }
            switch (alt24) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:443:3: ex= testlist
                    {
                    pushFollow(FOLLOW_testlist_in_print_stmt1234);
                    ex=testlist();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:444:12: RIGHTSHIFT ex= testlist
                    {
                    match(input,RIGHTSHIFT,FOLLOW_RIGHTSHIFT_in_print_stmt1247); 
                    pushFollow(FOLLOW_testlist_in_print_stmt1263);
                    ex=testlist();

                    state._fsp--;


                    }
                    break;

            }


            		statement = new PrintExpression( toDLTK( tu ), ex );
            		if( ex != null ) {
            			statement.setEnd(ex.sourceEnd());
            		}
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "print_stmt"


    // $ANTLR start "del_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:456:1: del_stmt returns [ Statement statement = null ] : sa= 'del' tu= exprlist ;
    public final Statement del_stmt() throws RecognitionException {
        Statement statement =  null;

        Token sa=null;
        PythonTestListExpression tu = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:456:48: (sa= 'del' tu= exprlist )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:457:2: sa= 'del' tu= exprlist
            {
            sa=(Token)match(input,71,FOLLOW_71_in_del_stmt1294); 
            pushFollow(FOLLOW_exprlist_in_del_stmt1302);
            tu=exprlist();

            state._fsp--;


            		statement = new PythonDelStatement( toDLTK( sa ), tu );
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "del_stmt"


    // $ANTLR start "pass_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:465:1: pass_stmt returns [ Statement statement = null] : tu= 'pass' ;
    public final Statement pass_stmt() throws RecognitionException {
        Statement statement =  null;

        Token tu=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:465:48: (tu= 'pass' )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:466:2: tu= 'pass'
            {
            tu=(Token)match(input,72,FOLLOW_72_in_pass_stmt1325); 

            		statement = new EmptyStatement( toDLTK( tu ) );
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "pass_stmt"


    // $ANTLR start "flow_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:473:1: flow_stmt returns [ Statement statement = null ] : (statement0= break_stmt | statement1= continue_stmt | statement2= return_stmt | statement3= raise_stmt | statement4= yield_stmt );
    public final Statement flow_stmt() throws RecognitionException {
        Statement statement =  null;

        Statement statement0 = null;

        Statement statement1 = null;

        Statement statement2 = null;

        PythonRaiseStatement statement3 = null;

        PythonYieldStatement statement4 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:473:49: (statement0= break_stmt | statement1= continue_stmt | statement2= return_stmt | statement3= raise_stmt | statement4= yield_stmt )
            int alt25=5;
            switch ( input.LA(1) ) {
            case 73:
                {
                alt25=1;
                }
                break;
            case 74:
                {
                alt25=2;
                }
                break;
            case 75:
                {
                alt25=3;
                }
                break;
            case 77:
                {
                alt25=4;
                }
                break;
            case 76:
                {
                alt25=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:474:4: statement0= break_stmt
                    {
                    pushFollow(FOLLOW_break_stmt_in_flow_stmt1351);
                    statement0=break_stmt();

                    state._fsp--;

                     statement = statement0; 

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:475:4: statement1= continue_stmt
                    {
                    pushFollow(FOLLOW_continue_stmt_in_flow_stmt1362);
                    statement1=continue_stmt();

                    state._fsp--;

                     statement = statement1; 

                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:476:4: statement2= return_stmt
                    {
                    pushFollow(FOLLOW_return_stmt_in_flow_stmt1372);
                    statement2=return_stmt();

                    state._fsp--;

                     statement = statement2; 

                    }
                    break;
                case 4 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:477:4: statement3= raise_stmt
                    {
                    pushFollow(FOLLOW_raise_stmt_in_flow_stmt1382);
                    statement3=raise_stmt();

                    state._fsp--;

                     statement = statement3; 

                    }
                    break;
                case 5 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:478:4: statement4= yield_stmt
                    {
                    pushFollow(FOLLOW_yield_stmt_in_flow_stmt1392);
                    statement4=yield_stmt();

                    state._fsp--;

                     statement = statement4; 

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "flow_stmt"


    // $ANTLR start "break_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:481:1: break_stmt returns [ Statement statement = null ] : ta= 'break' ;
    public final Statement break_stmt() throws RecognitionException {
        Statement statement =  null;

        Token ta=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:481:50: (ta= 'break' )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:482:2: ta= 'break'
            {
            ta=(Token)match(input,73,FOLLOW_73_in_break_stmt1413); 

            			statement = new BreakStatement( toDLTK( ta ), null, toDLTK(ta) );
            		

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "break_stmt"


    // $ANTLR start "continue_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:488:1: continue_stmt returns [ Statement statement = null ] : ta= 'continue' ;
    public final Statement continue_stmt() throws RecognitionException {
        Statement statement =  null;

        Token ta=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:488:53: (ta= 'continue' )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:489:2: ta= 'continue'
            {
            ta=(Token)match(input,74,FOLLOW_74_in_continue_stmt1437); 

            			statement = new ContinueStatement( toDLTK( ta ), null, toDLTK(ta) );
            		

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "continue_stmt"


    // $ANTLR start "return_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:495:1: return_stmt returns [ Statement statement = null ] : ra= 'return' (tu= testlist )? ;
    public final Statement return_stmt() throws RecognitionException {
        Statement statement =  null;

        Token ra=null;
        Expression tu = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:495:51: (ra= 'return' (tu= testlist )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:496:2: ra= 'return' (tu= testlist )?
            {
            ra=(Token)match(input,75,FOLLOW_75_in_return_stmt1462); 
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:497:3: (tu= testlist )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==LPAREN||LA26_0==NAME||(LA26_0>=PLUS && LA26_0<=MINUS)||(LA26_0>=TILDE && LA26_0<=LBRACK)||LA26_0==LCURLY||(LA26_0>=BACKQUOTE && LA26_0<=STRING)||LA26_0==95||LA26_0==97) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:497:5: tu= testlist
                    {
                    pushFollow(FOLLOW_testlist_in_return_stmt1473);
                    tu=testlist();

                    state._fsp--;


                    }
                    break;

            }


            			DLTKToken ret = toDLTK(ra);
            			int end = ret.getColumn()+ret.getText().length();
            			if( tu != null ) {
            				end = tu.sourceEnd();
            			}
            			statement = new ReturnStatement( toDLTK( ra ), tu , end );
            		

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "return_stmt"


    // $ANTLR start "yield_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:509:1: yield_stmt returns [ PythonYieldStatement statement = null ] : tu= 'yield' r= testlist ;
    public final PythonYieldStatement yield_stmt() throws RecognitionException {
        PythonYieldStatement statement =  null;

        Token tu=null;
        Expression r = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:509:61: (tu= 'yield' r= testlist )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:510:2: tu= 'yield' r= testlist
            {
            tu=(Token)match(input,76,FOLLOW_76_in_yield_stmt1501); 
            pushFollow(FOLLOW_testlist_in_yield_stmt1508);
            r=testlist();

            state._fsp--;


            		statement = new PythonYieldStatement( toDLTK( tu ), r );
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "yield_stmt"


    // $ANTLR start "raise_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:517:1: raise_stmt returns [ PythonRaiseStatement statement = null ] : tu= 'raise' (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )? ;
    public final PythonRaiseStatement raise_stmt() throws RecognitionException {
        PythonRaiseStatement statement =  null;

        Token tu=null;
        Expression e1 = null;

        Expression e2 = null;

        Expression e3 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:517:61: (tu= 'raise' (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:518:2: tu= 'raise' (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )?
            {
            tu=(Token)match(input,77,FOLLOW_77_in_raise_stmt1530); 

            		statement = new PythonRaiseStatement( toDLTK( tu ) );
            		int end = -1;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:523:2: (e1= test ( COMMA e2= test ( COMMA e3= test )? )? )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==LPAREN||LA29_0==NAME||(LA29_0>=PLUS && LA29_0<=MINUS)||(LA29_0>=TILDE && LA29_0<=LBRACK)||LA29_0==LCURLY||(LA29_0>=BACKQUOTE && LA29_0<=STRING)||LA29_0==95||LA29_0==97) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:524:3: e1= test ( COMMA e2= test ( COMMA e3= test )? )?
                    {
                    pushFollow(FOLLOW_test_in_raise_stmt1544);
                    e1=test();

                    state._fsp--;


                    			statement.acceptExpression1( e1 );
                    			if( e1 != null && e1.sourceEnd() > end ) {
                    				end = e1.sourceEnd();
                    			}
                    		
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:531:3: ( COMMA e2= test ( COMMA e3= test )? )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==COMMA) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:531:5: COMMA e2= test ( COMMA e3= test )?
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_raise_stmt1555); 
                            pushFollow(FOLLOW_test_in_raise_stmt1564);
                            e2=test();

                            state._fsp--;


                            				statement.acceptExpression2( e2 );
                            				if( e2 != null && e2.sourceEnd() > end ) {
                            					end = e2.sourceEnd();
                            				}
                            			
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:539:4: ( COMMA e3= test )?
                            int alt27=2;
                            int LA27_0 = input.LA(1);

                            if ( (LA27_0==COMMA) ) {
                                alt27=1;
                            }
                            switch (alt27) {
                                case 1 :
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:539:6: COMMA e3= test
                                    {
                                    match(input,COMMA,FOLLOW_COMMA_in_raise_stmt1577); 
                                    pushFollow(FOLLOW_test_in_raise_stmt1587);
                                    e3=test();

                                    state._fsp--;


                                    		   			statement.acceptExpression3( e3 );
                                    		   			if( e3 != null && e3.sourceEnd() > end ) {
                                    						end = e3.sourceEnd();
                                    					}
                                    		   		

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
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "raise_stmt"


    // $ANTLR start "import_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:553:1: import_stmt returns [ Statement statement = null ] : ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) ) ;
    public final Statement import_stmt() throws RecognitionException {
        Statement statement =  null;

        Token tu=null;
        Token r=null;
        Expression expr0 = null;

        Token moduleName = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:553:52: ( ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:554:6: ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) )
            {

                		Expression impExpr;
                		String impName;
                		String impName2;
                		//Token moduleName;		
                		
                		PythonTestListExpression importNames = new PythonTestListExpression();    		
                	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:563:6: ( (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* ) | r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) ) )
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==78) ) {
                alt33=1;
            }
            else if ( (LA33_0==79) ) {
                alt33=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }
            switch (alt33) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:564:7: (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:564:7: (tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )* )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:565:8: tu= 'import' expr0= module_imp ( COMMA expr0= module_imp )*
                    {
                    tu=(Token)match(input,78,FOLLOW_78_in_import_stmt1704); 
                    	    	
                        				statement = new PythonImportStatement( toDLTK( tu ), importNames );
                        				//Expression expr0 = null;
                        			
                    pushFollow(FOLLOW_module_imp_in_import_stmt1726);
                    expr0=module_imp();

                    state._fsp--;


                        					importNames.setStart(expr0.sourceStart());
                        					importNames.setEnd(expr0.sourceEnd());
                        					importNames.addExpression( expr0 );
                        					if( expr0.sourceEnd() > statement.sourceEnd() ) {
                        						statement.setEnd( expr0.sourceEnd() );
                        					}
                        				
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:580:8: ( COMMA expr0= module_imp )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==COMMA) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:580:10: COMMA expr0= module_imp
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_import_stmt1755); 
                    	    pushFollow(FOLLOW_module_imp_in_import_stmt1769);
                    	    expr0=module_imp();

                    	    state._fsp--;


                    	        					importNames.addExpression( expr0 );
                    	        					importNames.setEnd( expr0.sourceEnd());
                    	        					if( expr0.sourceEnd() > statement.sourceEnd() ) {
                    	        						statement.setEnd( expr0.sourceEnd() );
                    	        					}
                    	        				

                    	    }
                    	    break;

                    	default :
                    	    break loop30;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:592:3: r= 'from' moduleName= dot_name 'import' ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) )
                    {
                    r=(Token)match(input,79,FOLLOW_79_in_import_stmt1805); 
                    pushFollow(FOLLOW_dot_name_in_import_stmt1813);
                    moduleName=dot_name();

                    state._fsp--;

                    match(input,78,FOLLOW_78_in_import_stmt1820); 
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:596:3: ( (expr0= module_imp ( COMMA expr0= module_imp )* ) | ( STAR ) )
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==NAME) ) {
                        alt32=1;
                    }
                    else if ( (LA32_0==STAR) ) {
                        alt32=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 32, 0, input);

                        throw nvae;
                    }
                    switch (alt32) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:597:11: (expr0= module_imp ( COMMA expr0= module_imp )* )
                            {
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:597:11: (expr0= module_imp ( COMMA expr0= module_imp )* )
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:598:6: expr0= module_imp ( COMMA expr0= module_imp )*
                            {

                            						//moduleName.setColumn(moduleName.getColumn()-1);
                                    				statement = new PythonImportFromStatement( toDLTK( r ), new VariableReference( toDLTK( moduleName ) ), importNames );
                                    				//Expression expr0 = null;
                                    			
                            pushFollow(FOLLOW_module_imp_in_import_stmt1860);
                            expr0=module_imp();

                            state._fsp--;


                                						importNames.setStart(expr0.sourceStart());
                                						importNames.setEnd(expr0.sourceEnd());
                                						importNames.addExpression( expr0 );
                                						if( expr0.sourceEnd() > statement.sourceEnd() ) {
                                							statement.setEnd( expr0.sourceEnd() );
                                						}
                                					
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:612:9: ( COMMA expr0= module_imp )*
                            loop31:
                            do {
                                int alt31=2;
                                int LA31_0 = input.LA(1);

                                if ( (LA31_0==COMMA) ) {
                                    alt31=1;
                                }


                                switch (alt31) {
                            	case 1 :
                            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:612:11: COMMA expr0= module_imp
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_import_stmt1883); 
                            	    pushFollow(FOLLOW_module_imp_in_import_stmt1898);
                            	    expr0=module_imp();

                            	    state._fsp--;


                            	        							importNames.addExpression( expr0 );
                            	        							importNames.setEnd( expr0.sourceEnd());
                            	        							if( expr0.sourceEnd() > statement.sourceEnd() ) {
                            	        								statement.setEnd( expr0.sourceEnd() );
                            	        							}
                            	        						

                            	    }
                            	    break;

                            	default :
                            	    break loop31;
                                }
                            } while (true);


                            }


                            }
                            break;
                        case 2 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:624:8: ( STAR )
                            {
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:624:8: ( STAR )
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:625:5: STAR
                            {
                            match(input,STAR,FOLLOW_STAR_in_import_stmt1955); 

                            					//moduleName.setColumn(moduleName.getColumn()-1);
                            					statement = new PythonImportFromStatement( toDLTK( r ), new VariableReference( toDLTK( moduleName ) ), new PythonAllImportExpression( ) );
                            				

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
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "import_stmt"


    // $ANTLR start "dotted_name"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:635:1: dotted_name returns [ Token token = null ] : n= NAME ( DOT n2= NAME )+ ;
    public final Token dotted_name() throws RecognitionException {
        Token token =  null;

        Token n=null;
        Token n2=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:635:43: (n= NAME ( DOT n2= NAME )+ )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:636:2: n= NAME ( DOT n2= NAME )+
            {
            		
            		String value = "";
            	
            n=(Token)match(input,NAME,FOLLOW_NAME_in_dotted_name2005); 

            		value += n.getText();
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:643:2: ( DOT n2= NAME )+
            int cnt34=0;
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==DOT) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:644:3: DOT n2= NAME
            	    {
            	    match(input,DOT,FOLLOW_DOT_in_dotted_name2015); 
            	    n2=(Token)match(input,NAME,FOLLOW_NAME_in_dotted_name2022); 

            	    			value += "." + n2.getText();
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt34 >= 1 ) break loop34;
                        EarlyExitException eee =
                            new EarlyExitException(34, input);
                        throw eee;
                }
                cnt34++;
            } while (true);


            		token = new CommonToken( n );
            		token.setText( value );
            		//token.setColumn( n.getColumn() );
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return token;
    }
    // $ANTLR end "dotted_name"


    // $ANTLR start "dot_name"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:657:1: dot_name returns [ Token token = null ] : ( (moduleName1= dotted_name ) | (moduleName2= NAME ) );
    public final Token dot_name() throws RecognitionException {
        Token token =  null;

        Token moduleName2=null;
        Token moduleName1 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:657:40: ( (moduleName1= dotted_name ) | (moduleName2= NAME ) )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==NAME) ) {
                int LA35_1 = input.LA(2);

                if ( (LA35_1==NEWLINE||LA35_1==LPAREN||LA35_1==COMMA||LA35_1==SEMI||LA35_1==78||LA35_1==80) ) {
                    alt35=2;
                }
                else if ( (LA35_1==DOT) ) {
                    alt35=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:658:2: (moduleName1= dotted_name )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:658:2: (moduleName1= dotted_name )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:659:3: moduleName1= dotted_name
                    {
                    pushFollow(FOLLOW_dotted_name_in_dot_name2057);
                    moduleName1=dotted_name();

                    state._fsp--;


                    	    		token = moduleName1;
                    	    	

                    }


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:665:2: (moduleName2= NAME )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:665:2: (moduleName2= NAME )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:666:3: moduleName2= NAME
                    {
                    moduleName2=(Token)match(input,NAME,FOLLOW_NAME_in_dot_name2077); 

                    			token = moduleName2;
                    		

                    }


                    		if( token != null ) {
                    			//int column = token.getColumn();
                    			//token.setColumn( column -1 );
                    		}
                    	

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return token;
    }
    // $ANTLR end "dot_name"


    // $ANTLR start "module_imp"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:678:1: module_imp returns [ Expression expr = null ] : impName= dot_name ( 'as' as= NAME )? ;
    public final Expression module_imp() throws RecognitionException {
        Expression expr =  null;

        Token as=null;
        Token impName = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:678:46: (impName= dot_name ( 'as' as= NAME )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:679:2: impName= dot_name ( 'as' as= NAME )?
            {
            pushFollow(FOLLOW_dot_name_in_module_imp2107);
            impName=dot_name();

            state._fsp--;


            		expr = new PythonImportExpression( toDLTK( impName ) );
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:683:2: ( 'as' as= NAME )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==80) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:684:3: 'as' as= NAME
                    {
                    match(input,80,FOLLOW_80_in_module_imp2119); 
                    as=(Token)match(input,NAME,FOLLOW_NAME_in_module_imp2126); 

                    				expr = new PythonImportAsExpression( toDLTK( impName ), toDLTK( as ) );
                    			

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return expr;
    }
    // $ANTLR end "module_imp"


    // $ANTLR start "global_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:693:1: global_stmt returns [ PythonGlobalStatement statement = null; ] : w= 'global' n= NAME ( COMMA n= NAME )* ;
    public final PythonGlobalStatement global_stmt() throws RecognitionException {
        PythonGlobalStatement statement =  null;;

        Token w=null;
        Token n=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:693:64: (w= 'global' n= NAME ( COMMA n= NAME )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:694:3: w= 'global' n= NAME ( COMMA n= NAME )*
            {
            w=(Token)match(input,81,FOLLOW_81_in_global_stmt2157); 
             statement = new PythonGlobalStatement(toDLTK(w)); 
            n=(Token)match(input,NAME,FOLLOW_NAME_in_global_stmt2167); 
             if(n!=null) statement.add(toDLTK(n)); 
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:696:3: ( COMMA n= NAME )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==COMMA) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:696:4: COMMA n= NAME
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_global_stmt2174); 
            	    n=(Token)match(input,NAME,FOLLOW_NAME_in_global_stmt2180); 
            	     if(n!= null) statement.add(toDLTK(n)); 

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "global_stmt"


    // $ANTLR start "exec_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:700:1: exec_stmt returns [ Statement statement = null] : e= 'exec' ex= expr ( 'in' ex= test ( COMMA ex= test )? )? ;
    public final Statement exec_stmt() throws RecognitionException {
        Statement statement =  null;

        Token e=null;
        Expression ex = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:700:49: (e= 'exec' ex= expr ( 'in' ex= test ( COMMA ex= test )? )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:701:2: e= 'exec' ex= expr ( 'in' ex= test ( COMMA ex= test )? )?
            {
            e=(Token)match(input,82,FOLLOW_82_in_exec_stmt2208); 
            pushFollow(FOLLOW_expr_in_exec_stmt2214);
            ex=expr();

            state._fsp--;

             statement = new ExecStatement(this.converter.convert(e), ex); 
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:702:2: ( 'in' ex= test ( COMMA ex= test )? )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==83) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:702:3: 'in' ex= test ( COMMA ex= test )?
                    {
                    match(input,83,FOLLOW_83_in_exec_stmt2221); 
                    pushFollow(FOLLOW_test_in_exec_stmt2230);
                    ex=test();

                    state._fsp--;

                     ((ExecStatement)statement).acceptIn(ex); 
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:704:3: ( COMMA ex= test )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==COMMA) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:704:4: COMMA ex= test
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_exec_stmt2238); 
                            pushFollow(FOLLOW_test_in_exec_stmt2244);
                            ex=test();

                            state._fsp--;

                             ((ExecStatement)statement).acceptIn2(ex); 

                            }
                            break;

                    }


                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "exec_stmt"


    // $ANTLR start "assert_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:708:1: assert_stmt returns [ PythonAssertStatement statement = null ] : tu= 'assert' exp1= test ( COMMA exp2= test )? ;
    public final PythonAssertStatement assert_stmt() throws RecognitionException {
        PythonAssertStatement statement =  null;

        Token tu=null;
        Expression exp1 = null;

        Expression exp2 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:708:63: (tu= 'assert' exp1= test ( COMMA exp2= test )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:709:2: tu= 'assert' exp1= test ( COMMA exp2= test )?
            {
            tu=(Token)match(input,84,FOLLOW_84_in_assert_stmt2271); 
            pushFollow(FOLLOW_test_in_assert_stmt2278);
            exp1=test();

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:711:2: ( COMMA exp2= test )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==COMMA) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:711:4: COMMA exp2= test
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_assert_stmt2284); 
                    pushFollow(FOLLOW_test_in_assert_stmt2290);
                    exp2=test();

                    state._fsp--;


                    }
                    break;

            }


            			statement = new PythonAssertStatement( toDLTK( tu ), exp1, exp2 );
            		

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "assert_stmt"


    // $ANTLR start "compound_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:718:1: compound_stmt returns [ Statement statement = null ] : (statement0= if_stmt | statement1= while_stmt | statement2= for_stmt | statement3= try_stmt | statement4= funcdef | statement5= classdef | statement6= with_stmt );
    public final Statement compound_stmt() throws RecognitionException {
        Statement statement =  null;

        IfStatement statement0 = null;

        PythonWhileStatement statement1 = null;

        PythonForStatement statement2 = null;

        PythonTryStatement statement3 = null;

        MethodDeclaration statement4 = null;

        PythonClassDeclaration statement5 = null;

        Statement statement6 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:718:53: (statement0= if_stmt | statement1= while_stmt | statement2= for_stmt | statement3= try_stmt | statement4= funcdef | statement5= classdef | statement6= with_stmt )
            int alt41=7;
            switch ( input.LA(1) ) {
            case 85:
                {
                alt41=1;
                }
                break;
            case 88:
                {
                alt41=2;
                }
                break;
            case 89:
                {
                alt41=3;
                }
                break;
            case 90:
                {
                alt41=4;
                }
                break;
            case DECORATOR_S:
            case 69:
                {
                alt41=5;
                }
                break;
            case 99:
                {
                alt41=6;
                }
                break;
            case 98:
                {
                alt41=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }

            switch (alt41) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:719:2: statement0= if_stmt
                    {
                    pushFollow(FOLLOW_if_stmt_in_compound_stmt2318);
                    statement0=if_stmt();

                    state._fsp--;

                     statement = statement0; 

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:720:4: statement1= while_stmt
                    {
                    pushFollow(FOLLOW_while_stmt_in_compound_stmt2329);
                    statement1=while_stmt();

                    state._fsp--;

                     statement = statement1; 

                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:721:4: statement2= for_stmt
                    {
                    pushFollow(FOLLOW_for_stmt_in_compound_stmt2339);
                    statement2=for_stmt();

                    state._fsp--;

                     statement = statement2; 

                    }
                    break;
                case 4 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:722:4: statement3= try_stmt
                    {
                    pushFollow(FOLLOW_try_stmt_in_compound_stmt2349);
                    statement3=try_stmt();

                    state._fsp--;

                     statement = statement3; 

                    }
                    break;
                case 5 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:723:4: statement4= funcdef
                    {
                    pushFollow(FOLLOW_funcdef_in_compound_stmt2359);
                    statement4=funcdef();

                    state._fsp--;

                     statement = statement4; 

                    }
                    break;
                case 6 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:724:4: statement5= classdef
                    {
                    pushFollow(FOLLOW_classdef_in_compound_stmt2369);
                    statement5=classdef();

                    state._fsp--;

                     statement = statement5; 

                    }
                    break;
                case 7 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:725:4: statement6= with_stmt
                    {
                    pushFollow(FOLLOW_with_stmt_in_compound_stmt2379);
                    statement6=with_stmt();

                    state._fsp--;

                    statement = statement6; 

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "compound_stmt"


    // $ANTLR start "if_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:728:1: if_stmt returns [ IfStatement statement = null ] : is= 'if' mn= test COLON body= suite (z= 'elif' mn= test COLON body= suite )* ( 'else' COLON body= suite )? ;
    public final IfStatement if_stmt() throws RecognitionException {
        IfStatement statement =  null;

        Token is=null;
        Token z=null;
        Expression mn = null;

        Block body = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:728:49: (is= 'if' mn= test COLON body= suite (z= 'elif' mn= test COLON body= suite )* ( 'else' COLON body= suite )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:729:3: is= 'if' mn= test COLON body= suite (z= 'elif' mn= test COLON body= suite )* ( 'else' COLON body= suite )?
            {
            is=(Token)match(input,85,FOLLOW_85_in_if_stmt2402); 
            pushFollow(FOLLOW_test_in_if_stmt2411);
            mn=test();

            state._fsp--;

            match(input,COLON,FOLLOW_COLON_in_if_stmt2415); 
            pushFollow(FOLLOW_suite_in_if_stmt2424);
            body=suite();

            state._fsp--;

             
            			IfStatement t,base;
            			List ifs = new ArrayList();
            			statement = new IfStatement( toDLTK( is ), mn, body ); 
            			statement.setEnd(body.sourceEnd());
            			ifs.add(statement);
            			base = statement;
            			t = statement; 
            		
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:743:3: (z= 'elif' mn= test COLON body= suite )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==86) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:744:4: z= 'elif' mn= test COLON body= suite
            	    {
            	    z=(Token)match(input,86,FOLLOW_86_in_if_stmt2445); 
            	    pushFollow(FOLLOW_test_in_if_stmt2454);
            	    mn=test();

            	    state._fsp--;

            	    match(input,COLON,FOLLOW_COLON_in_if_stmt2460); 
            	    pushFollow(FOLLOW_suite_in_if_stmt2468);
            	    body=suite();

            	    state._fsp--;

            	     
            	    					IfStatement elseIfStatement = new IfStatement( toDLTK( z ), mn, body );
            	    					t.acceptElse( elseIfStatement );
            	    					t = elseIfStatement;
            	    					ifs.add(t);
            	    					for(Iterator i = ifs.iterator(); i.hasNext(); ((IfStatement)i.next()).setEnd(body.sourceEnd()));
            	    					base.setEnd(elseIfStatement.sourceEnd());
            	    				

            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:757:3: ( 'else' COLON body= suite )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==87) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:758:4: 'else' COLON body= suite
                    {
                    match(input,87,FOLLOW_87_in_if_stmt2489); 
                    match(input,COLON,FOLLOW_COLON_in_if_stmt2495); 
                    pushFollow(FOLLOW_suite_in_if_stmt2505);
                    body=suite();

                    state._fsp--;

                     
                    					t.setElse( body );
                    					for(Iterator i = ifs.iterator(); i.hasNext(); ((IfStatement)i.next()).setEnd(body.sourceEnd()));
                    				

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "if_stmt"


    // $ANTLR start "while_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:768:1: while_stmt returns [ PythonWhileStatement whileStatement = null ] : is= 'while' expression= test COLON body= suite ( 'else' COLON body= suite )? ;
    public final PythonWhileStatement while_stmt() throws RecognitionException {
        PythonWhileStatement whileStatement =  null;

        Token is=null;
        Expression expression = null;

        Block body = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:768:66: (is= 'while' expression= test COLON body= suite ( 'else' COLON body= suite )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:769:3: is= 'while' expression= test COLON body= suite ( 'else' COLON body= suite )?
            {
            is=(Token)match(input,88,FOLLOW_88_in_while_stmt2541); 
            pushFollow(FOLLOW_test_in_while_stmt2549);
            expression=test();

            state._fsp--;

            match(input,COLON,FOLLOW_COLON_in_while_stmt2553); 
            pushFollow(FOLLOW_suite_in_while_stmt2563);
            body=suite();

            state._fsp--;

             
            				whileStatement = new PythonWhileStatement( toDLTK( is ), expression, body ); 
            			
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:776:3: ( 'else' COLON body= suite )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==87) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:777:4: 'else' COLON body= suite
                    {
                    match(input,87,FOLLOW_87_in_while_stmt2577); 
                    match(input,COLON,FOLLOW_COLON_in_while_stmt2579); 
                    pushFollow(FOLLOW_suite_in_while_stmt2589);
                    body=suite();

                    state._fsp--;

                     
                    					whileStatement.setElseStatement( body ); 
                    				

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return whileStatement;
    }
    // $ANTLR end "while_stmt"


    // $ANTLR start "for_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:785:1: for_stmt returns [ PythonForStatement statement = null] : is= 'for' mains= exprlist 'in' conds= testlist COLON body= suite ( 'else' COLON body= suite )? ;
    public final PythonForStatement for_stmt() throws RecognitionException {
        PythonForStatement statement =  null;

        Token is=null;
        PythonTestListExpression mains = null;

        Expression conds = null;

        Block body = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:785:56: (is= 'for' mains= exprlist 'in' conds= testlist COLON body= suite ( 'else' COLON body= suite )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:786:3: is= 'for' mains= exprlist 'in' conds= testlist COLON body= suite ( 'else' COLON body= suite )?
            {
            is=(Token)match(input,89,FOLLOW_89_in_for_stmt2620); 
            pushFollow(FOLLOW_exprlist_in_for_stmt2629);
            mains=exprlist();

            state._fsp--;

            match(input,83,FOLLOW_83_in_for_stmt2634); 
            pushFollow(FOLLOW_testlist_in_for_stmt2642);
            conds=testlist();

            state._fsp--;

            match(input,COLON,FOLLOW_COLON_in_for_stmt2646); 
            pushFollow(FOLLOW_suite_in_for_stmt2655);
            body=suite();

            state._fsp--;


            				statement = new PythonForStatement( toDLTK( is ), mains, conds, body );
            			
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:795:3: ( 'else' COLON body= suite )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==87) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:796:4: 'else' COLON body= suite
                    {
                    match(input,87,FOLLOW_87_in_for_stmt2670); 
                    match(input,COLON,FOLLOW_COLON_in_for_stmt2676); 
                    pushFollow(FOLLOW_suite_in_for_stmt2686);
                    body=suite();

                    state._fsp--;


                    					statement.acceptElse( body );
                    				

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "for_stmt"


    // $ANTLR start "try_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:807:1: try_stmt returns [ PythonTryStatement statement = null ] : is= 'try' COLON body= suite ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) ) ;
    public final PythonTryStatement try_stmt() throws RecognitionException {
        PythonTryStatement statement =  null;

        Token is=null;
        Token ex_=null;
        Token elseT=null;
        Token fin=null;
        Block body = null;

        Expression t1 = null;

        Expression t2 = null;

        Block su = null;

        Block elseBlock = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:807:58: (is= 'try' COLON body= suite ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:808:2: is= 'try' COLON body= suite ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) )
            {
            is=(Token)match(input,90,FOLLOW_90_in_try_stmt2718); 
            match(input,COLON,FOLLOW_COLON_in_try_stmt2722); 

            			Token lastTok = is;
            			
                    		List catches = new ArrayList();
            		
            pushFollow(FOLLOW_suite_in_try_stmt2737);
            body=suite();

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:816:6: ( ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? ) | (fin= 'finally' COLON su= suite ) )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==91) ) {
                alt50=1;
            }
            else if ( (LA50_0==92) ) {
                alt50=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:817:9: ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:817:9: ( (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )? )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:818:3: (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+ ( (elseT= 'else' COLON elseBlock= suite ) )?
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:818:3: (ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite )+
                    int cnt48=0;
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==91) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:819:4: ex_= 'except' (t1= test ( COMMA t2= test )? )? COLON su= suite
                    	    {
                    	    ex_=(Token)match(input,91,FOLLOW_91_in_try_stmt2775); 
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:820:4: (t1= test ( COMMA t2= test )? )?
                    	    int alt47=2;
                    	    int LA47_0 = input.LA(1);

                    	    if ( (LA47_0==LPAREN||LA47_0==NAME||(LA47_0>=PLUS && LA47_0<=MINUS)||(LA47_0>=TILDE && LA47_0<=LBRACK)||LA47_0==LCURLY||(LA47_0>=BACKQUOTE && LA47_0<=STRING)||LA47_0==95||LA47_0==97) ) {
                    	        alt47=1;
                    	    }
                    	    switch (alt47) {
                    	        case 1 :
                    	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:821:5: t1= test ( COMMA t2= test )?
                    	            {
                    	            pushFollow(FOLLOW_test_in_try_stmt2790);
                    	            t1=test();

                    	            state._fsp--;

                    	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:822:5: ( COMMA t2= test )?
                    	            int alt46=2;
                    	            int LA46_0 = input.LA(1);

                    	            if ( (LA46_0==COMMA) ) {
                    	                alt46=1;
                    	            }
                    	            switch (alt46) {
                    	                case 1 :
                    	                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:822:6: COMMA t2= test
                    	                    {
                    	                    match(input,COMMA,FOLLOW_COMMA_in_try_stmt2797); 
                    	                    pushFollow(FOLLOW_test_in_try_stmt2803);
                    	                    t2=test();

                    	                    state._fsp--;


                    	                    }
                    	                    break;

                    	            }


                    	            }
                    	            break;

                    	    }

                    	    match(input,COLON,FOLLOW_COLON_in_try_stmt2816); 
                    	    pushFollow(FOLLOW_suite_in_try_stmt2825);
                    	    su=suite();

                    	    state._fsp--;


                    	    				lastTok = ex_;
                    	    				catches.add( new PythonExceptStatement( toDLTK( ex_ ), t1, t2, su ) );
                    	    			

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt48 >= 1 ) break loop48;
                                EarlyExitException eee =
                                    new EarlyExitException(48, input);
                                throw eee;
                        }
                        cnt48++;
                    } while (true);

                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:831:3: ( (elseT= 'else' COLON elseBlock= suite ) )?
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==87) ) {
                        alt49=1;
                    }
                    switch (alt49) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:832:4: (elseT= 'else' COLON elseBlock= suite )
                            {
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:832:4: (elseT= 'else' COLON elseBlock= suite )
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:832:6: elseT= 'else' COLON elseBlock= suite
                            {
                            elseT=(Token)match(input,87,FOLLOW_87_in_try_stmt2850); 
                            match(input,COLON,FOLLOW_COLON_in_try_stmt2856); 
                            pushFollow(FOLLOW_suite_in_try_stmt2866);
                            elseBlock=suite();

                            state._fsp--;


                            					lastTok = elseT;
                            				

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:844:9: (fin= 'finally' COLON su= suite )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:844:9: (fin= 'finally' COLON su= suite )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:845:10: fin= 'finally' COLON su= suite
                    {
                    fin=(Token)match(input,92,FOLLOW_92_in_try_stmt2977); 

                    	       		lastTok = fin;
                            	
                    match(input,COLON,FOLLOW_COLON_in_try_stmt3000); 
                    pushFollow(FOLLOW_suite_in_try_stmt3016);
                    su=suite();

                    state._fsp--;


                            		catches.add( new TryFinallyStatement( toDLTK( fin ), su ) );
                            	

                    }


                    }
                    break;

            }


            		statement = new PythonTryStatement( toDLTK( is ), body, catches );
            		statement.setElseStatement( elseBlock );
                    

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "try_stmt"


    // $ANTLR start "suite"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:862:1: suite returns [ Block statement = new Block() ] : ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT ) ;
    public final Block suite() throws RecognitionException {
        Block statement =  new Block();

        Token ind=null;
        Token d=null;
        ArrayList k = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:862:48: ( ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:863:4: ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT )
            {

            	  	ArrayList l = new ArrayList();		
            	  	int startPos = -1;
            	  	int endPos = -1;
            	  
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:868:4: ( simple_stmt[ l ] | NEWLINE ind= INDENT (k= stmt )+ d= DEDENT )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==LPAREN||LA52_0==NAME||(LA52_0>=PLUS && LA52_0<=MINUS)||(LA52_0>=TILDE && LA52_0<=LBRACK)||LA52_0==LCURLY||(LA52_0>=BACKQUOTE && LA52_0<=STRING)||(LA52_0>=70 && LA52_0<=79)||(LA52_0>=81 && LA52_0<=82)||LA52_0==84||LA52_0==95||LA52_0==97) ) {
                alt52=1;
            }
            else if ( (LA52_0==NEWLINE) ) {
                alt52=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:869:5: simple_stmt[ l ]
                    {
                    pushFollow(FOLLOW_simple_stmt_in_suite3074);
                    simple_stmt(l);

                    state._fsp--;


                    	  		Iterator i = l.iterator();
                    	  		while( i.hasNext()) {
                    	  			Statement sst = (Statement)i.next();
                    	  			if( sst != null ) {
                    	  				if( sst.sourceStart() < startPos || startPos == -1 ) {
                    	  					startPos = sst.sourceStart();
                    	  				} 
                    	  				if( sst.sourceEnd() > endPos || endPos == -1 ) {
                    	  					endPos = sst.sourceEnd();
                    	  				}
                    	  				statement.addStatement( sst );
                    	  			}
                    	  		}
                    	  	

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:886:5: NEWLINE ind= INDENT (k= stmt )+ d= DEDENT
                    {
                    match(input,NEWLINE,FOLLOW_NEWLINE_in_suite3095); 
                    ind=(Token)match(input,INDENT,FOLLOW_INDENT_in_suite3105); 

                    	  			if( ind != null ) {
                    	  				
                    		  			int s = converter.convert( ind ).getColumn();
                    		  			if( s < startPos && s != -1 ) {
                    		  				startPos = s;
                    		  			}
                    	  			}
                    	  			//ArrayList k;
                    	  		
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:898:5: (k= stmt )+
                    int cnt51=0;
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( ((LA51_0>=DECORATOR_S && LA51_0<=LPAREN)||LA51_0==NAME||(LA51_0>=PLUS && LA51_0<=MINUS)||(LA51_0>=TILDE && LA51_0<=LBRACK)||LA51_0==LCURLY||(LA51_0>=BACKQUOTE && LA51_0<=STRING)||(LA51_0>=69 && LA51_0<=79)||(LA51_0>=81 && LA51_0<=82)||(LA51_0>=84 && LA51_0<=85)||(LA51_0>=88 && LA51_0<=90)||LA51_0==95||(LA51_0>=97 && LA51_0<=99)) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:899:6: k= stmt
                    	    {
                    	    pushFollow(FOLLOW_stmt_in_suite3129);
                    	    k=stmt();

                    	    state._fsp--;


                    	    	  			//l.addAll( k );
                    	    	  			if( k != null ) {
                    	    		  			Iterator i = k.iterator();
                    	      					while( i.hasNext() ) {
                    	    	  					Statement sst = (Statement)i.next();
                    	    	  					if( sst != null ) {
                    	    	  						statement.addStatement( sst );
                    	    	  						if( sst.sourceStart() < startPos || startPos == -1 ) {
                    	    	  							startPos = sst.sourceStart();
                    	    	  						} 
                    	    	  						if( sst.sourceEnd() > endPos || endPos == -1 ) {
                    	    	  							endPos = sst.sourceEnd();
                    	    	  						}
                    	    	  					}
                    	    	  				}
                    	    	  			}
                    	    	  		

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt51 >= 1 ) break loop51;
                                EarlyExitException eee =
                                    new EarlyExitException(51, input);
                                throw eee;
                        }
                        cnt51++;
                    } while (true);

                    d=(Token)match(input,DEDENT,FOLLOW_DEDENT_in_suite3155); 

                    	  			if( d != null ) {
                    		  			int e = converter.convert( d ).getColumn() - 1;
                    		  			if( e > endPos ) {
                    		  				endPos = e;
                    		  			}
                    	  			}
                    	  		

                    }
                    break;

            }


            	   	//endPos -= 1;
            	   	//statement = new Block( startPos, endPos, l );
            	   	statement.setStart( startPos );
            	   	//if( endPos == -1 ) {
            	   	//	endPos = length;
            	   	//}
            	   	statement.setEnd( endPos );
            	  

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return statement;
    }
    // $ANTLR end "suite"


    // $ANTLR start "or_test"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:946:1: or_test returns [ Expression exp = null ] : exp0= and_test (r= 'or' v= and_test )* ;
    public final Expression or_test() throws RecognitionException {
        Expression exp =  null;

        Token r=null;
        Expression exp0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:946:43: (exp0= and_test (r= 'or' v= and_test )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:947:2: exp0= and_test (r= 'or' v= and_test )*
            {
            pushFollow(FOLLOW_and_test_in_or_test3205);
            exp0=and_test();

            state._fsp--;


            		exp = exp0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:951:2: (r= 'or' v= and_test )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==93) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:952:3: r= 'or' v= and_test
            	    {
            	    r=(Token)match(input,93,FOLLOW_93_in_or_test3219); 
            	    pushFollow(FOLLOW_and_test_in_or_test3228);
            	    v=and_test();

            	    state._fsp--;


            	    				exp = new BinaryExpression( exp, Expression.E_LOR, v);
            	    			

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "or_test"


    // $ANTLR start "test"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:960:1: test returns [ Expression exp = null ] : (exp0= or_test | exp0= lambdef );
    public final Expression test() throws RecognitionException {
        Expression exp =  null;

        Expression exp0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:960:39: (exp0= or_test | exp0= lambdef )
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==LPAREN||LA54_0==NAME||(LA54_0>=PLUS && LA54_0<=MINUS)||(LA54_0>=TILDE && LA54_0<=LBRACK)||LA54_0==LCURLY||(LA54_0>=BACKQUOTE && LA54_0<=STRING)||LA54_0==95) ) {
                alt54=1;
            }
            else if ( (LA54_0==97) ) {
                alt54=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:961:3: exp0= or_test
                    {
                    pushFollow(FOLLOW_or_test_in_test3257);
                    exp0=or_test();

                    state._fsp--;

                    exp = exp0;

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:963:4: exp0= lambdef
                    {
                    pushFollow(FOLLOW_lambdef_in_test3272);
                    exp0=lambdef();

                    state._fsp--;

                    exp = exp0;

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "test"


    // $ANTLR start "and_test"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:966:1: and_test returns [ Expression exp = null ] : exp0= not_test (m= 'and' v= not_test )* ;
    public final Expression and_test() throws RecognitionException {
        Expression exp =  null;

        Token m=null;
        Expression exp0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:966:43: (exp0= not_test (m= 'and' v= not_test )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:967:2: exp0= not_test (m= 'and' v= not_test )*
            {
            pushFollow(FOLLOW_not_test_in_and_test3295);
            exp0=not_test();

            state._fsp--;


            		exp = exp0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:971:2: (m= 'and' v= not_test )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==94) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:972:3: m= 'and' v= not_test
            	    {
            	    m=(Token)match(input,94,FOLLOW_94_in_and_test3309); 
            	    pushFollow(FOLLOW_not_test_in_and_test3318);
            	    v=not_test();

            	    state._fsp--;


            	    				exp = new BinaryExpression( exp, Expression.E_LAND,v);
            	    			

            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "and_test"


    // $ANTLR start "not_test"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:980:1: not_test returns [ Expression exp = null ] : ( (n= 'not' v= not_test ) | exp0= comparison );
    public final Expression not_test() throws RecognitionException {
        Expression exp =  null;

        Token n=null;
        Expression v = null;

        Expression exp0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:980:43: ( (n= 'not' v= not_test ) | exp0= comparison )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==95) ) {
                alt56=1;
            }
            else if ( (LA56_0==LPAREN||LA56_0==NAME||(LA56_0>=PLUS && LA56_0<=MINUS)||(LA56_0>=TILDE && LA56_0<=LBRACK)||LA56_0==LCURLY||(LA56_0>=BACKQUOTE && LA56_0<=STRING)) ) {
                alt56=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:981:2: (n= 'not' v= not_test )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:981:2: (n= 'not' v= not_test )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:982:3: n= 'not' v= not_test
                    {
                    n=(Token)match(input,95,FOLLOW_95_in_not_test3351); 
                    pushFollow(FOLLOW_not_test_in_not_test3360);
                    v=not_test();

                    state._fsp--;


                    				exp = new UnaryExpression( toDLTK( n ), Expression.E_LNOT, v );
                    			

                    }


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:988:4: exp0= comparison
                    {
                    pushFollow(FOLLOW_comparison_in_not_test3379);
                    exp0=comparison();

                    state._fsp--;

                     exp = exp0;

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "not_test"


    // $ANTLR start "comparison"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:991:1: comparison returns [ Expression exp = null ] : exp0= expr (tu= comp_op v= expr )* ;
    public final Expression comparison() throws RecognitionException {
        Expression exp =  null;

        Expression exp0 = null;

        int tu = 0;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:991:45: (exp0= expr (tu= comp_op v= expr )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:992:2: exp0= expr (tu= comp_op v= expr )*
            {
            pushFollow(FOLLOW_expr_in_comparison3402);
            exp0=expr();

            state._fsp--;


            		exp = exp0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:996:2: (tu= comp_op v= expr )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( ((LA57_0>=LESS && LA57_0<=NOTEQUAL)||LA57_0==83||(LA57_0>=95 && LA57_0<=96)) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:997:3: tu= comp_op v= expr
            	    {
            	    pushFollow(FOLLOW_comp_op_in_comparison3416);
            	    tu=comp_op();

            	    state._fsp--;

            	    pushFollow(FOLLOW_expr_in_comparison3425);
            	    v=expr();

            	    state._fsp--;


            	    				exp = new BinaryExpression( exp, tu, v );
            	    			

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "comparison"


    // $ANTLR start "comp_op"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1005:1: comp_op returns [ int t = Expression.E_EMPTY ] : (t1= LESS | t2= GREATER | t3= EQUAL | t4= GREATEREQUAL | t5= LESSEQUAL | t6= ALT_NOTEQUAL | t7= NOTEQUAL | t8= 'in' | t9= 'not' 'in' | t10= 'is' | t11= 'is' 'not' );
    public final int comp_op() throws RecognitionException {
        int t =  Expression.E_EMPTY;

        Token t1=null;
        Token t2=null;
        Token t3=null;
        Token t4=null;
        Token t5=null;
        Token t6=null;
        Token t7=null;
        Token t8=null;
        Token t9=null;
        Token t10=null;
        Token t11=null;

        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1005:48: (t1= LESS | t2= GREATER | t3= EQUAL | t4= GREATEREQUAL | t5= LESSEQUAL | t6= ALT_NOTEQUAL | t7= NOTEQUAL | t8= 'in' | t9= 'not' 'in' | t10= 'is' | t11= 'is' 'not' )
            int alt58=11;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1006:3: t1= LESS
                    {
                    t1=(Token)match(input,LESS,FOLLOW_LESS_in_comp_op3457); 
                    t = Expression.E_LT;

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1007:3: t2= GREATER
                    {
                    t2=(Token)match(input,GREATER,FOLLOW_GREATER_in_comp_op3469); 
                    t = Expression.E_GT;

                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1008:3: t3= EQUAL
                    {
                    t3=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_comp_op3480); 
                    t = Expression.E_EQUAL;

                    }
                    break;
                case 4 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1009:3: t4= GREATEREQUAL
                    {
                    t4=(Token)match(input,GREATEREQUAL,FOLLOW_GREATEREQUAL_in_comp_op3493); 
                    t = Expression.E_GE;

                    }
                    break;
                case 5 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1010:3: t5= LESSEQUAL
                    {
                    t5=(Token)match(input,LESSEQUAL,FOLLOW_LESSEQUAL_in_comp_op3503); 
                    t = Expression.E_LE;

                    }
                    break;
                case 6 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1011:3: t6= ALT_NOTEQUAL
                    {
                    t6=(Token)match(input,ALT_NOTEQUAL,FOLLOW_ALT_NOTEQUAL_in_comp_op3517); 
                    t = Expression.E_NOT_EQUAL2;

                    }
                    break;
                case 7 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1012:3: t7= NOTEQUAL
                    {
                    t7=(Token)match(input,NOTEQUAL,FOLLOW_NOTEQUAL_in_comp_op3529); 
                    t = Expression.E_NOT_EQUAL;

                    }
                    break;
                case 8 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1013:3: t8= 'in'
                    {
                    t8=(Token)match(input,83,FOLLOW_83_in_comp_op3544); 
                    t = Expression.E_IN;

                    }
                    break;
                case 9 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1014:3: t9= 'not' 'in'
                    {
                    t9=(Token)match(input,95,FOLLOW_95_in_comp_op3554); 
                    match(input,83,FOLLOW_83_in_comp_op3556); 
                    t = Expression.E_NOTIN;

                    }
                    break;
                case 10 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1015:3: t10= 'is'
                    {
                    t10=(Token)match(input,96,FOLLOW_96_in_comp_op3566); 
                    t = Expression.E_IS;

                    }
                    break;
                case 11 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1016:3: t11= 'is' 'not'
                    {
                    t11=(Token)match(input,96,FOLLOW_96_in_comp_op3576); 
                    match(input,95,FOLLOW_95_in_comp_op3578); 
                    t = Expression.E_ISNOT;

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return t;
    }
    // $ANTLR end "comp_op"


    // $ANTLR start "expr"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1019:1: expr returns [ Expression e = null] : e0= xor_expr (tu= VBAR v= xor_expr )* ;
    public final Expression expr() throws RecognitionException {
        Expression e =  null;

        Token tu=null;
        Expression e0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1019:36: (e0= xor_expr (tu= VBAR v= xor_expr )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1020:2: e0= xor_expr (tu= VBAR v= xor_expr )*
            {
            pushFollow(FOLLOW_xor_expr_in_expr3600);
            e0=xor_expr();

            state._fsp--;


            		e = e0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1024:2: (tu= VBAR v= xor_expr )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==VBAR) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1025:3: tu= VBAR v= xor_expr
            	    {
            	    tu=(Token)match(input,VBAR,FOLLOW_VBAR_in_expr3614); 
            	    pushFollow(FOLLOW_xor_expr_in_expr3624);
            	    v=xor_expr();

            	    state._fsp--;


            	    	 			e = new BinaryExpression( e, Expression.E_LOR,v);
            	    	 		

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "expr"


    // $ANTLR start "xor_expr"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1033:1: xor_expr returns [ Expression e = null ] : e0= and_expr (tu= CIRCUMFLEX v= and_expr )* ;
    public final Expression xor_expr() throws RecognitionException {
        Expression e =  null;

        Token tu=null;
        Expression e0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1033:41: (e0= and_expr (tu= CIRCUMFLEX v= and_expr )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1034:2: e0= and_expr (tu= CIRCUMFLEX v= and_expr )*
            {
            pushFollow(FOLLOW_and_expr_in_xor_expr3657);
            e0=and_expr();

            state._fsp--;


            		e = e0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1038:2: (tu= CIRCUMFLEX v= and_expr )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==CIRCUMFLEX) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1039:3: tu= CIRCUMFLEX v= and_expr
            	    {
            	    tu=(Token)match(input,CIRCUMFLEX,FOLLOW_CIRCUMFLEX_in_xor_expr3671); 
            	    pushFollow(FOLLOW_and_expr_in_xor_expr3680);
            	    v=and_expr();

            	    state._fsp--;


            	    				e = new BinaryExpression( e, Expression.E_XOR,v);
            	    			

            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "xor_expr"


    // $ANTLR start "and_expr"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1047:1: and_expr returns [ Expression e = null ] : e0= shift_expr (tu= AMPER v= shift_expr )* ;
    public final Expression and_expr() throws RecognitionException {
        Expression e =  null;

        Token tu=null;
        Expression e0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1047:41: (e0= shift_expr (tu= AMPER v= shift_expr )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1048:2: e0= shift_expr (tu= AMPER v= shift_expr )*
            {
            pushFollow(FOLLOW_shift_expr_in_and_expr3711);
            e0=shift_expr();

            state._fsp--;


            		e = e0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1052:2: (tu= AMPER v= shift_expr )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==AMPER) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1053:3: tu= AMPER v= shift_expr
            	    {
            	    tu=(Token)match(input,AMPER,FOLLOW_AMPER_in_and_expr3726); 
            	    pushFollow(FOLLOW_shift_expr_in_and_expr3736);
            	    v=shift_expr();

            	    state._fsp--;


            	    	 			e = new BinaryExpression( e, Expression.E_LAND, v );
            	    	 		

            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "and_expr"


    // $ANTLR start "shift_expr"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1060:1: shift_expr returns [ Expression e = null ] : e0= arith_expr ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )* ;
    public final Expression shift_expr() throws RecognitionException {
        Expression e =  null;

        Token t1=null;
        Token t2=null;
        Expression e0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1060:43: (e0= arith_expr ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1061:2: e0= arith_expr ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )*
            {
            pushFollow(FOLLOW_arith_expr_in_shift_expr3766);
            e0=arith_expr();

            state._fsp--;


            		e = e0;
            	

            			Token tk = null;
            		
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1068:2: ( (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==RIGHTSHIFT||LA63_0==LEFTSHIFT) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1069:3: (t1= LEFTSHIFT | t2= RIGHTSHIFT ) v= arith_expr
            	    {
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1069:3: (t1= LEFTSHIFT | t2= RIGHTSHIFT )
            	    int alt62=2;
            	    int LA62_0 = input.LA(1);

            	    if ( (LA62_0==LEFTSHIFT) ) {
            	        alt62=1;
            	    }
            	    else if ( (LA62_0==RIGHTSHIFT) ) {
            	        alt62=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 62, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt62) {
            	        case 1 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1070:4: t1= LEFTSHIFT
            	            {
            	            t1=(Token)match(input,LEFTSHIFT,FOLLOW_LEFTSHIFT_in_shift_expr3790); 

            	            					tk = t1;
            	            				

            	            }
            	            break;
            	        case 2 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1075:4: t2= RIGHTSHIFT
            	            {
            	            t2=(Token)match(input,RIGHTSHIFT,FOLLOW_RIGHTSHIFT_in_shift_expr3811); 

            	            					tk = t2;
            	            				

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_arith_expr_in_shift_expr3833);
            	    v=arith_expr();

            	    state._fsp--;

            	     
            	      	 			if( tk == t1 ) 
            	      	 				e = new BinaryExpression( e, Expression.E_LSHIFT, v );
            	      	 			else 
            	      	 				e = new BinaryExpression( e, Expression.E_RSHIFT, v);
            	      	 		

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "shift_expr"


    // $ANTLR start "arith_expr"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1090:1: arith_expr returns [ Expression e = null ] : e0= term ( (t1= PLUS | t2= MINUS ) v= term )* ;
    public final Expression arith_expr() throws RecognitionException {
        Expression e =  null;

        Token t1=null;
        Token t2=null;
        Expression e0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1090:44: (e0= term ( (t1= PLUS | t2= MINUS ) v= term )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1091:2: e0= term ( (t1= PLUS | t2= MINUS ) v= term )*
            {
            pushFollow(FOLLOW_term_in_arith_expr3869);
            e0=term();

            state._fsp--;


            		e = e0;
            		Token tk = null;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1096:2: ( (t1= PLUS | t2= MINUS ) v= term )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( ((LA65_0>=PLUS && LA65_0<=MINUS)) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1097:3: (t1= PLUS | t2= MINUS ) v= term
            	    {
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1097:3: (t1= PLUS | t2= MINUS )
            	    int alt64=2;
            	    int LA64_0 = input.LA(1);

            	    if ( (LA64_0==PLUS) ) {
            	        alt64=1;
            	    }
            	    else if ( (LA64_0==MINUS) ) {
            	        alt64=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 64, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt64) {
            	        case 1 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1098:4: t1= PLUS
            	            {
            	            t1=(Token)match(input,PLUS,FOLLOW_PLUS_in_arith_expr3889); 

            	            					tk = t1;
            	            				

            	            }
            	            break;
            	        case 2 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1103:4: t2= MINUS
            	            {
            	            t2=(Token)match(input,MINUS,FOLLOW_MINUS_in_arith_expr3909); 
            	             
            	            					tk = t2;
            	            				

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_term_in_arith_expr3928);
            	    v=term();

            	    state._fsp--;


            	    	 	 		if( tk == t1 ) 
            	    	 	 			e = new BinaryExpression( e, Expression.E_PLUS, v );
            	    	 	 		else 
            	    	 	 			e = new BinaryExpression( e, Expression.E_MINUS, v );
            	    	 	 	

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "arith_expr"


    // $ANTLR start "term"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1118:1: term returns [ Expression e = null] : e0= factor ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )* ;
    public final Expression term() throws RecognitionException {
        Expression e =  null;

        Token t1=null;
        Token t2=null;
        Token t3=null;
        Token t4=null;
        Expression e0 = null;

        Expression v = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1118:36: (e0= factor ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )* )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1119:2: e0= factor ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )*
            {
            pushFollow(FOLLOW_factor_in_term3958);
            e0=factor();

            state._fsp--;


            			e = e0;
            			int type = Expression.E_EMPTY;
            		
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1124:2: ( (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==STAR||(LA67_0>=SLASH && LA67_0<=DOUBLESLASH)) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1125:3: (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH ) v= factor
            	    {
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1125:3: (t1= STAR | t2= SLASH | t3= PERCENT | t4= DOUBLESLASH )
            	    int alt66=4;
            	    switch ( input.LA(1) ) {
            	    case STAR:
            	        {
            	        alt66=1;
            	        }
            	        break;
            	    case SLASH:
            	        {
            	        alt66=2;
            	        }
            	        break;
            	    case PERCENT:
            	        {
            	        alt66=3;
            	        }
            	        break;
            	    case DOUBLESLASH:
            	        {
            	        alt66=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 66, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt66) {
            	        case 1 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1126:5: t1= STAR
            	            {
            	            t1=(Token)match(input,STAR,FOLLOW_STAR_in_term3979); 

            	            	  			type = Expression.E_MULT;
            	            	  		

            	            }
            	            break;
            	        case 2 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1131:5: t2= SLASH
            	            {
            	            t2=(Token)match(input,SLASH,FOLLOW_SLASH_in_term4003); 

            	            	  			type = Expression.E_DIV;
            	            	  		

            	            }
            	            break;
            	        case 3 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1136:6: t3= PERCENT
            	            {
            	            t3=(Token)match(input,PERCENT,FOLLOW_PERCENT_in_term4028); 

            	            	   			type = Expression.E_MOD;
            	            	   		

            	            }
            	            break;
            	        case 4 :
            	            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1141:7: t4= DOUBLESLASH
            	            {
            	            t4=(Token)match(input,DOUBLESLASH,FOLLOW_DOUBLESLASH_in_term4056); 

            	            	    			type = Expression.E_DOUBLEDIV;
            	            	    		

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_factor_in_term4082);
            	    v=factor();

            	    state._fsp--;


            	      	    		e = new BinaryExpression( e, type, v );
            	      	    	

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "term"


    // $ANTLR start "factor"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1153:1: factor returns [ Expression e = new EmptyExpression() ] : ( (t1= PLUS | t2= MINUS | t3= TILDE ) e0= factor | e0= power );
    public final Expression factor() throws RecognitionException {
        Expression e =  new EmptyExpression();

        Token t1=null;
        Token t2=null;
        Token t3=null;
        Expression e0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1153:57: ( (t1= PLUS | t2= MINUS | t3= TILDE ) e0= factor | e0= power )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=PLUS && LA69_0<=MINUS)||LA69_0==TILDE) ) {
                alt69=1;
            }
            else if ( (LA69_0==LPAREN||LA69_0==NAME||LA69_0==LBRACK||LA69_0==LCURLY||(LA69_0>=BACKQUOTE && LA69_0<=STRING)) ) {
                alt69=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1154:4: (t1= PLUS | t2= MINUS | t3= TILDE ) e0= factor
                    {

                    	  	Token tk = null;
                    	  
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1157:4: (t1= PLUS | t2= MINUS | t3= TILDE )
                    int alt68=3;
                    switch ( input.LA(1) ) {
                    case PLUS:
                        {
                        alt68=1;
                        }
                        break;
                    case MINUS:
                        {
                        alt68=2;
                        }
                        break;
                    case TILDE:
                        {
                        alt68=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 68, 0, input);

                        throw nvae;
                    }

                    switch (alt68) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1158:5: t1= PLUS
                            {
                            t1=(Token)match(input,PLUS,FOLLOW_PLUS_in_factor4128); 

                            	  			tk = t1;
                            	  		

                            }
                            break;
                        case 2 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1163:5: t2= MINUS
                            {
                            t2=(Token)match(input,MINUS,FOLLOW_MINUS_in_factor4151); 

                            	  			tk = t2;
                            	  		

                            }
                            break;
                        case 3 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1168:5: t3= TILDE
                            {
                            t3=(Token)match(input,TILDE,FOLLOW_TILDE_in_factor4173); 

                            	  			tk = t3;
                            	  		

                            }
                            break;

                    }

                    pushFollow(FOLLOW_factor_in_factor4195);
                    e0=factor();

                    state._fsp--;


                    	  		if( tk == t1 ) {	  			
                    	  			e = new UnaryExpression( toDLTK( tk ), Expression.E_PLUS, e0);
                    	  		}
                    	  		else if( tk == t2 ) {
                    	  			e = new UnaryExpression( toDLTK( tk ), Expression.E_MINUS, e0);
                    	  		}
                    	  		else if( tk == t3 ) {
                    	  			e = new UnaryExpression( toDLTK( tk ), Expression.E_TILDE, e0);
                    	  		}	  		
                    	  		else {
                    	  			e = new UnaryExpression( toDLTK( tk ), Expression.E_FACTOR, e0);
                    	  		}
                    	  	

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1189:2: e0= power
                    {
                    pushFollow(FOLLOW_power_in_factor4212);
                    e0=power();

                    state._fsp--;


                    		e = e0;
                    	

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "factor"


    // $ANTLR start "power"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1194:1: power returns [ Expression exp = null; ] : (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? ) ;
    public final Expression power() throws RecognitionException {
        Expression exp =  null;;

        Expression exp0 = null;

        Expression ex = null;

        Expression expr0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1194:41: ( (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1195:3: (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? )
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1195:3: (exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1196:3: exp0= atom (ex= trailer[ exp ] )* ( options {greedy=true; } : DOUBLESTAR expr0= factor )?
            {
            pushFollow(FOLLOW_atom_in_power4239);
            exp0=atom();

            state._fsp--;


            			//Expression ex = exp;
            			exp = exp0;
            		
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1201:3: (ex= trailer[ exp ] )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==LPAREN||LA70_0==DOT||LA70_0==LBRACK) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1202:4: ex= trailer[ exp ]
            	    {
            	    pushFollow(FOLLOW_trailer_in_power4257);
            	    ex=trailer(exp);

            	    state._fsp--;


            	    				exp = ex;
            	    			

            	    }
            	    break;

            	default :
            	    break loop70;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1207:5: ( options {greedy=true; } : DOUBLESTAR expr0= factor )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==DOUBLESTAR) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1208:29: DOUBLESTAR expr0= factor
                    {
                    match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_power4290); 
                    pushFollow(FOLLOW_factor_in_power4308);
                    expr0=factor();

                    state._fsp--;


                    	  			//TODO: add factor expression.
                    //	  			PythonTestListExpression testListExpr = new PythonTestListExpression();
                    //	  			testListExpr.addExpression( exp );
                    //	  			testListExpr.addExpression( expr );
                    //	  			exp = testListExpr;
                    				exp = new BinaryExpression( exp, Expression.E_POWER, expr0 );
                    	  		

                    }
                    break;

            }


            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "power"


    // $ANTLR start "trailer"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1223:1: trailer[ Expression expr ] returns [ Expression result = null ] : (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME ) ;
    public final Expression trailer(Expression expr) throws RecognitionException {
        Expression result =  null;

        Token lp0=null;
        Token rp0=null;
        Token lb1=null;
        Token rb1=null;
        Token ta=null;
        PythonCallArgumentsList args = null;

        Expression k = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1223:64: ( (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1224:2: (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME )
            {

            		//Expression k=null;
            		// Create extended variable reference.
            		//if( !( expr instanceof ExtendedVariableReference ) )
            		//	expr = new ExtendedVariableReference( expr );
            		//ExtendedVariableReference exVariableReference = ( ExtendedVariableReference )expr;
            		result = expr;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1232:2: (lp0= LPAREN args= arglist rp0= RPAREN | lb1= LBRACK k= subscriptlist rb1= RBRACK | DOT ta= NAME )
            int alt72=3;
            switch ( input.LA(1) ) {
            case LPAREN:
                {
                alt72=1;
                }
                break;
            case LBRACK:
                {
                alt72=2;
                }
                break;
            case DOT:
                {
                alt72=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1233:3: lp0= LPAREN args= arglist rp0= RPAREN
                    {
                    lp0=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_trailer4356); 
                     args = new PythonCallArgumentsList(); 
                    pushFollow(FOLLOW_arglist_in_trailer4371);
                    args=arglist();

                    state._fsp--;

                    rp0=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_trailer4379); 

                    				result = new PythonCallExpression ( result,  args ); 
                    			

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1241:3: lb1= LBRACK k= subscriptlist rb1= RBRACK
                    {
                    lb1=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_trailer4397); 
                    pushFollow(FOLLOW_subscriptlist_in_trailer4407);
                    k=subscriptlist();

                    state._fsp--;

                    rb1=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_trailer4416); 

                    				result = new PythonArrayAccessExpression ( result, k );
                    			

                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1248:3: DOT ta= NAME
                    {
                    match(input,DOT,FOLLOW_DOT_in_trailer4431); 
                    ta=(Token)match(input,NAME,FOLLOW_NAME_in_trailer4439); 

                    				result = new PythonVariableAccessExpression (result, new VariableReference( toDLTK( ta ) ) );
                    			

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "trailer"


    // $ANTLR start "atom"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1256:1: atom returns [ Expression exp = null ] : (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ );
    public final Expression atom() throws RecognitionException {
        Expression exp =  null;

        Token lb=null;
        Token rb=null;
        Token a1=null;
        Token n=null;
        Token i=null;
        Token f=null;
        Token c=null;
        Token s=null;
        Expression exp0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1256:39: (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ )
            int alt74=12;
            alt74 = dfa74.predict(input);
            switch (alt74) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1257:4: lb= LPAREN (exp0= tuplelist ) rb= RPAREN
                    {
                    lb=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom4469); 
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1257:16: (exp0= tuplelist )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1257:18: exp0= tuplelist
                    {
                    pushFollow(FOLLOW_tuplelist_in_atom4477);
                    exp0=tuplelist();

                    state._fsp--;

                     exp = exp0;

                    }

                    rb=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom4487); 
                     setStartEndForEmbracedExpr(exp,lb,rb); 

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1258:4: lb= LPAREN rb= RPAREN
                    {
                    lb=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom4498); 
                     exp = new PythonTupleExpression(); 
                    rb=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom4506); 
                     setStartEndForEmbracedExpr(exp,lb,rb); 

                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1259:4: lb= LBRACK (exp0= listmaker ) rb= RBRACK
                    {
                    lb=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_atom4518); 
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1259:16: (exp0= listmaker )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1259:18: exp0= listmaker
                    {
                    pushFollow(FOLLOW_listmaker_in_atom4526);
                    exp0=listmaker();

                    state._fsp--;

                    exp = exp0; 

                    }

                    rb=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_atom4536); 
                     setStartEndForEmbracedExpr(exp,lb,rb); 

                    }
                    break;
                case 4 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1260:4: lb= LBRACK rb= RBRACK
                    {
                    lb=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_atom4546); 
                     exp = new PythonListExpression( ); 
                    rb=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_atom4554); 
                     setStartEndForEmbracedExpr(exp,lb,rb); 

                    }
                    break;
                case 5 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1261:4: a1= LCURLY (exp0= dictmaker ) rb= RCURLY
                    {
                    a1=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_atom4565); 
                    lb =a1;
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1261:24: (exp0= dictmaker )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1261:26: exp0= dictmaker
                    {
                    pushFollow(FOLLOW_dictmaker_in_atom4575);
                    exp0=dictmaker();

                    state._fsp--;

                     exp = exp0; 

                    }

                    rb=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_atom4585); 
                     setStartEndForEmbracedExpr(exp,lb,rb); 

                    }
                    break;
                case 6 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1262:4: lb= LCURLY rb= RCURLY
                    {
                    lb=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_atom4595); 
                     exp = new PythonDictExpression(); 
                    rb=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_atom4603); 
                     setStartEndForEmbracedExpr(exp,lb,rb); 

                    }
                    break;
                case 7 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1263:4: lb= BACKQUOTE exp0= testlist rb= BACKQUOTE
                    {
                    lb=(Token)match(input,BACKQUOTE,FOLLOW_BACKQUOTE_in_atom4616); 
                    pushFollow(FOLLOW_testlist_in_atom4622);
                    exp0=testlist();

                    state._fsp--;

                     exp = exp0; 
                    rb=(Token)match(input,BACKQUOTE,FOLLOW_BACKQUOTE_in_atom4631); 
                     setStartEndForEmbracedExpr(exp,lb,rb); 

                    }
                    break;
                case 8 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1264:4: n= NAME
                    {
                    n=(Token)match(input,NAME,FOLLOW_NAME_in_atom4641); 
                     exp = new VariableReference( toDLTK( n ) ); 

                    }
                    break;
                case 9 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1265:4: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_atom4653); 
                     exp = Literal.createNumericLiteral( toDLTK( i ) );

                    }
                    break;
                case 10 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1266:8: f= FLOAT
                    {
                    f=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_atom4670); 
                     exp=new FloatNumericLiteral( toDLTK( f ) );

                    }
                    break;
                case 11 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1267:8: c= COMPLEX
                    {
                    c=(Token)match(input,COMPLEX,FOLLOW_COMPLEX_in_atom4688); 
                     exp=new ComplexNumericLiteral( toDLTK( c ) ); 

                    }
                    break;
                case 12 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1269:2: (s= STRING )+
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1269:2: (s= STRING )+
                    int cnt73=0;
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==STRING) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1270:3: s= STRING
                    	    {
                    	    s=(Token)match(input,STRING,FOLLOW_STRING_in_atom4705); 
                    	     
                    	    				if( exp != null && exp instanceof StringLiteral  )
                    	    					exp = new StringLiteral( exp.sourceStart(), toDLTK(s),  ((StringLiteral)exp).getValue() + s.getText() ); 
                    	    				else
                    	    					exp = new StringLiteral( toDLTK( s ) ); 
                    	    			

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt73 >= 1 ) break loop73;
                                EarlyExitException eee =
                                    new EarlyExitException(73, input);
                                throw eee;
                        }
                        cnt73++;
                    } while (true);


                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "atom"


    // $ANTLR start "listmaker"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1280:1: listmaker returns [ PythonListExpression exp = new PythonListExpression() ] : firstExpr= test ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) ) ;
    public final PythonListExpression listmaker() throws RecognitionException {
        PythonListExpression exp =  new PythonListExpression();

        Expression firstExpr = null;

        Expression expr0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1280:76: (firstExpr= test ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1281:2: firstExpr= test ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) )
            {
            pushFollow(FOLLOW_test_in_listmaker4735);
            firstExpr=test();

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1282:2: ( ( list_for[ listExpr ] ) | ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? ) )
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==89) ) {
                alt77=1;
            }
            else if ( (LA77_0==COMMA||LA77_0==RBRACK) ) {
                alt77=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }
            switch (alt77) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1283:3: ( list_for[ listExpr ] )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1283:3: ( list_for[ listExpr ] )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1284:4: list_for[ listExpr ]
                    {

                    				PythonListForExpression listExpr = new PythonListForExpression( firstExpr );
                    			
                    pushFollow(FOLLOW_list_for_in_listmaker4752);
                    list_for(listExpr);

                    state._fsp--;


                    				exp.addExpression( listExpr );
                    			

                    }


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1293:3: ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1293:3: ( ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )? )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1294:4: ( options {greedy=true; } : COMMA expr0= test )* ( COMMA )?
                    {

                    				exp.addExpression( firstExpr );
                    			
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1297:4: ( options {greedy=true; } : COMMA expr0= test )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==COMMA) ) {
                            int LA75_1 = input.LA(2);

                            if ( (LA75_1==LPAREN||LA75_1==NAME||(LA75_1>=PLUS && LA75_1<=MINUS)||(LA75_1>=TILDE && LA75_1<=LBRACK)||LA75_1==LCURLY||(LA75_1>=BACKQUOTE && LA75_1<=STRING)||LA75_1==95||LA75_1==97) ) {
                                alt75=1;
                            }


                        }


                        switch (alt75) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1299:5: COMMA expr0= test
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_listmaker4800); 
                    	    pushFollow(FOLLOW_test_in_listmaker4810);
                    	    expr0=test();

                    	    state._fsp--;


                    	    					exp.addExpression( expr0 );
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop75;
                        }
                    } while (true);

                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1305:4: ( COMMA )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==COMMA) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1305:5: COMMA
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_listmaker4829); 

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
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return exp;
    }
    // $ANTLR end "listmaker"


    // $ANTLR start "list_for"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1311:1: list_for[PythonListForExpression list ] : (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+ ;
    public final void list_for(PythonListForExpression list) throws RecognitionException {
        Token forToken=null;
        PythonTestListExpression expr1 = null;

        Expression expr2 = null;

        Expression expr0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1311:41: ( (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+ )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1312:2: (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1312:2: (forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )* )+
            int cnt79=0;
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==89) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1313:3: forToken= 'for' expr1= exprlist 'in' expr2= testlist (expr0= list_if )*
            	    {
            	    forToken=(Token)match(input,89,FOLLOW_89_in_list_for4860); 
            	    pushFollow(FOLLOW_exprlist_in_list_for4868);
            	    expr1=exprlist();

            	    state._fsp--;

            	    match(input,83,FOLLOW_83_in_list_for4872); 
            	    pushFollow(FOLLOW_testlist_in_list_for4881);
            	    expr2=testlist();

            	    state._fsp--;


            	    			PythonForListExpression forListExpression = new PythonForListExpression( expr1, expr2 );
            	    			forListExpression.setStart(toDLTK(forToken).getColumn());
            	    			list.addExpression( forListExpression );
            	    		

            	    			PythonListExpression ifList = null;
            	    		
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1325:3: (expr0= list_if )*
            	    loop78:
            	    do {
            	        int alt78=2;
            	        int LA78_0 = input.LA(1);

            	        if ( (LA78_0==85) ) {
            	            alt78=1;
            	        }


            	        switch (alt78) {
            	    	case 1 :
            	    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1326:4: expr0= list_if
            	    	    {
            	    	    pushFollow(FOLLOW_list_if_in_list_for4904);
            	    	    expr0=list_if();

            	    	    state._fsp--;


            	    	    				if( ifList == null )
            	    	    					ifList = new PythonListExpression();
            	    	    				ifList.addExpression( expr0 );
            	    	    				ifList.setStart(expr0.sourceStart());
            	    	    				ifList.setEnd(expr0.sourceEnd());
            	    	    			

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop78;
            	        }
            	    } while (true);


            	    				if( ifList != null )
            	    					forListExpression.setIfListExpression( ifList );				
            	    			

            	    }
            	    break;

            	default :
            	    if ( cnt79 >= 1 ) break loop79;
                        EarlyExitException eee =
                            new EarlyExitException(79, input);
                        throw eee;
                }
                cnt79++;
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "list_for"


    // $ANTLR start "list_if"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1342:1: list_if returns [ Expression expr = null ] : 'if' expr0= test ;
    public final Expression list_if() throws RecognitionException {
        Expression expr =  null;

        Expression expr0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1342:42: ( 'if' expr0= test )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1343:2: 'if' expr0= test
            {
            match(input,85,FOLLOW_85_in_list_if4937); 
            pushFollow(FOLLOW_test_in_list_if4944);
            expr0=test();

            state._fsp--;


            		expr = expr0;
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return expr;
    }
    // $ANTLR end "list_if"


    // $ANTLR start "lambdef"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1348:1: lambdef returns [ Expression e = null ] : lambda= 'lambda' ( varargslist[ params ] )? COLON statement= test ;
    public final Expression lambdef() throws RecognitionException {
        Expression e =  null;

        Token lambda=null;
        Expression statement = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1348:40: (lambda= 'lambda' ( varargslist[ params ] )? COLON statement= test )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1349:2: lambda= 'lambda' ( varargslist[ params ] )? COLON statement= test
            {
            lambda=(Token)match(input,97,FOLLOW_97_in_lambdef4965); 

            		//ArrayList buf = this.fParameters;
            		//this.fParameters = new ArrayList();
            		ArgumentList params = new ArgumentList ();
            		//Expression statement;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1356:2: ( varargslist[ params ] )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==LPAREN||LA80_0==NAME||(LA80_0>=STAR && LA80_0<=DOUBLESTAR)) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1356:4: varargslist[ params ]
                    {
                    pushFollow(FOLLOW_varargslist_in_lambdef4973);
                    varargslist(params);

                    state._fsp--;


                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_lambdef4980); 
            pushFollow(FOLLOW_test_in_lambdef4988);
            statement=test();

            state._fsp--;

            		
            		if( statement == null ) {
            			statement = new EmptyExpression( );
            		}
            		e = new PythonLambdaExpression( toDLTK( lambda ), params, statement );
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "lambdef"


    // $ANTLR start "subscriptlist"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1367:1: subscriptlist returns [ Expression e = null ] : e0= subscript ( options {greedy=true; } : COMMA k= subscript )* ( COMMA )? ;
    public final Expression subscriptlist() throws RecognitionException {
        Expression e =  null;

        PythonSubscriptExpression e0 = null;

        PythonSubscriptExpression k = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1367:47: (e0= subscript ( options {greedy=true; } : COMMA k= subscript )* ( COMMA )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1368:2: e0= subscript ( options {greedy=true; } : COMMA k= subscript )* ( COMMA )?
            {
            pushFollow(FOLLOW_subscript_in_subscriptlist5012);
            e0=subscript();

            state._fsp--;


            	    e = e0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1372:2: ( options {greedy=true; } : COMMA k= subscript )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( (LA81_0==COMMA) ) {
                    int LA81_1 = input.LA(2);

                    if ( (LA81_1==LPAREN||(LA81_1>=NAME && LA81_1<=COLON)||LA81_1==DOT||(LA81_1>=PLUS && LA81_1<=MINUS)||(LA81_1>=TILDE && LA81_1<=LBRACK)||LA81_1==LCURLY||(LA81_1>=BACKQUOTE && LA81_1<=STRING)||LA81_1==95||LA81_1==97) ) {
                        alt81=1;
                    }


                }


                switch (alt81) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1373:26: COMMA k= subscript
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_subscriptlist5031); 
            	    pushFollow(FOLLOW_subscript_in_subscriptlist5040);
            	    k=subscript();

            	    state._fsp--;


            	    				e = new BinaryExpression( e, Expression.E_COMMA, k );
            	    			

            	    }
            	    break;

            	default :
            	    break loop81;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1379:2: ( COMMA )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==COMMA) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1379:3: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_subscriptlist5055); 

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "subscriptlist"


    // $ANTLR start "subscript"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1382:1: subscript returns [ PythonSubscriptExpression expression = null ] : (w= DOT DOT DOT | (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? ) | ( COLON (tu1= test )? (tu= sliceop )? ) );
    public final PythonSubscriptExpression subscript() throws RecognitionException {
        PythonSubscriptExpression expression =  null;

        Token w=null;
        Expression tu = null;

        Expression tu1 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1382:66: (w= DOT DOT DOT | (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? ) | ( COLON (tu1= test )? (tu= sliceop )? ) )
            int alt88=3;
            switch ( input.LA(1) ) {
            case DOT:
                {
                alt88=1;
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
            case 97:
                {
                alt88=2;
                }
                break;
            case COLON:
                {
                alt88=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }

            switch (alt88) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1383:2: w= DOT DOT DOT
                    {
                    w=(Token)match(input,DOT,FOLLOW_DOT_in_subscript5079); 
                    match(input,DOT,FOLLOW_DOT_in_subscript5081); 
                    match(input,DOT,FOLLOW_DOT_in_subscript5083); 

                    			expression = new PythonSubscriptExpression( toDLTK( w ) );
                    		

                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1389:2: (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1389:2: (tu= test ( COLON (tu1= test )? (tu= sliceop )? )? )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1390:3: tu= test ( COLON (tu1= test )? (tu= sliceop )? )?
                    {

                    			expression = new PythonSubscriptExpression( );	
                    		
                    pushFollow(FOLLOW_test_in_subscript5108);
                    tu=test();

                    state._fsp--;


                    				expression.setTest( tu );
                    			
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1397:3: ( COLON (tu1= test )? (tu= sliceop )? )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==COLON) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1398:4: COLON (tu1= test )? (tu= sliceop )?
                            {
                            match(input,COLON,FOLLOW_COLON_in_subscript5123); 
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1399:4: (tu1= test )?
                            int alt83=2;
                            int LA83_0 = input.LA(1);

                            if ( (LA83_0==LPAREN||LA83_0==NAME||(LA83_0>=PLUS && LA83_0<=MINUS)||(LA83_0>=TILDE && LA83_0<=LBRACK)||LA83_0==LCURLY||(LA83_0>=BACKQUOTE && LA83_0<=STRING)||LA83_0==95||LA83_0==97) ) {
                                alt83=1;
                            }
                            switch (alt83) {
                                case 1 :
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1400:5: tu1= test
                                    {
                                    pushFollow(FOLLOW_test_in_subscript5139);
                                    tu1=test();

                                    state._fsp--;


                                    						expression.setCondition( tu1 );
                                    					

                                    }
                                    break;

                            }

                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1405:4: (tu= sliceop )?
                            int alt84=2;
                            int LA84_0 = input.LA(1);

                            if ( (LA84_0==COLON) ) {
                                alt84=1;
                            }
                            switch (alt84) {
                                case 1 :
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1406:5: tu= sliceop
                                    {
                                    pushFollow(FOLLOW_sliceop_in_subscript5169);
                                    tu=sliceop();

                                    state._fsp--;


                                    						expression.setSlice( tu );
                                    					

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1414:2: ( COLON (tu1= test )? (tu= sliceop )? )
                    {
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1414:2: ( COLON (tu1= test )? (tu= sliceop )? )
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1415:3: COLON (tu1= test )? (tu= sliceop )?
                    {

                    			expression = new PythonSubscriptExpression( );
                    		
                    match(input,COLON,FOLLOW_COLON_in_subscript5211); 
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1419:8: (tu1= test )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==LPAREN||LA86_0==NAME||(LA86_0>=PLUS && LA86_0<=MINUS)||(LA86_0>=TILDE && LA86_0<=LBRACK)||LA86_0==LCURLY||(LA86_0>=BACKQUOTE && LA86_0<=STRING)||LA86_0==95||LA86_0==97) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1420:9: tu1= test
                            {
                            pushFollow(FOLLOW_test_in_subscript5235);
                            tu1=test();

                            state._fsp--;


                                						expression.setCondition( tu1 );
                                					

                            }
                            break;

                    }

                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1425:8: (tu= sliceop )?
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==COLON) ) {
                        alt87=1;
                    }
                    switch (alt87) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1426:9: tu= sliceop
                            {
                            pushFollow(FOLLOW_sliceop_in_subscript5281);
                            tu=sliceop();

                            state._fsp--;


                                						expression.setSlice( tu );
                                					

                            }
                            break;

                    }


                    }


                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return expression;
    }
    // $ANTLR end "subscript"


    // $ANTLR start "sliceop"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1436:1: sliceop returns [ Expression e = null ] : COLON (e0= test )? ;
    public final Expression sliceop() throws RecognitionException {
        Expression e =  null;

        Expression e0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1436:40: ( COLON (e0= test )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1437:2: COLON (e0= test )?
            {
            match(input,COLON,FOLLOW_COLON_in_sliceop5341); 
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1438:2: (e0= test )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==LPAREN||LA89_0==NAME||(LA89_0>=PLUS && LA89_0<=MINUS)||(LA89_0>=TILDE && LA89_0<=LBRACK)||LA89_0==LCURLY||(LA89_0>=BACKQUOTE && LA89_0<=STRING)||LA89_0==95||LA89_0==97) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1438:4: e0= test
                    {
                    pushFollow(FOLLOW_test_in_sliceop5350);
                    e0=test();

                    state._fsp--;

                     e = e0; 

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "sliceop"


    // $ANTLR start "exprlist"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1442:1: exprlist returns [ PythonTestListExpression p = new PythonTestListExpression( ); ] : e= expr ( options {k=2; greedy=true; } : COMMA e= expr )* ( COMMA )? ;
    public final PythonTestListExpression exprlist() throws RecognitionException {
        PythonTestListExpression p =  new PythonTestListExpression( );;

        Expression e = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1442:84: (e= expr ( options {k=2; greedy=true; } : COMMA e= expr )* ( COMMA )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1443:2: e= expr ( options {k=2; greedy=true; } : COMMA e= expr )* ( COMMA )?
            {
            pushFollow(FOLLOW_expr_in_exprlist5377);
            e=expr();

            state._fsp--;


            		p.addExpression( e );
            		p.setStart(e.sourceStart());
            		p.setEnd(e.sourceEnd());
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1449:2: ( options {k=2; greedy=true; } : COMMA e= expr )*
            loop90:
            do {
                int alt90=2;
                alt90 = dfa90.predict(input);
                switch (alt90) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1450:3: COMMA e= expr
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprlist5399); 
            	    pushFollow(FOLLOW_expr_in_exprlist5408);
            	    e=expr();

            	    state._fsp--;


            	    				p.addExpression( e );
            	    				if( e.sourceEnd() > p.sourceEnd() ) {
            	    					p.setEnd( e.sourceEnd() );
            	    				}
            	    			

            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1459:2: ( COMMA )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==COMMA) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1459:3: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_exprlist5422); 

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return p;
    }
    // $ANTLR end "exprlist"


    // $ANTLR start "testlist"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1463:1: testlist returns [ Expression p = new EmptyExpression() ] : e0= test ( options {k=2; greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? ;
    public final Expression testlist() throws RecognitionException {
        Expression p =  new EmptyExpression();

        Expression e0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1463:58: (e0= test ( options {k=2; greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1464:2: e0= test ( options {k=2; greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )?
            {

            		PythonTestListExpression listExpression = new PythonTestListExpression();
            		int end = -1;
            	
            pushFollow(FOLLOW_test_in_testlist5449);
            e0=test();

            state._fsp--;

            		
            		p = e0;
            		listExpression.addExpression( e0 );
            		if( p != null) {
            			listExpression.setStart(p.sourceStart());
            			end = p.sourceEnd();
            			listExpression.setEnd(end);
            		}
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1478:6: ( options {k=2; greedy=true; } : COMMA e0= test )*
            loop92:
            do {
                int alt92=2;
                alt92 = dfa92.predict(input);
                switch (alt92) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1479:7: COMMA e0= test
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_testlist5478); 
            	    pushFollow(FOLLOW_test_in_testlist5490);
            	    e0=test();

            	    state._fsp--;


            	        				if( e0 != null && e0.sourceEnd() > end ) {
            	        					end = e0.sourceEnd();
            	        				}
            	        				listExpression.addExpression( e0 );
            	        				p = listExpression;
            	        			

            	    }
            	    break;

            	default :
            	    break loop92;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1489:9: ( options {greedy=true; } : COMMA )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==COMMA) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1489:33: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_testlist5525); 

                    }
                    break;

            }


                    	if( end != -1 && p != null ) {
                    		p.setEnd(end);
                    	}
                    

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return p;
    }
    // $ANTLR end "testlist"


    // $ANTLR start "tuplelist"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1496:1: tuplelist returns [ Expression p = null ] : e0= test ( options {greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? ;
    public final Expression tuplelist() throws RecognitionException {
        Expression p =  null;

        Expression e0 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1496:42: (e0= test ( options {greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1497:2: e0= test ( options {greedy=true; } : COMMA e0= test )* ( options {greedy=true; } : COMMA )?
            {
            pushFollow(FOLLOW_test_in_tuplelist5559);
            e0=test();

            state._fsp--;


            		p = e0;
            		if( p == null ) {
            			p = new EmptyExpression();
            		}
            		//p.addExpression( e );
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1505:6: ( options {greedy=true; } : COMMA e0= test )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==COMMA) ) {
                    int LA94_1 = input.LA(2);

                    if ( (LA94_1==LPAREN||LA94_1==NAME||(LA94_1>=PLUS && LA94_1<=MINUS)||(LA94_1>=TILDE && LA94_1<=LBRACK)||LA94_1==LCURLY||(LA94_1>=BACKQUOTE && LA94_1<=STRING)||LA94_1==95||LA94_1==97) ) {
                        alt94=1;
                    }


                }


                switch (alt94) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1506:7: COMMA e0= test
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tuplelist5584); 
            	    pushFollow(FOLLOW_test_in_tuplelist5596);
            	    e0=test();

            	    state._fsp--;


            	        				if( !( p instanceof PythonTupleExpression ) ) {
            	    	    				PythonTupleExpression tuple = new PythonTupleExpression();
            	        					tuple.addExpression( p );
            	        					p = tuple;
            	        				}
            	        				PythonTupleExpression tup = (PythonTupleExpression)p;
            	        				tup.addExpression( e0 );
            	        			

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1518:9: ( options {greedy=true; } : COMMA )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==COMMA) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1518:33: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_tuplelist5632); 

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return p;
    }
    // $ANTLR end "tuplelist"


    // $ANTLR start "with_stmt"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1520:1: with_stmt returns [Statement st = null] : w_token= 'with' exp_what= test ( 'as' exp_as= testlist )? COLON block= suite ;
    public final Statement with_stmt() throws RecognitionException {
        Statement st =  null;

        Token w_token=null;
        Expression exp_what = null;

        Expression exp_as = null;

        Block block = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1520:40: (w_token= 'with' exp_what= test ( 'as' exp_as= testlist )? COLON block= suite )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1521:2: w_token= 'with' exp_what= test ( 'as' exp_as= testlist )? COLON block= suite
            {
            w_token=(Token)match(input,98,FOLLOW_98_in_with_stmt5664); 
            pushFollow(FOLLOW_test_in_with_stmt5672);
            exp_what=test();

            state._fsp--;

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1523:2: ( 'as' exp_as= testlist )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==80) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1524:4: 'as' exp_as= testlist
                    {
                    match(input,80,FOLLOW_80_in_with_stmt5680); 
                    pushFollow(FOLLOW_testlist_in_with_stmt5686);
                    exp_as=testlist();

                    state._fsp--;


                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_with_stmt5692); 
            pushFollow(FOLLOW_suite_in_with_stmt5698);
            block=suite();

            state._fsp--;


            		DLTKToken token = toDLTK(w_token);
            		st = new PythonWithStatement(token, exp_what, exp_as, block, token.getColumn(), block.sourceEnd());
            	

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return st;
    }
    // $ANTLR end "with_stmt"


    // $ANTLR start "dictmaker"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1532:1: dictmaker returns [ PythonDictExpression d = new PythonDictExpression() ] : t1= test COLON t2= test ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )* ( COMMA )? ;
    public final PythonDictExpression dictmaker() throws RecognitionException {
        PythonDictExpression d =  new PythonDictExpression();

        Expression t1 = null;

        Expression t2 = null;

        Expression t3 = null;

        Expression t4 = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1532:75: (t1= test COLON t2= test ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )* ( COMMA )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1533:2: t1= test COLON t2= test ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )* ( COMMA )?
            {
            pushFollow(FOLLOW_test_in_dictmaker5720);
            t1=test();

            state._fsp--;

            match(input,COLON,FOLLOW_COLON_in_dictmaker5723); 
            pushFollow(FOLLOW_test_in_dictmaker5730);
            t2=test();

            state._fsp--;


            			d.putExpression( t1, t2 );
            		
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1539:9: ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )*
            loop97:
            do {
                int alt97=2;
                alt97 = dfa97.predict(input);
                switch (alt97) {
            	case 1 :
            	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1540:37: COMMA t3= test COLON t4= test
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_dictmaker5766); 
            	    pushFollow(FOLLOW_test_in_dictmaker5781);
            	    t3=test();

            	    state._fsp--;

            	    match(input,COLON,FOLLOW_COLON_in_dictmaker5792); 
            	    pushFollow(FOLLOW_test_in_dictmaker5807);
            	    t4=test();

            	    state._fsp--;


            	            			d.putExpression( t3, t4 );
            	            		

            	    }
            	    break;

            	default :
            	    break loop97;
                }
            } while (true);

            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1548:9: ( COMMA )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==COMMA) ) {
                alt98=1;
            }
            switch (alt98) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1548:10: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_dictmaker5842); 

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return d;
    }
    // $ANTLR end "dictmaker"


    // $ANTLR start "classdef"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1552:1: classdef returns [ PythonClassDeclaration classDeclaration = null ] : c= 'class' tu= NAME (r= LPAREN (te= testlist )? m= RPAREN )? co= COLON sa= suite ;
    public final PythonClassDeclaration classdef() throws RecognitionException {
        PythonClassDeclaration classDeclaration =  null;

        Token c=null;
        Token tu=null;
        Token r=null;
        Token m=null;
        Token co=null;
        Expression te = null;

        Block sa = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1552:68: (c= 'class' tu= NAME (r= LPAREN (te= testlist )? m= RPAREN )? co= COLON sa= suite )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1553:2: c= 'class' tu= NAME (r= LPAREN (te= testlist )? m= RPAREN )? co= COLON sa= suite
            {
            c=(Token)match(input,99,FOLLOW_99_in_classdef5873); 
            tu=(Token)match(input,NAME,FOLLOW_NAME_in_classdef5880); 

            			classDeclaration = new PythonClassDeclaration( toDLTK( tu ), toDLTK(c) );
            		
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1558:2: (r= LPAREN (te= testlist )? m= RPAREN )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==LPAREN) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1559:3: r= LPAREN (te= testlist )? m= RPAREN
                    {
                    r=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_classdef5899); 
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1560:4: (te= testlist )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==LPAREN||LA99_0==NAME||(LA99_0>=PLUS && LA99_0<=MINUS)||(LA99_0>=TILDE && LA99_0<=LBRACK)||LA99_0==LCURLY||(LA99_0>=BACKQUOTE && LA99_0<=STRING)||LA99_0==95||LA99_0==97) ) {
                        alt99=1;
                    }
                    switch (alt99) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1560:5: te= testlist
                            {
                            pushFollow(FOLLOW_testlist_in_classdef5909);
                            te=testlist();

                            state._fsp--;


                            }
                            break;

                    }

                    m=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_classdef5920); 

                    				if (null != te)
                    				{
                    					if( te instanceof ExpressionList ) {
                    						classDeclaration.setParents( toDLTK( r ), (ExpressionList)te, toDLTK( m ) );
                    					}
                    					else {
                    						ExpressionList exprList = new ExpressionList();
                    						exprList.setStart(te.sourceStart());
                    						exprList.setEnd(te.sourceEnd());
                    						exprList.addExpression( te );
                    						classDeclaration.setParents( toDLTK( r ), exprList, toDLTK( m ) );
                    					}
                    				}
                    					
                    			

                    }
                    break;

            }

            co=(Token)match(input,COLON,FOLLOW_COLON_in_classdef5936); 
            pushFollow(FOLLOW_suite_in_classdef5943);
            sa=suite();

            state._fsp--;


                  			classDeclaration.setBody( toDLTK(co), sa, sa.sourceEnd() );
            		

            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return classDeclaration;
    }
    // $ANTLR end "classdef"


    // $ANTLR start "arglist"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1586:1: arglist returns [PythonCallArgumentsList arguments = new PythonCallArgumentsList();] : (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test ) ;
    public final PythonCallArgumentsList arglist() throws RecognitionException {
        PythonCallArgumentsList arguments =  new PythonCallArgumentsList();;

        Expression k = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1586:85: ( (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test ) )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1587:2: (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )
            {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1587:2: (k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )? | STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )
            int alt106=3;
            switch ( input.LA(1) ) {
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
            case 97:
                {
                alt106=1;
                }
                break;
            case STAR:
                {
                alt106=2;
                }
                break;
            case DOUBLESTAR:
                {
                alt106=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 106, 0, input);

                throw nvae;
            }

            switch (alt106) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1588:3: k= argument ( options {greedy=true; } : COMMA k= argument )* ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )?
                    {
                    pushFollow(FOLLOW_argument_in_arglist5972);
                    k=argument();

                    state._fsp--;


                    				arguments.addArgument( k );
                    			
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1592:11: ( options {greedy=true; } : COMMA k= argument )*
                    loop101:
                    do {
                        int alt101=2;
                        int LA101_0 = input.LA(1);

                        if ( (LA101_0==COMMA) ) {
                            int LA101_1 = input.LA(2);

                            if ( (LA101_1==LPAREN||LA101_1==NAME||(LA101_1>=PLUS && LA101_1<=MINUS)||(LA101_1>=TILDE && LA101_1<=LBRACK)||LA101_1==LCURLY||(LA101_1>=BACKQUOTE && LA101_1<=STRING)||LA101_1==95||LA101_1==97) ) {
                                alt101=1;
                            }


                        }


                        switch (alt101) {
                    	case 1 :
                    	    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1593:35: COMMA k= argument
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_arglist6012); 
                    	    pushFollow(FOLLOW_argument_in_arglist6030);
                    	    k=argument();

                    	    state._fsp--;


                    	             				arguments.addArgument( k );
                    	             			

                    	    }
                    	    break;

                    	default :
                    	    break loop101;
                        }
                    } while (true);

                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1599:3: ( COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )? )?
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==COMMA) ) {
                        alt104=1;
                    }
                    switch (alt104) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1600:4: COMMA ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )?
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_arglist6068); 
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1601:4: ( STAR k= test ( COMMA DOUBLESTAR k= test )? | DOUBLESTAR k= test )?
                            int alt103=3;
                            int LA103_0 = input.LA(1);

                            if ( (LA103_0==STAR) ) {
                                alt103=1;
                            }
                            else if ( (LA103_0==DOUBLESTAR) ) {
                                alt103=2;
                            }
                            switch (alt103) {
                                case 1 :
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1602:5: STAR k= test ( COMMA DOUBLESTAR k= test )?
                                    {
                                    match(input,STAR,FOLLOW_STAR_in_arglist6080); 
                                    pushFollow(FOLLOW_test_in_arglist6091);
                                    k=test();

                                    state._fsp--;


                                                					arguments.addArgument( k, PythonArgument.STAR );
                                                				
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1607:16: ( COMMA DOUBLESTAR k= test )?
                                    int alt102=2;
                                    int LA102_0 = input.LA(1);

                                    if ( (LA102_0==COMMA) ) {
                                        alt102=1;
                                    }
                                    switch (alt102) {
                                        case 1 :
                                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1608:17: COMMA DOUBLESTAR k= test
                                            {
                                            match(input,COMMA,FOLLOW_COMMA_in_arglist6147); 
                                            match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist6166); 
                                            pushFollow(FOLLOW_test_in_arglist6189);
                                            k=test();

                                            state._fsp--;


                                                        						arguments.addArgument( k, PythonArgument.DOUBLESTAR );
                                                        					

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 2 :
                                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1616:14: DOUBLESTAR k= test
                                    {
                                    match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist6258); 
                                    pushFollow(FOLLOW_test_in_arglist6278);
                                    k=test();

                                    state._fsp--;


                                              					arguments.addArgument( k, PythonArgument.DOUBLESTAR );
                                              				

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1624:3: STAR k= test ( COMMA DOUBLESTAR k= test )?
                    {
                    match(input,STAR,FOLLOW_STAR_in_arglist6317); 
                    pushFollow(FOLLOW_test_in_arglist6326);
                    k=test();

                    state._fsp--;


                    				arguments.addArgument( k, PythonArgument.STAR );
                    			
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1629:3: ( COMMA DOUBLESTAR k= test )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==COMMA) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1630:4: COMMA DOUBLESTAR k= test
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_arglist6342); 
                            match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist6348); 
                            pushFollow(FOLLOW_test_in_arglist6358);
                            k=test();

                            state._fsp--;


                            					arguments.addArgument( k, PythonArgument.DOUBLESTAR );
                            				

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1638:3: DOUBLESTAR k= test
                    {
                    match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist6378); 
                    pushFollow(FOLLOW_test_in_arglist6387);
                    k=test();

                    state._fsp--;


                    				arguments.addArgument( k, PythonArgument.DOUBLESTAR );
                    			

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return arguments;
    }
    // $ANTLR end "arglist"


    // $ANTLR start "argument"
    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1646:1: argument returns [ Expression e = null ] : e0= test ( ASSIGN k= test )? ;
    public final Expression argument() throws RecognitionException {
        Expression e =  null;

        Expression e0 = null;

        Expression k = null;


        try {
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1646:41: (e0= test ( ASSIGN k= test )? )
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1647:2: e0= test ( ASSIGN k= test )?
            {
            pushFollow(FOLLOW_test_in_argument6425);
            e0=test();

            state._fsp--;


            		e = e0;
            	
            // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1651:2: ( ASSIGN k= test )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==ASSIGN) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // /Users/andreyvit/Projects/sadr/com.yoursway.sadr.python.core/src/org/eclipse/dltk/python/internal/core/parsers/python_v3.g:1652:3: ASSIGN k= test
                    {
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_argument6435); 
                    pushFollow(FOLLOW_test_in_argument6444);
                    k=test();

                    state._fsp--;


                    				e = new Assignment( e, k );
                    			

                    }
                    break;

            }


            }

        }

        catch (RecognitionException re) {
        	if( reporter != null ) {
        		reporter.reportError(re);
        	}
        	recover(input,re);
        }
        catch (Throwable extre) {
        	//System.err.println(t);
        	if( reporter != null ) {
        		reporter.reportThrowable(extre);
        	}
        }
        finally {
        }
        return e;
    }
    // $ANTLR end "argument"

    // Delegated rules


    protected DFA58 dfa58 = new DFA58(this);
    protected DFA74 dfa74 = new DFA74(this);
    protected DFA90 dfa90 = new DFA90(this);
    protected DFA92 dfa92 = new DFA92(this);
    protected DFA97 dfa97 = new DFA97(this);
    static final String DFA58_eotS =
        "\15\uffff";
    static final String DFA58_eofS =
        "\15\uffff";
    static final String DFA58_minS =
        "\1\37\11\uffff\1\10\2\uffff";
    static final String DFA58_maxS =
        "\1\140\11\uffff\1\137\2\uffff";
    static final String DFA58_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\13\1\12";
    static final String DFA58_specialS =
        "\15\uffff}>";
    static final String[] DFA58_transitionS = {
            "\1\1\1\2\1\3\1\4\1\5\1\6\1\7\55\uffff\1\10\13\uffff\1\11\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14\1\uffff\1\14\37\uffff\2\14\3\uffff\2\14\1\uffff\1\14"+
            "\1\uffff\5\14\46\uffff\1\13",
            "",
            ""
    };

    static final short[] DFA58_eot = DFA.unpackEncodedString(DFA58_eotS);
    static final short[] DFA58_eof = DFA.unpackEncodedString(DFA58_eofS);
    static final char[] DFA58_min = DFA.unpackEncodedStringToUnsignedChars(DFA58_minS);
    static final char[] DFA58_max = DFA.unpackEncodedStringToUnsignedChars(DFA58_maxS);
    static final short[] DFA58_accept = DFA.unpackEncodedString(DFA58_acceptS);
    static final short[] DFA58_special = DFA.unpackEncodedString(DFA58_specialS);
    static final short[][] DFA58_transition;

    static {
        int numStates = DFA58_transitionS.length;
        DFA58_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA58_transition[i] = DFA.unpackEncodedString(DFA58_transitionS[i]);
        }
    }

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = DFA58_eot;
            this.eof = DFA58_eof;
            this.min = DFA58_min;
            this.max = DFA58_max;
            this.accept = DFA58_accept;
            this.special = DFA58_special;
            this.transition = DFA58_transition;
        }
        public String getDescription() {
            return "1005:1: comp_op returns [ int t = Expression.E_EMPTY ] : (t1= LESS | t2= GREATER | t3= EQUAL | t4= GREATEREQUAL | t5= LESSEQUAL | t6= ALT_NOTEQUAL | t7= NOTEQUAL | t8= 'in' | t9= 'not' 'in' | t10= 'is' | t11= 'is' 'not' );";
        }
    }
    static final String DFA74_eotS =
        "\20\uffff";
    static final String DFA74_eofS =
        "\20\uffff";
    static final String DFA74_minS =
        "\4\10\14\uffff";
    static final String DFA74_maxS =
        "\1\70\3\141\14\uffff";
    static final String DFA74_acceptS =
        "\4\uffff\1\7\1\10\1\11\1\12\1\13\1\14\1\2\1\1\1\3\1\4\1\6\1\5";
    static final String DFA74_specialS =
        "\20\uffff}>";
    static final String[] DFA74_transitionS = {
            "\1\1\1\uffff\1\5\45\uffff\1\2\1\uffff\1\3\1\uffff\1\4\1\6\1"+
            "\7\1\10\1\11",
            "\1\13\1\12\1\13\37\uffff\2\13\3\uffff\2\13\1\uffff\1\13\1\uffff"+
            "\5\13\46\uffff\1\13\1\uffff\1\13",
            "\1\14\1\uffff\1\14\37\uffff\2\14\3\uffff\2\14\1\15\1\14\1\uffff"+
            "\5\14\46\uffff\1\14\1\uffff\1\14",
            "\1\17\1\uffff\1\17\37\uffff\2\17\3\uffff\2\17\1\uffff\1\17"+
            "\1\16\5\17\46\uffff\1\17\1\uffff\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA74_eot = DFA.unpackEncodedString(DFA74_eotS);
    static final short[] DFA74_eof = DFA.unpackEncodedString(DFA74_eofS);
    static final char[] DFA74_min = DFA.unpackEncodedStringToUnsignedChars(DFA74_minS);
    static final char[] DFA74_max = DFA.unpackEncodedStringToUnsignedChars(DFA74_maxS);
    static final short[] DFA74_accept = DFA.unpackEncodedString(DFA74_acceptS);
    static final short[] DFA74_special = DFA.unpackEncodedString(DFA74_specialS);
    static final short[][] DFA74_transition;

    static {
        int numStates = DFA74_transitionS.length;
        DFA74_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA74_transition[i] = DFA.unpackEncodedString(DFA74_transitionS[i]);
        }
    }

    class DFA74 extends DFA {

        public DFA74(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 74;
            this.eot = DFA74_eot;
            this.eof = DFA74_eof;
            this.min = DFA74_min;
            this.max = DFA74_max;
            this.accept = DFA74_accept;
            this.special = DFA74_special;
            this.transition = DFA74_transition;
        }
        public String getDescription() {
            return "1256:1: atom returns [ Expression exp = null ] : (lb= LPAREN (exp0= tuplelist ) rb= RPAREN | lb= LPAREN rb= RPAREN | lb= LBRACK (exp0= listmaker ) rb= RBRACK | lb= LBRACK rb= RBRACK | a1= LCURLY (exp0= dictmaker ) rb= RCURLY | lb= LCURLY rb= RCURLY | lb= BACKQUOTE exp0= testlist rb= BACKQUOTE | n= NAME | i= INT | f= FLOAT | c= COMPLEX | (s= STRING )+ );";
        }
    }
    static final String DFA90_eotS =
        "\24\uffff";
    static final String DFA90_eofS =
        "\24\uffff";
    static final String DFA90_minS =
        "\2\6\22\uffff";
    static final String DFA90_maxS =
        "\2\123\22\uffff";
    static final String DFA90_acceptS =
        "\2\uffff\1\2\5\uffff\1\1\13\uffff";
    static final String DFA90_specialS =
        "\24\uffff}>";
    static final String[] DFA90_transitionS = {
            "\1\2\5\uffff\1\1\3\uffff\1\2\102\uffff\1\2",
            "\1\2\1\uffff\1\10\1\uffff\1\10\5\uffff\1\2\31\uffff\2\10\3"+
            "\uffff\2\10\1\uffff\1\10\1\uffff\5\10\32\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA90_eot = DFA.unpackEncodedString(DFA90_eotS);
    static final short[] DFA90_eof = DFA.unpackEncodedString(DFA90_eofS);
    static final char[] DFA90_min = DFA.unpackEncodedStringToUnsignedChars(DFA90_minS);
    static final char[] DFA90_max = DFA.unpackEncodedStringToUnsignedChars(DFA90_maxS);
    static final short[] DFA90_accept = DFA.unpackEncodedString(DFA90_acceptS);
    static final short[] DFA90_special = DFA.unpackEncodedString(DFA90_specialS);
    static final short[][] DFA90_transition;

    static {
        int numStates = DFA90_transitionS.length;
        DFA90_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA90_transition[i] = DFA.unpackEncodedString(DFA90_transitionS[i]);
        }
    }

    class DFA90 extends DFA {

        public DFA90(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 90;
            this.eot = DFA90_eot;
            this.eof = DFA90_eof;
            this.min = DFA90_min;
            this.max = DFA90_max;
            this.accept = DFA90_accept;
            this.special = DFA90_special;
            this.transition = DFA90_transition;
        }
        public String getDescription() {
            return "()* loopback of 1449:2: ( options {k=2; greedy=true; } : COMMA e= expr )*";
        }
    }
    static final String DFA92_eotS =
        "\71\uffff";
    static final String DFA92_eofS =
        "\71\uffff";
    static final String DFA92_minS =
        "\2\6\67\uffff";
    static final String DFA92_maxS =
        "\1\131\1\141\67\uffff";
    static final String DFA92_acceptS =
        "\2\uffff\1\2\44\uffff\1\1\4\uffff\1\1\14\uffff";
    static final String DFA92_specialS =
        "\71\uffff}>";
    static final String[] DFA92_transitionS = {
            "\1\2\2\uffff\1\2\1\uffff\1\2\1\1\2\uffff\16\2\24\uffff\1\2\2"+
            "\uffff\1\2\40\uffff\1\2\3\uffff\1\2",
            "\1\2\1\uffff\1\54\1\2\1\54\1\2\3\uffff\16\2\15\uffff\2\54\3"+
            "\uffff\2\54\1\2\1\54\1\uffff\1\47\4\54\34\uffff\1\2\3\uffff"+
            "\1\2\5\uffff\1\54\1\uffff\1\54",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA92_eot = DFA.unpackEncodedString(DFA92_eotS);
    static final short[] DFA92_eof = DFA.unpackEncodedString(DFA92_eofS);
    static final char[] DFA92_min = DFA.unpackEncodedStringToUnsignedChars(DFA92_minS);
    static final char[] DFA92_max = DFA.unpackEncodedStringToUnsignedChars(DFA92_maxS);
    static final short[] DFA92_accept = DFA.unpackEncodedString(DFA92_acceptS);
    static final short[] DFA92_special = DFA.unpackEncodedString(DFA92_specialS);
    static final short[][] DFA92_transition;

    static {
        int numStates = DFA92_transitionS.length;
        DFA92_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA92_transition[i] = DFA.unpackEncodedString(DFA92_transitionS[i]);
        }
    }

    class DFA92 extends DFA {

        public DFA92(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 92;
            this.eot = DFA92_eot;
            this.eof = DFA92_eof;
            this.min = DFA92_min;
            this.max = DFA92_max;
            this.accept = DFA92_accept;
            this.special = DFA92_special;
            this.transition = DFA92_transition;
        }
        public String getDescription() {
            return "()* loopback of 1478:6: ( options {k=2; greedy=true; } : COMMA e0= test )*";
        }
    }
    static final String DFA97_eotS =
        "\22\uffff";
    static final String DFA97_eofS =
        "\22\uffff";
    static final String DFA97_minS =
        "\1\14\1\10\20\uffff";
    static final String DFA97_maxS =
        "\1\63\1\141\20\uffff";
    static final String DFA97_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\15\uffff";
    static final String DFA97_specialS =
        "\22\uffff}>";
    static final String[] DFA97_transitionS = {
            "\1\1\46\uffff\1\2",
            "\1\4\1\uffff\1\4\37\uffff\2\4\3\uffff\2\4\1\uffff\1\4\1\2\5"+
            "\4\46\uffff\1\4\1\uffff\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA97_eot = DFA.unpackEncodedString(DFA97_eotS);
    static final short[] DFA97_eof = DFA.unpackEncodedString(DFA97_eofS);
    static final char[] DFA97_min = DFA.unpackEncodedStringToUnsignedChars(DFA97_minS);
    static final char[] DFA97_max = DFA.unpackEncodedStringToUnsignedChars(DFA97_maxS);
    static final short[] DFA97_accept = DFA.unpackEncodedString(DFA97_acceptS);
    static final short[] DFA97_special = DFA.unpackEncodedString(DFA97_specialS);
    static final short[][] DFA97_transition;

    static {
        int numStates = DFA97_transitionS.length;
        DFA97_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA97_transition[i] = DFA.unpackEncodedString(DFA97_transitionS[i]);
        }
    }

    class DFA97 extends DFA {

        public DFA97(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 97;
            this.eot = DFA97_eot;
            this.eof = DFA97_eof;
            this.min = DFA97_min;
            this.max = DFA97_max;
            this.accept = DFA97_accept;
            this.special = DFA97_special;
            this.transition = DFA97_transition;
        }
        public String getDescription() {
            return "()* loopback of 1539:9: ( options {k=2; greedy=true; } : COMMA t3= test COLON t4= test )*";
        }
    }
 

    public static final BitSet FOLLOW_NEWLINE_in_file_input101 = new BitSet(new long[]{0x01F58C00000005C0L,0x0000000E8736FFE0L});
    public static final BitSet FOLLOW_stmt_in_file_input126 = new BitSet(new long[]{0x01F58C00000005C0L,0x0000000E8736FFE0L});
    public static final BitSet FOLLOW_EOF_in_file_input157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECORATOR_S_in_decorator185 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_dot_name_in_decorator192 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_LPAREN_in_decorator204 = new BitSet(new long[]{0x01F58C0000006700L,0x0000000280000000L});
    public static final BitSet FOLLOW_arglist_in_decorator214 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_decorator229 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NEWLINE_in_decorator245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decorator_in_decoraror_list267 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_decoraror_list_in_funcdef302 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_funcdef314 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_funcdef322 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_parameters_in_funcdef328 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_funcdef346 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_funcdef360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_parameters382 = new BitSet(new long[]{0x0000000000006700L});
    public static final BitSet FOLLOW_varargslist_in_parameters387 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_parameters394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_defparameter_in_varargslist405 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist422 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_defparameter_in_varargslist427 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist443 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_STAR_in_varargslist460 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_varargslist466 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist501 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist503 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_varargslist509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist551 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_varargslist557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_varargslist595 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_varargslist601 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist610 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist615 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_varargslist621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist635 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_varargslist641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fpdef_in_defparameter660 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_ASSIGN_in_defparameter665 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_defparameter671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_fpdef703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_fpdef714 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_fplist_in_fpdef716 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_fpdef719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fpdef_in_fplist734 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_fplist752 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_fpdef_in_fplist754 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_fplist763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_small_stmt_in_simple_stmt781 = new BitSet(new long[]{0x0000000000010040L});
    public static final BitSet FOLLOW_SEMI_in_simple_stmt800 = new BitSet(new long[]{0x01F58C0000000500L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_small_stmt_in_simple_stmt802 = new BitSet(new long[]{0x0000000000010040L});
    public static final BitSet FOLLOW_SEMI_in_simple_stmt812 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NEWLINE_in_simple_stmt817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_stmt_in_stmt838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compound_stmt_in_stmt858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_stmt_in_small_stmt892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_print_stmt_in_small_stmt903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_del_stmt_in_small_stmt913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pass_stmt_in_small_stmt923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_flow_stmt_in_small_stmt933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_import_stmt_in_small_stmt943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_global_stmt_in_small_stmt953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exec_stmt_in_small_stmt963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assert_stmt_in_small_stmt973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1001 = new BitSet(new long[]{0x000000001FFE8002L});
    public static final BitSet FOLLOW_augassign_in_expr_stmt1015 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1046 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1055 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_PLUSEQUAL_in_augassign1088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUSEQUAL_in_augassign1097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAREQUAL_in_augassign1107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SLASHEQUAL_in_augassign1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERCENTEQUAL_in_augassign1126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AMPEREQUAL_in_augassign1135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VBAREQUAL_in_augassign1144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CIRCUMFLEXEQUAL_in_augassign1153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFTSHIFTEQUAL_in_augassign1162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RIGHTSHIFTEQUAL_in_augassign1171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAREQUAL_in_augassign1180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESLASHEQUAL_in_augassign1189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_print_stmt1223 = new BitSet(new long[]{0x01F58C0020000502L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_print_stmt1234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RIGHTSHIFT_in_print_stmt1247 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_print_stmt1263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_del_stmt1294 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_exprlist_in_del_stmt1302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_pass_stmt1325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_break_stmt_in_flow_stmt1351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_continue_stmt_in_flow_stmt1362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_return_stmt_in_flow_stmt1372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_raise_stmt_in_flow_stmt1382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_yield_stmt_in_flow_stmt1392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_break_stmt1413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_continue_stmt1437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_return_stmt1462 = new BitSet(new long[]{0x01F58C0000000502L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_return_stmt1473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_yield_stmt1501 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_yield_stmt1508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_raise_stmt1530 = new BitSet(new long[]{0x01F58C0000000502L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_raise_stmt1544 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_raise_stmt1555 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_raise_stmt1564 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_raise_stmt1577 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_raise_stmt1587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_import_stmt1704 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_module_imp_in_import_stmt1726 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_import_stmt1755 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_module_imp_in_import_stmt1769 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_79_in_import_stmt1805 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_dot_name_in_import_stmt1813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_import_stmt1820 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_module_imp_in_import_stmt1860 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_import_stmt1883 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_module_imp_in_import_stmt1898 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_STAR_in_import_stmt1955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_dotted_name2005 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DOT_in_dotted_name2015 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_dotted_name2022 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_dotted_name_in_dot_name2057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_dot_name2077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dot_name_in_module_imp2107 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_module_imp2119 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_module_imp2126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_global_stmt2157 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_global_stmt2167 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_global_stmt2174 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_global_stmt2180 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_82_in_exec_stmt2208 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_expr_in_exec_stmt2214 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_exec_stmt2221 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_exec_stmt2230 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_exec_stmt2238 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_exec_stmt2244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_assert_stmt2271 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_assert_stmt2278 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_assert_stmt2284 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_assert_stmt2290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_stmt_in_compound_stmt2318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_stmt_in_compound_stmt2329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_stmt_in_compound_stmt2339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_try_stmt_in_compound_stmt2349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_funcdef_in_compound_stmt2359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classdef_in_compound_stmt2369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_with_stmt_in_compound_stmt2379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_if_stmt2402 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_if_stmt2411 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_if_stmt2415 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_if_stmt2424 = new BitSet(new long[]{0x0000000000000002L,0x0000000000C00000L});
    public static final BitSet FOLLOW_86_in_if_stmt2445 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_if_stmt2454 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_if_stmt2460 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_if_stmt2468 = new BitSet(new long[]{0x0000000000000002L,0x0000000000C00000L});
    public static final BitSet FOLLOW_87_in_if_stmt2489 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_if_stmt2495 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_if_stmt2505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_while_stmt2541 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_while_stmt2549 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_while_stmt2553 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_while_stmt2563 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_while_stmt2577 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_while_stmt2579 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_while_stmt2589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_for_stmt2620 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_exprlist_in_for_stmt2629 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_for_stmt2634 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_for_stmt2642 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_for_stmt2646 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_for_stmt2655 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_for_stmt2670 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_for_stmt2676 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_for_stmt2686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_try_stmt2718 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_try_stmt2722 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_try_stmt2737 = new BitSet(new long[]{0x0000000000000000L,0x0000000018000000L});
    public static final BitSet FOLLOW_91_in_try_stmt2775 = new BitSet(new long[]{0x01F58C0000000D00L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_try_stmt2790 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_COMMA_in_try_stmt2797 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_try_stmt2803 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_try_stmt2816 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_try_stmt2825 = new BitSet(new long[]{0x0000000000000002L,0x0000000008800000L});
    public static final BitSet FOLLOW_87_in_try_stmt2850 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_try_stmt2856 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_try_stmt2866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_try_stmt2977 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_try_stmt3000 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_try_stmt3016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_stmt_in_suite3074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_suite3095 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INDENT_in_suite3105 = new BitSet(new long[]{0x01F58C00000005C0L,0x0000000E8736FFE0L});
    public static final BitSet FOLLOW_stmt_in_suite3129 = new BitSet(new long[]{0x01F58C00000005E0L,0x0000000E8736FFE0L});
    public static final BitSet FOLLOW_DEDENT_in_suite3155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_and_test_in_or_test3205 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_or_test3219 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_and_test_in_or_test3228 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_or_test_in_test3257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lambdef_in_test3272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_not_test_in_and_test3295 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_and_test3309 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_not_test_in_and_test3318 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_95_in_not_test3351 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_not_test_in_not_test3360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comparison_in_not_test3379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_comparison3402 = new BitSet(new long[]{0x0000003F80000002L,0x0000000180080000L});
    public static final BitSet FOLLOW_comp_op_in_comparison3416 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_expr_in_comparison3425 = new BitSet(new long[]{0x0000003F80000002L,0x0000000180080000L});
    public static final BitSet FOLLOW_LESS_in_comp_op3457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_in_comp_op3469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQUAL_in_comp_op3480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATEREQUAL_in_comp_op3493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESSEQUAL_in_comp_op3503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ALT_NOTEQUAL_in_comp_op3517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOTEQUAL_in_comp_op3529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_comp_op3544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_comp_op3554 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_comp_op3556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_comp_op3566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_comp_op3576 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_comp_op3578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_xor_expr_in_expr3600 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_VBAR_in_expr3614 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_xor_expr_in_expr3624 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_and_expr_in_xor_expr3657 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_CIRCUMFLEX_in_xor_expr3671 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_and_expr_in_xor_expr3680 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_shift_expr_in_and_expr3711 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_AMPER_in_and_expr3726 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_shift_expr_in_and_expr3736 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_arith_expr_in_shift_expr3766 = new BitSet(new long[]{0x0000020020000002L});
    public static final BitSet FOLLOW_LEFTSHIFT_in_shift_expr3790 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_RIGHTSHIFT_in_shift_expr3811 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_arith_expr_in_shift_expr3833 = new BitSet(new long[]{0x0000020020000002L});
    public static final BitSet FOLLOW_term_in_arith_expr3869 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_PLUS_in_arith_expr3889 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_MINUS_in_arith_expr3909 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_term_in_arith_expr3928 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_factor_in_term3958 = new BitSet(new long[]{0x0000700000002002L});
    public static final BitSet FOLLOW_STAR_in_term3979 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_SLASH_in_term4003 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_PERCENT_in_term4028 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_DOUBLESLASH_in_term4056 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_factor_in_term4082 = new BitSet(new long[]{0x0000700000002002L});
    public static final BitSet FOLLOW_PLUS_in_factor4128 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_MINUS_in_factor4151 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_TILDE_in_factor4173 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_factor_in_factor4195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_power_in_factor4212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_power4239 = new BitSet(new long[]{0x0001000040004102L});
    public static final BitSet FOLLOW_trailer_in_power4257 = new BitSet(new long[]{0x0001000040004102L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_power4290 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_factor_in_power4308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_trailer4356 = new BitSet(new long[]{0x01F58C0000006500L,0x0000000280000000L});
    public static final BitSet FOLLOW_arglist_in_trailer4371 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_trailer4379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_trailer4397 = new BitSet(new long[]{0x01F58C0040000D00L,0x0000000280000000L});
    public static final BitSet FOLLOW_subscriptlist_in_trailer4407 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_RBRACK_in_trailer4416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_trailer4431 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_trailer4439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_atom4469 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_tuplelist_in_atom4477 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_atom4487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_atom4498 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_atom4506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_atom4518 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_listmaker_in_atom4526 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_RBRACK_in_atom4536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_atom4546 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_RBRACK_in_atom4554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LCURLY_in_atom4565 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_dictmaker_in_atom4575 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_RCURLY_in_atom4585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LCURLY_in_atom4595 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_RCURLY_in_atom4603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BACKQUOTE_in_atom4616 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_atom4622 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_BACKQUOTE_in_atom4631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_atom4641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atom4653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_atom4670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMPLEX_in_atom4688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_atom4705 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_test_in_listmaker4735 = new BitSet(new long[]{0x0000000000001002L,0x0000000002000000L});
    public static final BitSet FOLLOW_list_for_in_listmaker4752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_listmaker4800 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_listmaker4810 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_listmaker4829 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_list_for4860 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_exprlist_in_list_for4868 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_list_for4872 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_list_for4881 = new BitSet(new long[]{0x0000000000000002L,0x0000000002200000L});
    public static final BitSet FOLLOW_list_if_in_list_for4904 = new BitSet(new long[]{0x0000000000000002L,0x0000000002200000L});
    public static final BitSet FOLLOW_85_in_list_if4937 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_list_if4944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_lambdef4965 = new BitSet(new long[]{0x0000000000006D00L});
    public static final BitSet FOLLOW_varargslist_in_lambdef4973 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_lambdef4980 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_lambdef4988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subscript_in_subscriptlist5012 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_subscriptlist5031 = new BitSet(new long[]{0x01F58C0040000D00L,0x0000000280000000L});
    public static final BitSet FOLLOW_subscript_in_subscriptlist5040 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_subscriptlist5055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_subscript5079 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DOT_in_subscript5081 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DOT_in_subscript5083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_subscript5108 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COLON_in_subscript5123 = new BitSet(new long[]{0x01F58C0000000D02L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_subscript5139 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_sliceop_in_subscript5169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_subscript5211 = new BitSet(new long[]{0x01F58C0000000D02L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_subscript5235 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_sliceop_in_subscript5281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_sliceop5341 = new BitSet(new long[]{0x01F58C0000000502L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_sliceop5350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_exprlist5377 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_exprlist5399 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000080000000L});
    public static final BitSet FOLLOW_expr_in_exprlist5408 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_exprlist5422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_testlist5449 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_testlist5478 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_testlist5490 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_testlist5525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_tuplelist5559 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_tuplelist5584 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_tuplelist5596 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_tuplelist5632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_with_stmt5664 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_with_stmt5672 = new BitSet(new long[]{0x0000000000000800L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_with_stmt5680 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_with_stmt5686 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_with_stmt5692 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_with_stmt5698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_dictmaker5720 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_dictmaker5723 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_dictmaker5730 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_dictmaker5766 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_dictmaker5781 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_dictmaker5792 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_dictmaker5807 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_dictmaker5842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_classdef5873 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_NAME_in_classdef5880 = new BitSet(new long[]{0x0000000000000900L});
    public static final BitSet FOLLOW_LPAREN_in_classdef5899 = new BitSet(new long[]{0x01F58C0000000700L,0x0000000280000000L});
    public static final BitSet FOLLOW_testlist_in_classdef5909 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_classdef5920 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_classdef5936 = new BitSet(new long[]{0x01F58C0000000540L,0x000000028016FFC0L});
    public static final BitSet FOLLOW_suite_in_classdef5943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_arglist5972 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_arglist6012 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_argument_in_arglist6030 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_arglist6068 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_STAR_in_arglist6080 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_arglist6091 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_arglist6147 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6166 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_arglist6189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6258 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_arglist6278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_arglist6317 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_arglist6326 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_arglist6342 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6348 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_arglist6358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist6378 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_arglist6387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_argument6425 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_ASSIGN_in_argument6435 = new BitSet(new long[]{0x01F58C0000000500L,0x0000000280000000L});
    public static final BitSet FOLLOW_test_in_argument6444 = new BitSet(new long[]{0x0000000000000002L});

}