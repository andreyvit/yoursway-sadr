package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.HashMap;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public abstract class BinaryC extends PythonConstructImpl<BinaryExpression> {
    
    protected static HashMap<String, String> lbinoplist = new HashMap<String, String>();
    static HashMap<String, String> rbinoplist = new HashMap<String, String>();
    static HashMap<String, String> ibinoplist = new HashMap<String, String>();
    static {
        addOp("+", "__add__");
        addOp("-", "__sub__");
        addOp("*", "__mul__");
        addOp("//", "__floordiv__");
        addOp("%", "__mod__");
        addOp("**", "__pow__");
        addOp("<<", "__lshift__");
        addOp(">>", "__rshift__");
        addOp("&&", "__and__");//numeric and
        addOp(" xor ", "__xor__");
        addOp(" and ", "__and__");
        addOp(" or ", "__or__");
        addOp(" in ", "__contains__");
        addOp("||", "__or__");//numeric or
        addOp("/", "__div__");
        addOp("<", "__lt__");
        addOp("<=", "__le__");
        addOp("==", "__eq__");
        addOp("!=", "__ne__");
        addOp(">", "__gt__");
        addOp(">=", "__ge__");
        addOp(" is ", "__eq__");
        addOp(" is not ", "__ne__");
        addOp(" not in ", "__notcontains__");
    }
    
    static void addOp(String name, String op) {
        lbinoplist.put(name, op);
        rbinoplist.put(name, "__r" + op.substring(2));
        ibinoplist.put(name + "=", "__i" + op.substring(2));
    }
    
    protected static boolean isBinaryOperation(String name) {
        return lbinoplist.containsKey(name);
    }
    
    protected static boolean isBinAssOperation(String name) {
        return ibinoplist.containsKey(name);
    }
    
    BinaryC(PythonLexicalContext sc, BinaryExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        if (node.getChilds().size() != 2) {
            throw new IllegalArgumentException();
        }
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        return PythonValueSet.EMPTY;
    }
    
}
