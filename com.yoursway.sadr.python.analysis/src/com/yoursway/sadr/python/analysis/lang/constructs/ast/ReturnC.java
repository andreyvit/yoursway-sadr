package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.support.VoidConstructException;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ReturnC extends PythonConstructImpl<ReturnStatement> implements IndexAffector {
    
    private final PythonConstruct expression;
    
    ReturnC(PythonLexicalContext sc, ReturnStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        expression = wrap(node.getExpression(), sc);
    }
    
    public PythonConstruct getExpression() {
        return expression;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new VoidConstructException(this);
    }
    
    public void actOnIndex(IndexRequest r) {
        MethodArea area = staticContext().getArea().getReturnableArea();
        if (area != null) {
            Unode unode = expression.toUnode();
            if (unode != null)
                r.addReturnedValue(area, new Bnode(unode, staticContext()));
        }
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return null;
    }
    
}
