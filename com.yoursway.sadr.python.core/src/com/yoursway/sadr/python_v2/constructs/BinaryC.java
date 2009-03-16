package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

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
        addOp(" is ", "__eq__");//FIXME
        addOp(" is not ", "__ne__");//FIXME
        addOp(" not in ", "__notcontains__");//FIXME
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
    
    BinaryC(Scope sc, BinaryExpression node) {
        super(sc, node);
        if (node.getChilds().size() != 2) {
            throw new IllegalArgumentException();
        }
    }
    
    public PythonConstruct getLeft() {
        if (node.getKind() == Expression.E_IN)
            return getPostChildren().get(1);
        if (node.getKind() == Expression.E_NOTIN)
            return getPostChildren().get(1);
        return getPostChildren().get(0);
    }
    
    public PythonConstruct getRight() {
        if (node.getKind() == Expression.E_IN)
            return getPostChildren().get(0);
        if (node.getKind() == Expression.E_NOTIN)
            return getPostChildren().get(0);
        return getPostChildren().get(1);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        return PythonValueSet.EMPTY;
    }
}
