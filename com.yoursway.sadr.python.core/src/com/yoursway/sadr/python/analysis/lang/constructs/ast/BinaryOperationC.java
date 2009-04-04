package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;

public class BinaryOperationC extends BinaryC {
    
    BinaryOperationC(PythonStaticContext sc, BinaryExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    //    @Override
    //    public PythonValueSet evaluate(final PythonDynamicContext parent) {
    //        PythonValueSet leftSet = getLeft().evaluate(parent);
    //        PythonValueSet rightSet = getRight().evaluate(parent);
    //        PythonValueSet resultSet = new PythonValueSet();
    //        
    //        for (PythonValue left : leftSet) {
    //            PythonValueSet methods = CallResolver.findMethod(left, leftOpName, parent);
    //            if (!methods.isEmpty()) {
    //                for (PythonValue right : rightSet) {
    //                    RuntimeArguments args = new RuntimeArguments(left, right);
    //                    for (PythonValue method : methods) {
    //                        PythonValueSet value = CallResolver.callFunction(method, args, parent, this);
    //                        resultSet.addResults(value);
    //                    }
    //                }
    //            } else {
    //                for (PythonValue right : rightSet) {
    //                    RuntimeArguments args = new RuntimeArguments(left, right);
    //                    PythonValueSet rightMethods = CallResolver.findMethod(right, rightOpName, parent);
    //                    for (PythonValue method : rightMethods) {
    //                        PythonValueSet value = CallResolver.callFunction(method, args, parent, this);
    //                        resultSet.addResults(value);
    //                    }
    //                }
    //            }
    //        }
    //        
    //        return resultSet;
    //    }
}
