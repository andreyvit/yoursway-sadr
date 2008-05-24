package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.sideeffects.ValueF;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.IntType;
import com.yoursway.sadr.succeeder.IGoal;

public class IntegerLiteralC extends PythonConstructImpl<NumericLiteral> {
    
    IntegerLiteralC(Scope sc, NumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                acceptor.addResult(IntType.wrap(IntegerLiteralC.this), context);
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                String basic = "Evaluating integer literal";
                return basic + "\nfor expression " + IntegerLiteralC.this.toString();
            }
        };
    }
    
    @Override
    public Frog toFrog() {
        return new ValueF(IntType.wrap(this));
    }
    
}
