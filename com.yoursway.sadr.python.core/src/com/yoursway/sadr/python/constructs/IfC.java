package com.yoursway.sadr.python.constructs;

import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.statements.IfStatement;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.model.values.BooleanValue;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class IfC extends PythonConstructImpl<IfStatement> {
    
    private final List<PythonConstruct> thenBlock;
    private final List<PythonConstruct> elseBlock;
    private final PythonConstruct condition;
    
    IfC(PythonStaticContext sc, IfStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        condition = wrap(node.getCondition(), sc);
        thenBlock = wrap(node.getSaneThen(), sc);
        elseBlock = wrap(node.getSaneElse(), sc);
    }
    
    public List<PythonConstruct> thenBlock() {
        return thenBlock;
    }
    
    public List<PythonConstruct> elseBlock() {
        return elseBlock;
    }
    
    public PythonConstruct getCondition() {
        return condition;
    }
    
    public List<PythonConstruct> getBranch(PythonValue choice) {
        if (BooleanValue.instance_true.equals(choice)) {
            return thenBlock();
        } else if (BooleanValue.instance_false.equals(choice)) {
            return elseBlock();
        } else { // What the hell was this?!
            return null;
        }
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
}
