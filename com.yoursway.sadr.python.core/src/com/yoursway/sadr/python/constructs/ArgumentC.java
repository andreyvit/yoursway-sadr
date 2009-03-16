package com.yoursway.sadr.python.constructs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ArgumentC extends PythonConstructImpl<PythonArgument> {
    
    private final PythonConstruct init;
    
    ArgumentC(PythonStaticContext sc, PythonArgument node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        Assert.isLegal(node.getName() != null, "node.getName() should be != null");
        if (node.getInitialization() == null)
            init = null;
        else
            init = wrap(node.getInitialization(), sc);
    }
    
    int getStar() {
        return node.getStar();
    }
    
    public String getName() {
        return node.getName();
    }
    
    public PythonConstruct getInit() {
        return init;
    }
    
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new UnsupportedOperationException();
    }
    
}
