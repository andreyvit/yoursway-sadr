package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;


public class BinaryComparisonC extends BinaryC {
    
    private final Comparison comparison;
    
    BinaryComparisonC(Scope sc, BinaryExpression node) {
        super(sc, node);
        this.comparison = parseComparison(node.getOperator());
    }
    
    static Map<String, Comparison> byOperator;
    
    static {
        byOperator = new HashMap<String, Comparison>();
        byOperator.put("==", Comparison.EQUALS);
        byOperator.put("<>", Comparison.NOT_EQUALS);
        byOperator.put("!=", Comparison.NOT_EQUALS);
        byOperator.put("<", Comparison.LESS);
        byOperator.put("<=", Comparison.LESS_OR_EQUALS);
        byOperator.put(">", Comparison.GREATER);
        byOperator.put(">=", Comparison.GREATER_OR_EQUALS);
    }
    
    public static Comparison parseComparison(String operator) {
        return byOperator.get(operator);
    }
    
    public Comparison getComparison() {
        return comparison;
    }
}
