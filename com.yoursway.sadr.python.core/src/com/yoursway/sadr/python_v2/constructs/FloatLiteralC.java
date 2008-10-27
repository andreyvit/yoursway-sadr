package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.FloatType;
import com.yoursway.sadr.succeeder.IGoal;

public class FloatLiteralC extends PythonConstructImpl<FloatNumericLiteral> {
    FloatLiteralC(Scope sc, FloatNumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                acceptor.addResult(FloatType.wrap(FloatLiteralC.this), context);
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                String basic = "Evaluating float literal";
                return basic + "\nfor expression " + FloatLiteralC.this.toString();
            }
        };
    }
}
