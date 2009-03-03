package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.PythonDictExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.DictIterator;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.DictType;
import com.yoursway.sadr.python_v2.model.builtins.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class PythonDictExpressionC extends PythonConstructImpl<PythonDictExpression> implements
        PythonConstruct {
    
    public PythonDictExpressionC(Scope sc, PythonDictExpression node) {
        super(sc, node);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        List<PythonConstruct> args = getPostChildren();
        PythonValueSet results = new PythonValueSet();
        Map<PythonConstruct, PythonValueSet> choices = new HashMap<PythonConstruct, PythonValueSet>();
        for (PythonConstruct arg : args) {
            PythonValueSet evaluated = arg.evaluate(context);
            choices.put(arg, evaluated);
        }
        for (Map<PythonConstruct, RuntimeObject> actualArguments : new DictIterator<PythonConstruct>(choices)) {
            PythonValue<DictValue> dictObject = DictType.wrap();
            HashMap<RuntimeObject, RuntimeObject> dict = dictObject.getValue().getDict();
            for (int i = 0; i < args.size() / 2; i++) {
                RuntimeObject key = actualArguments.get(i * 2);
                RuntimeObject value = actualArguments.get(i * 2 + 1);
                dict.put(key, value);
            }
            results.addResult(dictObject, context);
        }
        return results;
    }
}
