package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ReturnC extends PythonConstructImpl<ReturnStatement> {
    
    private final PythonConstruct expression;
    
    ReturnC(PythonStaticContext sc, ReturnStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        expression = wrap(node.getExpression(), sc);
    }
    
    public PythonConstruct getExpression() {
        return expression;
    }
    
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
}
