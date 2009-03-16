package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class CallArgumentC extends PythonConstructImpl<PythonCallArgument> {
    
    private final PythonConstruct value;
    
    public CallArgumentC(PythonStaticContext staticContext, PythonCallArgument node,
            PythonConstructImpl<?> parent) {
        super(staticContext, node, parent);
        Assert.isLegal(node.getValue() != null, "node.getValue() should be != null");
        value = wrap(node.getValue(), staticContext);
    }
    
    int getStar() {
        return node.getStar();
    }
    
    public PythonConstruct getValue() {
        return value;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new UnsupportedOperationException();
    }
    
}
