package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.DotFrog;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> {
    
    private final PythonConstruct receiver;
    private final VariableReferenceC variable;
    
    FieldAccessC(PythonStaticContext sc, PythonVariableAccessExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        this.receiver = wrap(node.getReceiver(), sc);
        this.variable = (VariableReferenceC) wrap(node.variable(), sc);
    }
    
    public boolean match(Frog frog) {
        if (frog instanceof DotFrog) {
            DotFrog dotFrog = (DotFrog) frog;
            return dotFrog.match(this.variable.name());
        }
        return false;
    }
    
    public PythonConstruct receiver() {
        return receiver;
    }
    
    public VariableReferenceC variable() {
        return variable;
    }
    
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        // TODO
        return null;
    }
    
}
