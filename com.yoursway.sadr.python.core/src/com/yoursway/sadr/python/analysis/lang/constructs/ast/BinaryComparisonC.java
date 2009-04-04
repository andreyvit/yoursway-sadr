package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.support.Comparison;

public class BinaryComparisonC extends BinaryC {
    
    private final Comparison comparison;
    
    BinaryComparisonC(PythonStaticContext sc, BinaryExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
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
