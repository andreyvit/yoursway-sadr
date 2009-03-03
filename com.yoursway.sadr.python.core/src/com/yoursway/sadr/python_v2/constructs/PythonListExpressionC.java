package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.DictIterator;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.ListType;
import com.yoursway.sadr.python_v2.model.builtins.ListValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class PythonListExpressionC extends PythonConstructImpl<PythonListExpression> implements
        PythonConstruct {
    
    public PythonListExpressionC(Scope sc, PythonListExpression node) {
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
            PythonValue<ListValue> listObject = ListType.wrap(actualArguments.values());
            results.addResult(listObject, context);
        }
        return results;
    }
}
