package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class UnaryC extends PythonConstructImpl<UnaryExpression> {
    
    protected static HashMap<Integer, String> opnames = new HashMap<Integer, String>();
    protected static HashMap<String, String> oplist = new HashMap<String, String>();
    static {
        addOp(Expression.E_LNOT, "not ", "__not__");
        addOp(Expression.E_BNOT, "~", "__invert__");
        addOp(Expression.E_MINUS, "-", "__neg__");
        addOp(Expression.E_BNOT, "+", "__pos__");
        addOp(Expression.E_LNOT, "!", "__not__");
    }
    
    static void addOp(int kind, String name, String op) {
        opnames.put(kind, name);
        oplist.put(name, op);
    }
    
    protected static boolean isUnaryOperation(String name) {
        return oplist.containsKey(name);
    }
    
    UnaryC(Scope sc, UnaryExpression node) {
        super(sc, node);
        if (node.getChilds().size() != 1) {
            throw new IllegalArgumentException();
        }
    }
    
    public PythonConstruct getLeft() {
        return getPostChildren().get(0);
    }
}
