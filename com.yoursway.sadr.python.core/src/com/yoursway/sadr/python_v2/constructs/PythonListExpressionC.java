package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.TupleIterator;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.ListType;
import com.yoursway.sadr.python_v2.model.builtins.values.ListValue;

public class PythonListExpressionC extends PythonConstructImpl<PythonListExpression> implements
        PythonConstruct {
    
    public PythonListExpressionC(Scope sc, PythonListExpression node) {
        super(sc, node);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        List<PythonConstruct> args = getPostChildren();
        PythonValueSet results = new PythonValueSet();
        ArrayList<PythonValueSet> choices = new ArrayList<PythonValueSet>();
        for (PythonConstruct arg : args) {
            PythonValueSet evaluated = arg.evaluate(context);
            choices.add(evaluated);
        }
        for (List<PythonObject> actualArguments : new TupleIterator(choices)) {
            ListValue listObject = ListType.wrap(actualArguments);
            results.addResult(listObject, context);
        }
        return results;
    }
}
