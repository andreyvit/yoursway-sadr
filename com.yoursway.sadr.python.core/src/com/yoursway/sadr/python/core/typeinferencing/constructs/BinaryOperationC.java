package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.HashMap;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class BinaryOperationC extends BinaryC {
    
    private final String opName;
    
    static HashMap<String, String> binoplist = new HashMap<String, String>();
    static {
        binoplist.put("+", "__add__");
        binoplist.put("-", "__sub__");
        binoplist.put("*", "__mul__");
        binoplist.put("//", "__floordiv__");
        binoplist.put("%", "__mod__");
        binoplist.put("**", "__pow__");
        binoplist.put("<<", "__lshift__");
        binoplist.put(">>", "__rshift__");
        binoplist.put("&", "__and__");
        binoplist.put("^", "__xor__");
        binoplist.put("|", "__or__");
        binoplist.put("/", "__div__");
        
        //      binoplist.put("+", "__radd__");
        //      binoplist.put("-", "__rsub__");
        //      binoplist.put("*", "__rmul__");
        //      binoplist.put("/", "__rdiv__");
        //      binoplist.put("//", "__rfloordiv__");
        //      binoplist.put("%", "__rmod__");
        //      binoplist.put("**", "__rpow__");
        //      binoplist.put("<<", "__rlshift__");
        //      binoplist.put(">>", "__rrshift__");
        //      binoplist.put("&", "__rand__");
        //      binoplist.put("^", "__rxor__");
        //      binoplist.put("|", "__ror__");
        
        binoplist.put("<", "__lt__");
        binoplist.put("<=", "__le__");
        binoplist.put("==", "__eq__");
        binoplist.put("!=", "__ne__");
        binoplist.put(">", "__gt__");
        binoplist.put(">=", "__ge__");
    }
    
    static boolean isBinaryOperation(String name) {
        return binoplist.containsKey(name);
    }
    
    BinaryOperationC(Scope sc, BinaryExpression node) {
        super(sc, node);
        this.opName = binoplist.get(node.getOperator());
    }
    
    @Override
    public String getOperationMethodName() {
        return this.opName;
    }
    
}
