package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonDictExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class PythonDictExpressionC extends PythonConstructImpl<PythonDictExpression> implements
        PythonConstruct {
    
    public PythonDictExpressionC(PythonStaticContext sc, PythonDictExpression node,
            PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
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
    //            DictValue dictObject = DictType.wrap();
    //            HashMap<PythonValue, PythonValue> dict = dictObject.getDict();
    //            for (int i = 0; i < args.size() / 2; i++) {
    //                PythonValue key = actualArguments.get(i * 2);
    //                PythonValue value = actualArguments.get(i * 2 + 1);
    //                dict.put(key, value);
    //            }
    //            results.addResult(dictObject, context);
    //        }
    //        return results;
    //    }
}
