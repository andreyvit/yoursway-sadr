package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.LongType;
import com.yoursway.sadr.succeeder.IGoal;

public class BigIntegerLiteralC extends PythonConstructImpl<BigNumericLiteral> {
    
    BigIntegerLiteralC(Scope sc, BigNumericLiteral literal) {
        super(sc, literal);
    }
    
    @Override
    public IGoal evaluate(final Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                acceptor.addResult(LongType.newLongObject(BigIntegerLiteralC.this), context);
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                String basic = "Evaluating integer literal";
                return basic + "\nfor expression " + BigIntegerLiteralC.this.toString();
            }
        };
    }
}
