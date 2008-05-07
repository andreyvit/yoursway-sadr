package com.yoursway.sadr.python_v2.goals.visitors;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructVisitor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.builtins.IntType;
import com.yoursway.sadr.python_v2.model.builtins.StringType;

public class ExpressionGoalsCreator extends PythonConstructVisitor {
    private final PythonValueSetAcceptor acceptor;
    private final Context context;
    
    public ExpressionGoalsCreator(PythonValueSetAcceptor acceptor, Context context) {
        this.acceptor = acceptor;
        this.context = context;
    }
    
    @Override
    public boolean visit(IntegerLiteralC construct) {
        acceptor.addResult(IntType.newIntObject(construct), context);
        return false;
    }
    
    @Override
    public boolean visit(StringLiteralC construct) {
        acceptor.addResult(StringType.newStringObject(construct), context);
        return false;
    }
    
    @Override
    public boolean visit(VariableReferenceC construct) {
        
        return false;
    }
}
