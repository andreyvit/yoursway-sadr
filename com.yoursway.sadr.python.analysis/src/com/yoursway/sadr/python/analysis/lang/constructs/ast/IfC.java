package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.List;

import org.eclipse.dltk.python.parser.ast.statements.IfStatement;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.objectmodel.values.BooleanValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;

public class IfC extends PythonConstructImpl<IfStatement> {
    
    private final List<PythonConstruct> thenBlock;
    private final List<PythonConstruct> elseBlock;
    private final PythonConstruct condition;
    
    IfC(PythonLexicalContext sc, IfStatement node, PythonConstructImpl<?> parent) {
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
    
}
