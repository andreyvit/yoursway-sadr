package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.ComplexType;
import com.yoursway.sadr.succeeder.IGoal;

public class ComplexLiteralC extends PythonConstructImpl<ComplexNumericLiteral> {
    
    ComplexLiteralC(Scope sc, ComplexNumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    public IGoal evaluate(final Context context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                acceptor.addResult(ComplexType.wrap(ComplexLiteralC.this), context);
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                String basic = "Evaluating complex literal";
                return basic + "\nfor expression " + ComplexLiteralC.this.toString();
            }
        };
    }
}
