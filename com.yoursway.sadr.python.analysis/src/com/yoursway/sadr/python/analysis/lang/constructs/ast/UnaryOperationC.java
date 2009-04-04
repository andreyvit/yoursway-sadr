package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.python.parser.ast.expressions.UnaryExpression;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;

public class UnaryOperationC extends UnaryC implements PythonConstruct {
    
    UnaryOperationC(PythonLexicalContext sc, UnaryExpression node, PythonConstructImpl<?> parent) {
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
