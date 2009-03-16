package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;

public class UnaryOperationC extends UnaryC implements PythonConstruct {
    
    UnaryOperationC(PythonStaticContext sc, UnaryExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    //    @Override
    //    public PythonValueSet evaluate(final PythonDynamicContext context) {
    //        PythonValueSet results = new PythonValueSet();
    //        for (PythonValue left : getLeft().evaluate(context)) {
    //            final RuntimeArguments args = new RuntimeArguments(left);
    //            for (PythonValue method : CallResolver.findMethod(left, opName, context)) {
    //                results.addResults(CallResolver.callFunction(method, args, context, this));
    //            }
    //        }
    //        return results;
    //    }
}
