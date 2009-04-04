package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.HashMap;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class UnaryC extends PythonConstructImpl<UnaryExpression> {
    
    protected static HashMap<Integer, String> opnames = new HashMap<Integer, String>();
    protected static HashMap<String, String> oplist = new HashMap<String, String>();
    private final PythonConstruct argument;
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
    
    UnaryC(PythonStaticContext sc, UnaryExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        if (node.getChilds().size() != 1) {
            throw new IllegalArgumentException();
        }
        argument = wrap((ASTNode) node.getChilds().get(0), sc);
        argument.hashCode();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        return PythonValueSet.EMPTY;
    }
    
}
