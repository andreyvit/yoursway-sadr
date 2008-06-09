package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
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
                acceptor.addResult(LongType.wrap(BigIntegerLiteralC.this), context);
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                String basic = "Evaluating long literal";
                return basic + "\nfor expression " + BigIntegerLiteralC.this.toString();
            }
        };
    }
}
