package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class BinaryPercentC extends BinaryC {
    
    protected AnalysisProvider typeAnalysisProvider;
    
    BinaryPercentC(Scope sc, BinaryExpression node) {
        super(sc, node);
    }
    
    @Override
    public String getOperationMethodName() {
        // TODO Auto-generated method stub
        return null;
    }
}
