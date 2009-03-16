package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallResolver;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

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
        
        for (PythonObject left : leftSet) {
            PythonValueSet methods = CallResolver.findMethod(left, leftOpName, parent);
            if (!methods.isEmpty()) {
                for (PythonObject right : rightSet) {
                    RuntimeArguments args = new RuntimeArguments(left, right);
                    for (PythonObject method : methods) {
                        PythonValueSet value = CallResolver.callFunction(method, args, parent, this);
                        resultSet.addResults(value);
                    }
                }
            } else {
                for (PythonObject right : rightSet) {
                    RuntimeArguments args = new RuntimeArguments(left, right);
                    PythonValueSet rightMethods = CallResolver.findMethod(right, rightOpName, parent);
                    for (PythonObject method : rightMethods) {
                        PythonValueSet value = CallResolver.callFunction(method, args, parent, this);
                        resultSet.addResults(value);
                    }
                }
            }
        }
        
        return resultSet;
    }
}
