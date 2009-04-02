grammar python_v3;

//parser grammar python_v3;
options
{
//	output = AST;
//	k = 2;
	language = Java;
} 

tokens {
    INDENT;
    DEDENT;
}
 
@lexer::header {
	package org.eclipse.dltk.python.internal.core.parsers;
}

@parser::header {
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
}
@lexer::members {
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
@parser::members {
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
}

@rulecatch {
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
}

// Parser rules
file_input:
    	( 
    		NEWLINE
    		|    	
    		s = stmt
    			{ 
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
    	
    	)*
    	EOF
	//{
	//	decl.setStatements( statements );
	//}
	;

// Decorator grammar. 2.4.x
// decorator: '@' dotted_name [ '(' [arglist] ')' ] NEWLINE
// decorators: decorator+

decorator returns [ Decorator decorator = null ]:
	dec = DECORATOR_S
	dottedName = dot_name
	(
	 	lp0 = LPAREN
			(arguments = arglist)?
			{ if (arguments == null) arguments = new PythonCallArgumentsList(); }
		rp0 = RPAREN
			{
				decorator = new PythonFunctionDecorator( toDLTK( dottedName), toDLTK(dec), toDLTK(rp0), arguments );
			}
	)?

	{
		if( decorator == null ) {
			decorator = new PythonFunctionDecorator( toDLTK( dottedName ), toDLTK(dec) );
		}
	}
	NEWLINE
	;

decoraror_list returns [List decorators = new ArrayList() ]:
	(
		dec = decorator		
		{
			if( dec != null ) {
				decorators.add( dec );
			}
		}	
	)+
	;
//
// Function declaration
funcdef returns [ MethodDeclaration methodDeclaration = null; ]:
	( 
		decorators = decoraror_list 
	)?
	w = 'def' 
	tu = NAME
	{
		methodDeclaration = new MethodDeclaration( toDLTK( w ), toDLTK( tu ) );
		if( decorators != null ) {
			methodDeclaration.setDecorators( decorators );
		}
		//this.fParameters.clear();
		
		ArgumentList params = new ArgumentList();
		
		//Block body;
   	}
	parameters[ params ]
	{
		methodDeclaration.acceptArguments( params ); 
	}
        e = COLON
        body = suite
	{
		methodDeclaration.acceptBody( body );
	}       
;
	
// function parameters
parameters[ ArgumentList params ]:
	LPAREN 
	(varargslist[ params  ])? 
	RPAREN
;

// List of arguments
varargslist[ ArgumentList params ]:
	defparameter[params]
	(
		options { greedy=true;} :COMMA 
		defparameter[ params ]
	)*
        (COMMA
            	( STAR tu = NAME 
            		{ 
				params.add( new PythonArgument( toDLTK( tu ), PythonArgument.STAR ) ); 
			} 
            		(COMMA DOUBLESTAR t1 = NAME 
				{	
					params.add( new PythonArgument( toDLTK( t1 ), PythonArgument.DOUBLESTAR ) );
				}
            		)?
            		| DOUBLESTAR t2 = NAME
				{
            				params.add( new PythonArgument( toDLTK( t2 ), PythonArgument.DOUBLESTAR ) );
            			}
            	)?
        )?
	| STAR m = NAME 
		{ 
			params.add( new PythonArgument( toDLTK( m ), PythonArgument.STAR ) );
		}
	(COMMA 
		DOUBLESTAR m1 = NAME
			{
				params.add( new PythonArgument( toDLTK( m1 ), PythonArgument.DOUBLESTAR ) );
			}
	)?
	| DOUBLESTAR m2 = NAME
		{
			params.add( new PythonArgument( toDLTK( m2 ), PythonArgument.DOUBLESTAR ) );
		}
;

// Parameter with possible initialization expression.
defparameter[ ArgumentList params ]:
	lastParam = fpdef[ params ]
	(ASSIGN
		initExpr=test
		{					
			if( lastParam != null ) {
				lastParam.assign( initExpr );
			}				
		}
	)?
;

// Parameter declaration
fpdef [ ArgumentList params ] returns [ PythonArgument argument = null ]:
    	tu = NAME
		{
			argument = new PythonArgument( toDLTK( tu ) );
			params.add( argument );
		}	  
	|LPAREN fplist[params] RPAREN
;

//
fplist [ArgumentList params ]:   
	fpdef[ params ] 
	(options { greedy=true; }:
		COMMA fpdef[ params ]
	)*
	(COMMA)?
;
////////////////////////////////////////////////////////////////////
//// Statements Part////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////

// Statement rule

simple_stmt [ List stmts ]:
	small_stmt[ stmts ]
	(options { greedy = true; }:
		SEMI small_stmt[ stmts ] 
	) * (SEMI)?
	NEWLINE
	;	stmt returns [ ArrayList statements = new ArrayList( ) ]:
	(
		{
		List simpleStatements = new ArrayList();
		}
		simple_stmt[ simpleStatements ] { statements.addAll( simpleStatements ); }
		|					
		compoundStatement = compound_stmt { statements.add( compoundStatement ); }
	)
	;

// Simple statement rule.

// Small statement declaration
small_stmt [ List stmts ] returns [ Statement rstatement = null ]: 
	(
	  statement1 = expr_stmt { rstatement = statement1; }
	| statement2 = print_stmt{ rstatement = statement2; }
	| statement3 = del_stmt{ rstatement = statement3; }
	| statement4 = pass_stmt{ rstatement = statement4; }
	| statement5 = flow_stmt{ rstatement = statement5; }
	| statement6 = import_stmt{ rstatement = statement6; }
	| statement7 = global_stmt{ rstatement = statement7; }
	| statement8 = exec_stmt{ rstatement = statement8; }
	| statement9 = assert_stmt{ rstatement = statement9; }
	)
	{
		if( rstatement != null )
			stmts.add( rstatement );
	}
	;		
// Expression rule
expr_stmt returns [ Expression exp = null ] :
	exp0 = testlist { exp = exp0; }
	(	
		type = augassign 
		right = testlist
			{
				if( type != 0 ) {
		 			NotStrictAssignment e = new NotStrictAssignment( exp, type, right );
			    		exp = e;
				}
				else {
					exp = new Assignment( exp, right );
				}
		 	}
		|
		(
			a = ASSIGN
			right = testlist
			{
				if( type != 0 ) {
		 			NotStrictAssignment e = new NotStrictAssignment( exp, type, right );
			    		exp = e;
				}
				else {
					exp = new Assignment( exp, right );
				}
			}
		)+
	)?
		{
			if( exp == null )
				exp = new EmptyExpression();
		}
	;	

augassign returns[ int type = 0 ] :
	PLUSEQUAL
		{
			type = Expression.E_PLUS_ASSIGN;
		}
	| MINUSEQUAL 
		{					
			type = Expression.E_MINUS_ASSIGN;		
		}
	| STAREQUAL
		{
			type = Expression.E_MULT_ASSIGN;
		}
	| SLASHEQUAL 
		{
			type = Expression.E_DIV_ASSIGN;
		}
	| PERCENTEQUAL
		{
			type = Expression.E_MOD_ASSIGN;
		}
	| AMPEREQUAL
		{
			type = Expression.E_BAND_ASSIGN;
		}
	| VBAREQUAL
		{
			type = Expression.E_BOR_ASSIGN;
		}
	| CIRCUMFLEXEQUAL
		{
			type = Expression.E_BXOR_ASSIGN;
		}
	| LEFTSHIFTEQUAL
		{
			type = Expression.E_SL_ASSIGN;
		}
	| RIGHTSHIFTEQUAL
		{
			type = Expression.E_SR_ASSIGN;
		}
	| DOUBLESTAREQUAL
		{
			type = Expression.E_DOUBLESTAR_ASSIGN;
		}
	| DOUBLESLASHEQUAL
		{
			type = Expression.E_DOUBLEDIV_ASSIGN;
		}	
	;	
// Print rule statement
print_stmt returns [ Statement statement = null ]:
	
        tu =  'print'
	(
		ex = testlist
        	| RIGHTSHIFT 
        	ex = testlist
	)?
	
	{
		statement = new PrintExpression( toDLTK( tu ), ex );
		if( ex != null ) {
			statement.setEnd(ex.sourceEnd());
		}
	}	
	;		
// Delete statement
del_stmt returns [ Statement statement = null ]: 
	sa = 'del' 
	tu = exprlist
	{
		statement = new PythonDelStatement( toDLTK( sa ), tu );
	}
	;

// Pass statement
pass_stmt returns [ Statement statement = null]: 
	tu = 'pass' 
	{
		statement = new EmptyStatement( toDLTK( tu ) );
	}
	;	

// Flow statement
flow_stmt returns [ Statement statement = null ]:
	  statement0 = break_stmt { statement = statement0; }
	| statement1 = continue_stmt{ statement = statement1; }
	| statement2 = return_stmt{ statement = statement2; }
	| statement3 = raise_stmt{ statement = statement3; }
	| statement4 = yield_stmt{ statement = statement4; }
	;	
// Break statement
break_stmt returns [ Statement statement = null ]: 
	ta = 'break'
		{
			statement = new BreakStatement( toDLTK( ta ), null, toDLTK(ta) );
		}
	;	
// Continue statement
continue_stmt returns [ Statement statement = null ]: 
	ta = 'continue' 
		{
			statement = new ContinueStatement( toDLTK( ta ), null, toDLTK(ta) );
		}
	;	
// Return statement
return_stmt returns [ Statement statement = null ]: 
	ra = 'return' 
		( tu = testlist )?
		{
			DLTKToken ret = toDLTK(ra);
			int end = ret.getColumn()+ret.getText().length();
			if( tu != null ) {
				end = tu.sourceEnd();
			}
			statement = new ReturnStatement( toDLTK( ra ), tu , end );
		}
	;	

// Yield statement
yield_stmt returns [ PythonYieldStatement statement = null ]: 
	tu = 'yield'
	r = testlist
	{
		statement = new PythonYieldStatement( toDLTK( tu ), r );
	}
	;	
// Exception raise statement
raise_stmt returns [ PythonRaiseStatement statement = null ]:
	tu = 'raise'
	{
		statement = new PythonRaiseStatement( toDLTK( tu ) );
		int end = -1;
	}
	(
		e1 = test 
		{
			statement.acceptExpression1( e1 );
			if( e1 != null && e1.sourceEnd() > end ) {
				end = e1.sourceEnd();
			}
		}
		( COMMA
			e2 = test 
			{
				statement.acceptExpression2( e2 );
				if( e2 != null && e2.sourceEnd() > end ) {
					end = e2.sourceEnd();
				}
			}
			( COMMA
				e3 = test
		   		{
		   			statement.acceptExpression3( e3 );
		   			if( e3 != null && e3.sourceEnd() > end ) {
						end = e3.sourceEnd();
					}
		   		}		 
		 	)?
		 )?
	)?
	;	


import_stmt returns [ Statement statement = null ] :         		
    	{
    		Expression impExpr;
    		String impName;
    		String impName2;
    		//Token moduleName;		
    		
    		PythonTestListExpression importNames = new PythonTestListExpression();    		
    	}    
    	
    	(    		
    		(	  			       
    			tu ='import'
    			{	    	
    				statement = new PythonImportStatement( toDLTK( tu ), importNames );
    				//Expression expr0 = null;
    			}
    			expr0 = module_imp
    				{
    					importNames.setStart(expr0.sourceStart());
    					importNames.setEnd(expr0.sourceEnd());
    					importNames.addExpression( expr0 );
    					if( expr0.sourceEnd() > statement.sourceEnd() ) {
    						statement.setEnd( expr0.sourceEnd() );
    					}
    				}
    			
    			( COMMA
    				expr0 = module_imp
    				{
    					importNames.addExpression( expr0 );
    					importNames.setEnd( expr0.sourceEnd());
    					if( expr0.sourceEnd() > statement.sourceEnd() ) {
    						statement.setEnd( expr0.sourceEnd() );
    					}
    				}
    			)*
		)
		|
		r = 'from'
		moduleName = dot_name
		
		'import'
		(
        		(
					{
						//moduleName.setColumn(moduleName.getColumn()-1);
        				statement = new PythonImportFromStatement( toDLTK( r ), new VariableReference( toDLTK( moduleName ) ), importNames );
        				//Expression expr0 = null;
        			}
        			expr0 = module_imp
    					{
    						importNames.setStart(expr0.sourceStart());
    						importNames.setEnd(expr0.sourceEnd());
    						importNames.addExpression( expr0 );
    						if( expr0.sourceEnd() > statement.sourceEnd() ) {
    							statement.setEnd( expr0.sourceEnd() );
    						}
    					}
    				( COMMA
    					expr0 = module_imp
    						{
    							importNames.addExpression( expr0 );
    							importNames.setEnd( expr0.sourceEnd());
    							if( expr0.sourceEnd() > statement.sourceEnd() ) {
    								statement.setEnd( expr0.sourceEnd() );
    							}
    						}
    				)*
    			)
    			|
    			( 
				STAR
				{
					//moduleName.setColumn(moduleName.getColumn()-1);
					statement = new PythonImportFromStatement( toDLTK( r ), new VariableReference( toDLTK( moduleName ) ), new PythonAllImportExpression( ) );
				} 
			)
    		)
    	)
	;		
	
dotted_name returns [ Token token = null ]:
	{		
		String value = "";
	}
	n =NAME
	{
		value += n.getText();
	}
	(
		DOT
		n2 =NAME
		{
			value += "." + n2.getText();
		}
	)+
	{
		token = new CommonToken( n );
		token.setText( value );
		//token.setColumn( n.getColumn() );
	}
	;		

dot_name returns [ Token token = null ]:
	(
		moduleName1 = dotted_name
		{
	    		token = moduleName1;
	    	}
	)
	|
	(
		moduleName2 =NAME
		{
			token = moduleName2;
		}
	)
	{
		if( token != null ) {
			//int column = token.getColumn();
			//token.setColumn( column -1 );
		}
	}
	;		
module_imp returns [ Expression expr = null ]:	
	impName = dot_name	
	{
		expr = new PythonImportExpression( toDLTK( impName ) );
	}
	( 
		'as'// this is should be as.
		as =NAME
			{
				expr = new PythonImportAsExpression( toDLTK( impName ), toDLTK( as ) );
			}
	)?
	;		

// TODO: Not Implemented at all yet.
global_stmt returns [ PythonGlobalStatement statement = null; ]:
	 w = 'global' { statement = new PythonGlobalStatement(toDLTK(w)); }
	 n = NAME { if(n!=null) statement.add(toDLTK(n)); }
	 (COMMA n = NAME { if(n!= null) statement.add(toDLTK(n)); } )*
	;		
	
// TODO: Not Implemented at all yet.
exec_stmt  returns [ Statement statement = null]:
	e = 'exec' ex = expr { statement = new ExecStatement(this.converter.convert(e), ex); } 
	('in' 
		ex = test { ((ExecStatement)statement).acceptIn(ex); } 
	 (COMMA ex = test { ((ExecStatement)statement).acceptIn2(ex); }
	)?)?
	;		
// Assert statement
assert_stmt returns [ PythonAssertStatement statement = null ]:
	tu ='assert'
	exp1 = test 
	( COMMA exp2 = test )?
		{
			statement = new PythonAssertStatement( toDLTK( tu ), exp1, exp2 );
		}
	;		

// Compound statement
compound_stmt returns [ Statement statement = null ]:
	statement0 = if_stmt { statement = statement0; }
	| statement1 = while_stmt{ statement = statement1; }
	| statement2 = for_stmt{ statement = statement2; }
	| statement3 = try_stmt{ statement = statement3; }
	| statement4 = funcdef{ statement = statement4; }
	| statement5 = classdef{ statement = statement5; }
	| statement6 = with_stmt {statement = statement6; }
	;		
// If Statement
if_stmt returns [ IfStatement statement = null ]: 
		is ='if' 
		mn = test
		COLON 
		body = suite
		{ 
			IfStatement t,base;
			List ifs = new ArrayList();
			statement = new IfStatement( toDLTK( is ), mn, body ); 
			statement.setEnd(body.sourceEnd());
			ifs.add(statement);
			base = statement;
			t = statement; 
		}		
		
		(
			z ='elif'
			mn = test 
			COLON 
			body=suite
				{ 
					IfStatement elseIfStatement = new IfStatement( toDLTK( z ), mn, body );
					t.acceptElse( elseIfStatement );
					t = elseIfStatement;
					ifs.add(t);
					for(Iterator i = ifs.iterator(); i.hasNext(); ((IfStatement)i.next()).setEnd(body.sourceEnd()));
					base.setEnd(elseIfStatement.sourceEnd());
				} 
		)*
		(
			'else' 
			COLON 
			body = suite  		
				{ 
					t.setElse( body );
					for(Iterator i = ifs.iterator(); i.hasNext(); ((IfStatement)i.next()).setEnd(body.sourceEnd()));
				} 
		)?
	;		
// While statement
while_stmt returns [ PythonWhileStatement whileStatement = null ]:
		is ='while'
		expression = test
		COLON		
		body = suite
			{ 
				whileStatement = new PythonWhileStatement( toDLTK( is ), expression, body ); 
			}
		(
			'else' COLON 
			body = suite
				{ 
					whileStatement.setElseStatement( body ); 
				}
		)?
	;		
// For statement
for_stmt returns [ PythonForStatement statement = null]:
		is ='for' 
		mains = exprlist 
		'in'
		conds = testlist
		COLON 
		body = suite 
			{
				statement = new PythonForStatement( toDLTK( is ), mains, conds, body );
			}
		(
			'else' 
			COLON 
			body = suite
				{
					statement.acceptElse( body );
				}
		)?
	;	
	
// Try Statement
// not fully implemented yet
try_stmt returns [ PythonTryStatement statement = null  ]:
	is ='try' 
	COLON
		{
			Token lastTok = is;
			
        		List catches = new ArrayList();
		}
    	body = suite
    	(
        (        	
		( 
			ex_='except'
			(
				t1 = test
				(COMMA t2 = test)?
			)?
			COLON
			su = suite
			{
				lastTok = ex_;
				catches.add( new PythonExceptStatement( toDLTK( ex_ ), t1, t2, su ) );
			}
		)+
		(
			( elseT = 'else'
				COLON
				elseBlock = suite 
				{
					lastTok = elseT;
				}			
	         	)	        	
        		
        	)?
        	
        )
        |
        ( 
        	fin ='finally' 
        	{
	       		lastTok = fin;
        	}
        	COLON 
        	su = suite
	       	{
        		catches.add( new TryFinallyStatement( toDLTK( fin ), su ) );
        	}
	)
	)
        {
		statement = new PythonTryStatement( toDLTK( is ), body, catches );
		statement.setElseStatement( elseBlock );
        }
	;
// Suite rule
suite returns [ Block statement = new Block() ]:
	  {
	  	ArrayList l = new ArrayList();		
	  	int startPos = -1;
	  	int endPos = -1;
	  } 	  
	  (
	  	simple_stmt[ l ] 
	  	{
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
	  	| 
	  	NEWLINE
	  	ind = INDENT
	  		{
	  			if( ind != null ) {
	  				
		  			int s = converter.convert( ind ).getColumn();
		  			if( s < startPos && s != -1 ) {
		  				startPos = s;
		  			}
	  			}
	  			//ArrayList k;
	  		}
	  	(
	  		k = stmt 
	  		{
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
	  	)+  
	  	d =DEDENT
	  		{
	  			if( d != null ) {
		  			int e = converter.convert( d ).getColumn() - 1;
		  			if( e > endPos ) {
		  				endPos = e;
		  			}
	  			}
	  		}
	  )
	  
	  {
	   	//endPos -= 1;
	   	//statement = new Block( startPos, endPos, l );
	   	statement.setStart( startPos );
	   	//if( endPos == -1 ) {
	   	//	endPos = length;
	   	//}
	   	statement.setEnd( endPos );
	  }	  

	;		

///////////////////////////////////////////////
/// Expressions
//////////////////////////////////////////

or_test returns [ Expression exp = null ] :
	exp0 = and_test
	{
		exp = exp0;
	}
	(
		r = 'or' 
		v = and_test 
			{
				exp = new BinaryExpression( exp, Expression.E_LOR, v);
			}
	)*
	;
//To be replaced with  'expression' (?) in some rules (as 2.5 grammar)
test returns [ Expression exp = null ]:
		exp0 = or_test {exp = exp0;}
	| 
		 exp0 = lambdef {exp = exp0;}
	;		

and_test returns [ Expression exp = null ]: 
	exp0 = not_test
	{
		exp = exp0;
	}
	( 
		m ='and' 
		v = not_test 
			{
				exp = new BinaryExpression( exp, Expression.E_LAND,v);
			}
	)*
	;		

not_test returns [ Expression exp = null ]:
	(
		n ='not' 
		v = not_test 
			{
				exp = new UnaryExpression( toDLTK( n ), Expression.E_LNOT, v );
			}
		)
	| exp0 = comparison { exp = exp0;}
	;		

comparison returns [ Expression exp = null ]: 
	exp0 = expr
	{
		exp = exp0;
	}
	(
		tu = comp_op 
		v = expr
			{
				exp = new BinaryExpression( exp, tu, v );
			}
		 )*
	;		

comp_op returns [ int t = Expression.E_EMPTY ] :
	 t1 =LESS  		{t = Expression.E_LT;}
	|t2 =GREATER 		{t = Expression.E_GT;}
	|t3 =EQUAL   		{t = Expression.E_EQUAL;}
	|t4 =GREATEREQUAL 	{t = Expression.E_GE;}
	|t5 =LESSEQUAL     	{t = Expression.E_LE;}
	|t6 =ALT_NOTEQUAL   	{t = Expression.E_NOT_EQUAL2;}
	|t7 =NOTEQUAL	    	{t = Expression.E_NOT_EQUAL;}	
	|t8 ='in'		{t = Expression.E_IN;}
	|t9 ='not' 'in'		{t = Expression.E_NOTIN;}///XXX://
	|t10 ='is'		{t = Expression.E_IS;}
	|t11 ='is' 'not'	{t = Expression.E_ISNOT;}//XXX://
	;	

expr returns [ Expression e = null]: 
	e0 = xor_expr
	{
		e = e0;
	}
	(
		tu = VBAR 
	 	v = xor_expr 
	 		{
	 			e = new BinaryExpression( e, Expression.E_LOR,v);
	 		}
	 	)*
	;	

xor_expr returns [ Expression e = null ]: 
	e0 = and_expr
	{
		e = e0;
	}
	( 
		tu =CIRCUMFLEX 
		v = and_expr 
			{
				e = new BinaryExpression( e, Expression.E_XOR,v);
			}
	)*
	;		

and_expr returns [ Expression e = null ]: 
	e0 = shift_expr 
	{
		e = e0;
	}
	(
		tu = AMPER 
	 	v = shift_expr 
	 		{
	 			e = new BinaryExpression( e, Expression.E_LAND, v );
	 		}
	 )*
	;
shift_expr returns [ Expression e = null ]: 
	e0 = arith_expr 
	{
		e = e0;
	}
		{
			Token tk = null;
		}
	(
		(
			t1 = LEFTSHIFT 
				{
					tk = t1;
				}
			|
			t2 = RIGHTSHIFT
				{
					tk = t2;
				}
		) 
  	 	v = arith_expr 
  	 		{ 
  	 			if( tk == t1 ) 
  	 				e = new BinaryExpression( e, Expression.E_LSHIFT, v );
  	 			else 
  	 				e = new BinaryExpression( e, Expression.E_RSHIFT, v);
  	 		}
  	 )*
	;	

arith_expr returns [ Expression  e = null ]: 
	e0 = term 
	{
		e = e0;
		Token tk = null;
	}
	(
		(
			t1 = PLUS
				{
					tk = t1;
				}
			|
			t2 = MINUS
				{ 
					tk = t2;
				}
		) 
		v = term 
			{
	 	 		if( tk == t1 ) 
	 	 			e = new BinaryExpression( e, Expression.E_PLUS, v );
	 	 		else 
	 	 			e = new BinaryExpression( e, Expression.E_MINUS, v );
	 	 	}
	)*
	;	

term returns [ Expression e = null]: 
	e0 = factor 
		{
			e = e0;
			int type = Expression.E_EMPTY;
		}
	(
		(
	  	t1 =STAR 
	  		{
	  			type = Expression.E_MULT;
	  		}
	  	| 
	  	t2 =SLASH  
	  		{
	  			type = Expression.E_DIV;
	  		}
	  	|
	   	t3 =PERCENT  
	   		{
	   			type = Expression.E_MOD;
	   		}
	   	|
	    	t4 =DOUBLESLASH  
	    		{
	    			type = Expression.E_DOUBLEDIV;
	    		}
		) 
  	 v = factor 
  	 	{
  	    		e = new BinaryExpression( e, type, v );
  	    	}
	)*
	;		

factor returns [ Expression  e = new EmptyExpression() ]: 
	  {
	  	Token tk = null;
	  }
	  (
	  	t1 = PLUS
	  		{
	  			tk = t1;
	  		}
	  	|
	  	t2 = MINUS
	  		{
	  			tk = t2;
	  		}
	  	|
	  	t3 =TILDE
	  		{
	  			tk = t3;
	  		}
	  ) 
	  e0 = factor
	  	{
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
	| 
	e0 = power {
		e = e0;
	}
	;		

power returns [ Expression exp = null; ]:
	 (
		exp0 = atom
		{
			//Expression ex = exp;
			exp = exp0;
		}
		( 
			ex = trailer[ exp ] 
			{
				exp = ex;
			} 
		)*
	  	(
	  		options {greedy=true;}:DOUBLESTAR 
	  		
	  		expr0 = factor 
	  		{
	  			//TODO: add factor expression.
//	  			PythonTestListExpression testListExpr = new PythonTestListExpression();
//	  			testListExpr.addExpression( exp );
//	  			testListExpr.addExpression( expr );
//	  			exp = testListExpr;
				exp = new BinaryExpression( exp, Expression.E_POWER, expr0 );
	  		}
	  	)?
	  )
	;		
// expr this is initial expression.
trailer[ Expression expr ] returns [ Expression result = null ]:
	{
		//Expression k=null;
		// Create extended variable reference.
		//if( !( expr instanceof ExtendedVariableReference ) )
		//	expr = new ExtendedVariableReference( expr );
		//ExtendedVariableReference exVariableReference = ( ExtendedVariableReference )expr;
		result = expr;
	}
	(
		lp0 = LPAREN 
			{ args = new PythonCallArgumentsList(); }
			args = arglist
		rp0 = RPAREN
			{
				result = new PythonCallExpression ( result,  args ); 
			}
		| 
		lb1 = LBRACK 
			k = subscriptlist 
		rb1 = RBRACK 
			{
				result = new PythonArrayAccessExpression ( result, k );
			}
		| 
		DOT 
		ta =NAME 
			{
				result = new PythonVariableAccessExpression (result, new VariableReference( toDLTK( ta ) ) );
			}
	)
	;
	
atom returns [ Expression exp = null ]:
	  lb = LPAREN ( exp0 = tuplelist { exp = exp0;} ) rb = RPAREN { setStartEndForEmbracedExpr(exp,lb,rb); }
	| lb = LPAREN { exp = new PythonTupleExpression(); } rb = RPAREN { setStartEndForEmbracedExpr(exp,lb,rb); } // for initializations like a = ()
	| lb = LBRACK ( exp0 = listmaker {exp = exp0; } ) rb = RBRACK { setStartEndForEmbracedExpr(exp,lb,rb); }
	| lb =LBRACK { exp = new PythonListExpression( ); } rb = RBRACK { setStartEndForEmbracedExpr(exp,lb,rb); }  // for initializations like a = []
	| a1=LCURLY {lb =a1;} ( exp0 = dictmaker { exp = exp0; } ) rb = RCURLY { setStartEndForEmbracedExpr(exp,lb,rb); }
	| lb =LCURLY { exp = new PythonDictExpression(); } rb = RCURLY { setStartEndForEmbracedExpr(exp,lb,rb); }  // for initialization like a = {}
	| lb = BACKQUOTE exp0 = testlist { exp = exp0; } rb =  BACKQUOTE { setStartEndForEmbracedExpr(exp,lb,rb); }
	| n =NAME { exp = new VariableReference( toDLTK( n ) ); }	
	| i = INT  { exp = Literal.createNumericLiteral( toDLTK( i ) );} 
    	| f = FLOAT    { exp=new FloatNumericLiteral( toDLTK( f ) );}
    	| c = COMPLEX  { exp=new ComplexNumericLiteral( toDLTK( c ) ); }
	|
	(
		s = STRING
			{ 
				if( exp != null && exp instanceof StringLiteral  )
					exp = new StringLiteral( exp.sourceStart(), toDLTK(s),  ((StringLiteral)exp).getValue() + s.getText() ); 
				else
					exp = new StringLiteral( toDLTK( s ) ); 
			}
	)+  
	;	

listmaker returns [ PythonListExpression exp = new PythonListExpression() ]:
	firstExpr = test
	(
		(
			{
				PythonListForExpression listExpr = new PythonListForExpression( firstExpr );
			}
			list_for[ listExpr ]
			{
				exp.addExpression( listExpr );
			}
		)
		|
		(
			{
				exp.addExpression( firstExpr );
			} 
			(
				options {greedy=true;}: 
				COMMA
				expr0 = test 
				{
					exp.addExpression( expr0 );
				}
			)*
			(COMMA)?
		)
	)
	;	

// Available only in python >=2.3.x
list_for [PythonListForExpression list ]:
	(
		forToken = 'for'
		expr1 = exprlist
		'in' 
		expr2 = testlist
		{
			PythonForListExpression forListExpression = new PythonForListExpression( expr1, expr2 );
			forListExpression.setStart(toDLTK(forToken).getColumn());
			list.addExpression( forListExpression );
		}
		{
			PythonListExpression ifList = null;
		}		
		(
			expr0 = list_if
			{
				if( ifList == null )
					ifList = new PythonListExpression();
				ifList.addExpression( expr0 );
				ifList.setStart(expr0.sourceStart());
				ifList.setEnd(expr0.sourceEnd());
			}
		)*
			{
				if( ifList != null )
					forListExpression.setIfListExpression( ifList );				
			}
	)+
	;	

list_if returns[ Expression expr = null ]:
	'if'
	expr0 = test {
		expr = expr0;
	}
	;		
lambdef returns [ Expression e = null ]:
	lambda = 'lambda'
	{
		//ArrayList buf = this.fParameters;
		//this.fParameters = new ArrayList();
		ArgumentList params = new ArgumentList ();
		//Expression statement;
	}
	( varargslist[ params ] )?
	COLON 
	statement = test
	{		
		if( statement == null ) {
			statement = new EmptyExpression( );
		}
		e = new PythonLambdaExpression( toDLTK( lambda ), params, statement );
	}
	;

subscriptlist returns [ Expression e = null ] :  
	e0 = subscript 
	{
	    e = e0;
	}
	( 
		options {greedy=true;}:COMMA 
		k = subscript 
			{
				e = new BinaryExpression( e, Expression.E_COMMA, k );
			}
	)* 
	(COMMA)?
	;			

subscript returns [ PythonSubscriptExpression expression = null ]: 
	w = DOT DOT DOT
		{
			expression = new PythonSubscriptExpression( toDLTK( w ) );
		}
		
	|
	(
		{
			expression = new PythonSubscriptExpression( );	
		}
		tu = test
			{
				expression.setTest( tu );
			} 
		(
			COLON 
			(
				tu1 = test 
					{
						expression.setCondition( tu1 );
					}
			)? 
			(
				tu = sliceop 
					{
						expression.setSlice( tu );
					} 
			)?
		)?
	)
	| 
	(
		{
			expression = new PythonSubscriptExpression( );
		}
    		COLON 
    			(
    				tu1 = test 
    					{
    						expression.setCondition( tu1 );
    					}
    			)? 
    			(
    				tu = sliceop 
    					{
    						expression.setSlice( tu );
    					}
    			)?
    
	)      
    	;	    


sliceop returns [ Expression e = null ]:
	COLON
	( e0 = test { e = e0; } )?
	;		


exprlist returns [ PythonTestListExpression p = new PythonTestListExpression( ); ] :
	e = expr 
	{
		p.addExpression( e );
		p.setStart(e.sourceStart());
		p.setEnd(e.sourceEnd());
	}
	(options {k=2;greedy=true;}:
		COMMA 
		e = expr 
			{
				p.addExpression( e );
				if( e.sourceEnd() > p.sourceEnd() ) {
					p.setEnd( e.sourceEnd() );
				}
			}
	)*
	(COMMA)?
	;		

// to be replaced by 'target' in some rules(as  in 2.5 grammar)
testlist returns [ Expression p = new EmptyExpression() ]: 
	{
		PythonTestListExpression listExpression = new PythonTestListExpression();
		int end = -1;
	}
	e0 = test
	{		
		p = e0;
		listExpression.addExpression( e0 );
		if( p != null) {
			listExpression.setStart(p.sourceStart());
			end = p.sourceEnd();
			listExpression.setEnd(end);
		}
	}
    	(options {k=2;greedy=true;}:
    		COMMA
    		e0 = test
    			{
    				if( e0 != null && e0.sourceEnd() > end ) {
    					end = e0.sourceEnd();
    				}
    				listExpression.addExpression( e0 );
    				p = listExpression;
    			}
    	)*
        (options {greedy=true;}:COMMA)?
        {
        	if( end != -1 && p != null ) {
        		p.setEnd(end);
        	}
        }
    	;
tuplelist returns [ Expression p = null ]: 
	e0 = test
	{
		p = e0;
		if( p == null ) {
			p = new EmptyExpression();
		}
		//p.addExpression( e );
	}
    	(options {greedy=true;}:
    		COMMA
    		e0 = test 
    			{
    				if( !( p instanceof PythonTupleExpression ) ) {
	    				PythonTupleExpression tuple = new PythonTupleExpression();
    					tuple.addExpression( p );
    					p = tuple;
    				}
    				PythonTupleExpression tup = (PythonTupleExpression)p;
    				tup.addExpression( e0 );
    			}
    	)*
        (options {greedy=true;}:COMMA)?
    	;        	
with_stmt returns [Statement st = null]:
	w_token = 'with' 
	exp_what = test
	(
		 'as' exp_as = testlist
	)? COLON block = suite
	{
		DLTKToken token = toDLTK(w_token);
		st = new PythonWithStatement(token, exp_what, exp_as, block, token.getColumn(), block.sourceEnd());
	}
	;
//TODO: NEED to implement
dictmaker returns [ PythonDictExpression d = new PythonDictExpression() ] :
	t1 = test
	COLON
	t2 = test
		{
			d.putExpression( t1, t2 );
		}
        (
        	options {k=2;greedy=true;}:COMMA
        	t3 = test
        	COLON
        	t4 = test
        		{
        			d.putExpression( t3, t4 );
        		}
        )* 
        (COMMA)?
    	;	    	


classdef returns [ PythonClassDeclaration classDeclaration = null ]:
	c = 'class'
	tu = NAME
		{
			classDeclaration = new PythonClassDeclaration( toDLTK( tu ), toDLTK(c) );
		}				
	( 
		r =LPAREN
			(te = testlist)?
			m = RPAREN
			{
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
	)?
	co = COLON
	sa = suite
		{
      			classDeclaration.setBody( toDLTK(co), sa, sa.sourceEnd() );
		}	
	;		

arglist returns [PythonCallArgumentsList arguments = new PythonCallArgumentsList();]:
	(
		k = argument 
			{
				arguments.addArgument( k );
			} 
         	( 
         		options {greedy=true;}:COMMA 
         		k = argument 
         			{
         				arguments.addArgument( k );
         			}
         	)*
		( 
			COMMA
			( 
				STAR 
				k = test 
            				{
            					arguments.addArgument( k, PythonArgument.STAR );
            				}  
            			(
            				COMMA 
            				DOUBLESTAR 
            				k = test 
            					{
            						arguments.addArgument( k, PythonArgument.DOUBLESTAR );
            					}
            			)?
          			| 
          			DOUBLESTAR 
          			k = test 
          				{
          					arguments.addArgument( k, PythonArgument.DOUBLESTAR );
          				}
			)?
		)?
		|   
		STAR 
		k = test 
			{
				arguments.addArgument( k, PythonArgument.STAR );
			} 
		(
			COMMA 
			DOUBLESTAR 
			k = test 
				{
					arguments.addArgument( k, PythonArgument.DOUBLESTAR );
				}
		)?
		|
		DOUBLESTAR 
		k = test 
			{
				arguments.addArgument( k, PythonArgument.DOUBLESTAR );
			}
	)
    	;	    	

argument returns [ Expression e = null ]: 
	e0 = test
	{
		e = e0;
	}
	(
		ASSIGN 
		k = test
			{
				e = new Assignment( e, k );
			}
	)?
	;

/////////////////////////////////////////////////////////////////////
//Lexer rules
//lexer grammar python_v3;ASSIGN
//@lexer::options
//{
//	k = 3;
//	testLiterals = true;
//	charVocabulary='\u0003'..'\ufffe';
//	language = Java;
//} 

LPAREN	: '(' {implicitLineJoiningLevel++;} ;

RPAREN	: ')' {implicitLineJoiningLevel--;} ;

LBRACK	: '[' {implicitLineJoiningLevel++;} ;

RBRACK	: ']' {implicitLineJoiningLevel--;} ;

COLON 	: ':' ;

COMMA	: ',' ;

SEMI	: ';' ;

PLUS	: '+' ;

MINUS	: '-' ;

STAR	: '*' ;

SLASH	: '/' ;

VBAR	: '|' ;

AMPER	: '&' ;

LESS	: '<' ;

GREATER	: '>' ;

ASSIGN	: '=' ;

PERCENT	: '%' ;

BACKQUOTE	: '`' ;

LCURLY	: '{' {implicitLineJoiningLevel++;} ;

RCURLY	: '}' {implicitLineJoiningLevel--;} ;

CIRCUMFLEX	: '^' ;

TILDE	: '~' ;

EQUAL	: '==' ;

NOTEQUAL	: '!=' ;

ALT_NOTEQUAL: '<>' ;

LESSEQUAL	: '<=' ;

LEFTSHIFT	: '<<' ;

GREATEREQUAL	: '>=' ;

RIGHTSHIFT	: '>>' ;

PLUSEQUAL	: '+=' ;

MINUSEQUAL	: '-=' ;

DOUBLESTAR	: '**' ;

STAREQUAL	: '*=' ;

DOUBLESLASH	: '//' ;

SLASHEQUAL	: '/=' ;

VBAREQUAL	: '|=' ;

PERCENTEQUAL	: '%=' ;

AMPEREQUAL	: '&=' ;

CIRCUMFLEXEQUAL	: '^=' ;

LEFTSHIFTEQUAL	: '<<=' ;

RIGHTSHIFTEQUAL	: '>>=' ;

DOUBLESTAREQUAL	: '**=' ;

DOUBLESLASHEQUAL	: '//=' ;

DOT : '.' ;

FLOAT  	:	POINTFLOAT | EXPONENTFLOAT
//	:	'.' DIGITS (Exponent)?
//    |   DIGITS ('.' (DIGITS (Exponent)?)? | Exponent)
    ;
POINTFLOAT
	:	DIGITS? FRACTION | DIGITS '.'
	;
FRACTION 
	:	'.' DIGITS
	;
EXPONENTFLOAT 
	:	(DIGITS | POINTFLOAT) Exponent
	;
fragment
Exponent
	:	('e' | 'E') ( '+' | '-' )? DIGITS
	;

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

COMPLEX
    :   DIGITS ('j'|'J')
    |   FLOAT ('j'|'J')
    ;

fragment
DIGITS : ( '0' .. '9' )+ ;

NAME:	( 'a' .. 'z' | 'A' .. 'Z' | '_')
        ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
    ;

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

/** the two '"'? cause a warning -- is there a way to avoid that? */
fragment
TRIQUOTE
    : '"'? '"'? (ESC|~('\\'|'"'))+
    ;

/** the two '\''? cause a warning -- is there a way to avoid that? */
fragment
TRIAPOS
    : '\''? '\''? (ESC|~('\\'|'\''))+
    ;
fragment
ESC
	:	'\\' .
	;

/** Consume a newline and any whitespace at start of next line */
CONTINUED_LINE
	:	'\\' ('\r')? '\n' (' '|'\t')* {
		 //fModule.acceptLine(getColumn());
		 /*newline();*/ $channel=HIDDEN; }
	;

WS	:	{startPos>0}?=> (' '|'\t')+ {$channel=HIDDEN;}
	; 
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
DECORATOR_S:
	'@'
;
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
