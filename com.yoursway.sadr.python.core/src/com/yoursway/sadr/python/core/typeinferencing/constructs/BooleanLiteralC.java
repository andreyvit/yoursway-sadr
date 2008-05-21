package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.BoolType;
import com.yoursway.sadr.succeeder.IGoal;

public class BooleanLiteralC extends PythonConstructImpl<VariableReference> {
    
    public BooleanLiteralC(Scope sc, VariableReference node) {
        super(sc, node);
    }
    
    public boolean getValue() {
        String repr = node.getName();
        if (!BooleanValue.TRUE.equals(repr) && !BooleanValue.FALSE.equals(repr))
            throw new NullPointerException();
        return BooleanValue.TRUE.equals(repr);
    }
    
    @Override
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                acceptor.addResult(BoolType.newBooleanObject(BooleanLiteralC.this), context);
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                String basic = super.describe();
                return basic + "\nfor expression " + BooleanLiteralC.this.toString();
            }
        };
    }
}
