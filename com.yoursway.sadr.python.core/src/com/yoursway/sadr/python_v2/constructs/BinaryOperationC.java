package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class BinaryOperationC extends BinaryC {
    
    private final String leftOpName;
    private final String rightOpName;
    
    BinaryOperationC(Scope sc, BinaryExpression node) {
        super(sc, node);
        this.leftOpName = lbinoplist.get(node.getOperator());
        this.rightOpName = rbinoplist.get(node.getOperator());
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile parent) {
        PythonValueSet leftSet = getLeft().evaluate(parent);
        PythonValueSet rightSet = getRight().evaluate(parent);
        PythonValueSet resultSet = new PythonValueSet();
        
        for (RuntimeObject left : leftSet) {
            PythonValueSet methods = CallResolver.findMethod(left, leftOpName, parent);
            if (!methods.isEmpty()) {
                for (RuntimeObject right : rightSet) {
                    PythonArguments args = new PythonArguments(left, right);
                    for (RuntimeObject method : methods) {
                        PythonValueSet value = CallResolver.callFunction(method, args, parent, this);
                        resultSet.addResults(value);
                    }
                }
            } else {
                for (RuntimeObject right : rightSet) {
                    PythonArguments args = new PythonArguments(left, right);
                    PythonValueSet rightMethods = CallResolver.findMethod(right, rightOpName, parent);
                    for (RuntimeObject method : rightMethods) {
                        PythonValueSet value = CallResolver.callFunction(method, args, parent, this);
                        resultSet.addResults(value);
                    }
                }
            }
        }
        
        return resultSet;
    }
}
