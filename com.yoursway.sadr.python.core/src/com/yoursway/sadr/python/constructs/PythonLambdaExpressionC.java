package com.yoursway.sadr.python.constructs;

import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class PythonLambdaExpressionC extends PythonScopeImpl<PythonLambdaExpression> implements
        CallableDeclaration {
    
    public PythonLambdaExpressionC(PythonStaticContext sc, PythonLambdaExpression node,
            PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    public PythonValueSet call(PythonDynamicContext crocodile) {
        return PythonValueSet.EMPTY;
    }
    
    @Override
    public String toString() {
        return "Lambda";
    }
    
    @Override
    public String name() {
        return "<lambda>";
    }
    
    @SuppressWarnings("unchecked")
    public List<PythonArgument> getArguments() {
        return node.getArguments();
    }
    
    public PythonConstruct getArgInit(String name) {
        return null;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new UnsupportedOperationException();
    }
    
}
