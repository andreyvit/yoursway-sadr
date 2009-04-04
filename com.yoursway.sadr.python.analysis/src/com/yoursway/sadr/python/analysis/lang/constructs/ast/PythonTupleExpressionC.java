package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonTupleExpression;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class PythonTupleExpressionC extends PythonConstructImpl<PythonTupleExpression> implements
        PythonConstruct {
    
    public PythonTupleExpressionC(PythonLexicalContext sc, PythonTupleExpression node,
            PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new UnsupportedOperationException();
    }
    
    //    @Override
    //    public PythonValueSet evaluate(final PythonDynamicContext context) {
    //        List<PythonConstruct> args = getPostChildren();
    //        PythonValueSet results = new PythonValueSet();
    //        ArrayList<PythonValueSet> choices = new ArrayList<PythonValueSet>();
    //        for (PythonConstruct arg : args) {
    //            PythonValueSet evaluated = arg.evaluate(context);
    //            choices.add(evaluated);
    //        }
    //        for (List<PythonValue> actualArguments : new TupleIterator(choices)) {
    //            TupleValue tupleObject = TupleType.wrap(actualArguments);
    //            results.addResult(tupleObject, context);
    //        }
    //        return results;
    //    }
    
}
