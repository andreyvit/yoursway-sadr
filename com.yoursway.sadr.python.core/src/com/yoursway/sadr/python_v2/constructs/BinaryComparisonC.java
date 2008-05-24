package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class BinaryComparisonC extends BinaryC {
    
    private final Comparison comparison;
    
    BinaryComparisonC(Scope sc, BinaryExpression node, Comparison comparison) {
        super(sc, node);
        this.comparison = comparison;
    }
    
    private static final Map<String, Comparison> byOperator = createMap();
    
    private static Map<String, Comparison> createMap() {
        Map<String, Comparison> result = new HashMap<String, Comparison>();
        result.put("==", Comparison.EQUALS);
        result.put("<>", Comparison.NOT_EQUALS);
        result.put("!=", Comparison.NOT_EQUALS);
        result.put("<", Comparison.LESS);
        result.put("<=", Comparison.LESS_OR_EQUALS);
        result.put(">", Comparison.GREATER);
        result.put(">=", Comparison.GREATER_OR_EQUALS);
        return result;
    }
    
    public static Comparison parseComparison(String operator) {
        return byOperator.get(operator);
    }
    
    public Comparison getComparison() {
        return comparison;
    }
    
    @Override
    public String getOperationMethodName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
