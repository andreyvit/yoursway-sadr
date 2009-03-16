package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class PythonListExpressionC extends PythonConstructImpl<PythonListExpression> implements
        PythonConstruct {
    
    public PythonListExpressionC(PythonStaticContext sc, PythonListExpression node,
            PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    //    
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
    //            ListValue listObject = ListType.wrap(actualArguments);
    //            results.addResult(listObject, context);
    //        }
    //        return results;
    //    }
    
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new UnsupportedOperationException();
    }
    
}
