package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallResolver;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public class UnaryOperationC extends UnaryC implements PythonConstruct {
    private final String opName;
    
    UnaryOperationC(Scope sc, UnaryExpression node) {
        super(sc, node);
        opName = oplist.get(node.getOperator());
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        PythonValueSet results = new PythonValueSet();
        for (PythonObject left : getLeft().evaluate(context)) {
            final RuntimeArguments args = new RuntimeArguments(left);
            for (PythonObject method : CallResolver.findMethod(left, opName, context)) {
                results.addResults(CallResolver.callFunction(method, args, context, this));
            }
        }
        return results;
    }
}
