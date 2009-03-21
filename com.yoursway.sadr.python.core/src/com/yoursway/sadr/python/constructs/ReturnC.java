package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.model.IndexAffector;
import com.yoursway.sadr.python.model.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.model.IndexRequest;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ReturnC extends PythonConstructImpl<ReturnStatement> implements IndexAffector {
    
    private final PythonConstruct expression;
    
    ReturnC(PythonStaticContext sc, ReturnStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        expression = wrap(node.getExpression(), sc);
    }
    
    public PythonConstruct getExpression() {
        return expression;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
    public void actOnIndex(IndexRequest r) {
        MethodDeclarationC methodC = staticContext().getParentMethodDeclarationC();
        if (methodC != null)
            r.addReturnedValue(methodC, expression);
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return null;
    }
    
}
