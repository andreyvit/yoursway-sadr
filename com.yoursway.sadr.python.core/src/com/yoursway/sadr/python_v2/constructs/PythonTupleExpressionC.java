package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.PythonTupleExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.DictIterator;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.TupleType;
import com.yoursway.sadr.python_v2.model.builtins.TupleValue;

public class PythonTupleExpressionC extends PythonConstructImpl<PythonTupleExpression> implements
        PythonConstruct {
    
    public PythonTupleExpressionC(Scope sc, PythonTupleExpression node) {
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
            PythonValue<TupleValue> tupleObject = TupleType.wrap(actualArguments.values());
            results.addResult(tupleObject, context);
        }
        return results;
    }
}
